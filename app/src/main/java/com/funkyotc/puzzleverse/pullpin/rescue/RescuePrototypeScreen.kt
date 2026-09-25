package com.funkyotc.puzzleverse.pullpin.rescue

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.core.ui.StandardGameLayout
import com.funkyotc.puzzleverse.pullpin.data.WORLD_H
import com.funkyotc.puzzleverse.pullpin.data.WORLD_W
import kotlin.math.hypot

private val StoneColor = Color(0xFFB6B8B6)
private val WallColor = Color(0xFF536981)
private val PinColor = Color(0xFFFFC45B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RescuePrototypeScreen(navController: NavController) {
    val model: RescuePrototypeViewModel = viewModel()
    val ui by model.ui.collectAsState()
    val level = RescuePrototype.levels[ui.levelIndex]
    val state = ui.state
    var showHelp by remember { mutableStateOf(true) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(showHelp) { model.setOverlayPaused(showHelp) }

    DisposableEffect(lifecycleOwner, model) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) model.setBackgroundPaused(true)
            if (event == Lifecycle.Event.ON_START) model.setBackgroundPaused(false)
        }
        model.setBackgroundPaused(!lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            model.setBackgroundPaused(true)
        }
    }
    LaunchedEffect(model) {
        while (true) withFrameNanos { model.frame(it) }
    }

    if (showHelp) AlertDialog(
        onDismissRequest = {},
        title = { Text(level.title) },
        text = { Text("${level.lesson}\n\nStones begin flowing when you start. Tap gold handles to remove pins. Keep the king's head clear until every stone settles. Pins cannot be replaced.") },
        confirmButton = { TextButton(onClick = { showHelp = false; model.start() }) { Text("Start rescue") } }
    )
    if (state.status == RescueStatus.WON || state.status == RescueStatus.LOST) AlertDialog(
        onDismissRequest = {},
        title = { Text(if (state.status == RescueStatus.WON) "King rescued" else "King buried") },
        text = { Text(if (state.status == RescueStatus.WON) "The stones have settled and his head is clear." else "The stones covered his head. Try another route.") },
        confirmButton = {
            if (state.status == RescueStatus.WON && ui.levelIndex < RescuePrototype.levels.lastIndex) {
                TextButton(onClick = { model.selectLevel(ui.levelIndex + 1); showHelp = true }) { Text("Next level") }
            } else TextButton(onClick = { model.retry() }) { Text("Retry") }
        },
        dismissButton = {
            if (state.status == RescueStatus.WON && ui.levelIndex < RescuePrototype.levels.lastIndex) {
                TextButton(onClick = { model.retry() }) { Text("Retry") }
            }
        }
    )

    StandardGameLayout(
        title = "Pull the Pin · Rescue",
        navController = navController,
        onHowToClick = { model.setOverlayPaused(true); showHelp = true },
        actions = {
            IconButton(onClick = { model.retry() }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Retry current rescue")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(level.title, style = MaterialTheme.typography.titleMedium)
            Text(level.lesson, style = MaterialTheme.typography.bodySmall)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                RescuePrototype.levels.forEachIndexed { index, _ ->
                    TextButton(onClick = {
                        model.selectLevel(index)
                        showHelp = true
                    }) { Text("Level ${index + 1}" + if (index == ui.levelIndex) " ●" else "") }
                }
            }
            val danger = when {
                state.status == RescueStatus.LOST -> "The king's head is buried"
                state.burial.covered -> "Head covered — clear the flow!"
                maxOf(state.burial.left, state.burial.right, state.burial.above) >= .25 -> "Stones are rising near the king"
                else -> "Keep the king's head clear"
            }
            Text(danger, style = MaterialTheme.typography.labelMedium,
                color = if (state.burial.covered) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)
            RescueBoard(level, state, Modifier.fillMaxWidth().weight(1f)) { model.pull(it) }
            TextButton(onClick = { model.retry() }) { Text("Retry same layout") }
        }
    }
}

@Composable
private fun RescueBoard(level: RescueLevel, state: RescueState, modifier: Modifier, onPinTap: (String) -> Unit) {
    val density = LocalDensity.current
    val currentState by rememberUpdatedState(state)
    val currentTap by rememberUpdatedState(onPinTap)
    BoxWithConstraints(modifier) {
        val width = with(density) { maxWidth.toPx() }
        val height = with(density) { maxHeight.toPx() }
        val boardScale = minOf(width / WORLD_W, height / WORLD_H)
        val left = (width - WORLD_W * boardScale) / 2f
        val top = (height - WORLD_H * boardScale) / 2f
        Box(Modifier.fillMaxSize().semantics {
            contentDescription = "${level.title}. ${state.stones.size} stones. ${if (state.burial.covered) "King's head covered" else "King's head clear"}."
            customActions = state.pins.filter { it.acceptedTick == null }.map { pin ->
                CustomAccessibilityAction(pin.pin.label) { currentTap(pin.pin.id); true }
            }
        }.pointerInput(boardScale, left, top) {
            detectTapGestures { tap ->
                val x = (tap.x - left) / boardScale
                val y = (tap.y - top) / boardScale
                val radius = maxOf(28f, with(density) { 24.dp.toPx() } / boardScale)
                currentState.pins.filter { it.acceptedTick == null }.minByOrNull { pin ->
                    val w = pin.pin.shape
                    hypot((x - w.x - w.w / 2).toDouble(), (y - w.y - w.h / 2).toDouble())
                }?.takeIf { pin ->
                    val w = pin.pin.shape
                    hypot((x - w.x - w.w / 2).toDouble(), (y - w.y - w.h / 2).toDouble()) <= radius
                }?.let { currentTap(it.pin.id) }
            }
        }) {
            Canvas(Modifier.fillMaxSize()) {
                translate(left, top) {
                    scale(boardScale, pivot = Offset.Zero) { drawRescue(level, state) }
                }
            }
        }
    }
}

private fun DrawScope.drawRescue(level: RescueLevel, state: RescueState) {
    drawRoundRect(Color(0xFF111D32), size = Size(WORLD_W, WORLD_H), cornerRadius = CornerRadius(14f))
    for (wall in level.walls) rotate(wall.angle, Offset(wall.x + wall.w / 2, wall.y + wall.h / 2)) {
        drawRoundRect(WallColor, Offset(wall.x, wall.y), Size(wall.w, wall.h), CornerRadius(2f))
    }
    val king = level.king
    val kingColor = if (state.burial.covered) Color(0xFFF07868) else Color(0xFFF3B45F)
    drawRoundRect(kingColor, Offset((king.x - king.bodyWidth / 2).toFloat(),
        (king.floorY - king.bodyHeight).toFloat()), Size(king.bodyWidth.toFloat(), king.bodyHeight.toFloat()), CornerRadius(4f))
    drawCircle(kingColor, king.headRadius.toFloat(), Offset(king.x.toFloat(), king.headY.toFloat()))
    drawCircle(Color(0xFF17243A), 1.8f, Offset((king.x + 3).toFloat(), (king.headY - 1).toFloat()))
    val crown = Offset(king.x.toFloat(), (king.headY - king.headRadius - 3).toFloat())
    drawLine(PinColor, crown + Offset(-8f, -4f), crown + Offset(-4f, 2f), 3f)
    drawLine(PinColor, crown + Offset(-4f, 2f), crown, 3f)
    drawLine(PinColor, crown, crown + Offset(4f, 2f), 3f)
    drawLine(PinColor, crown + Offset(4f, 2f), crown + Offset(8f, -4f), 3f)
    for (stone in state.stones) {
        drawCircle(StoneColor, stone.radius.toFloat(), Offset(stone.x.toFloat(), stone.y.toFloat()))
    }
    for (pin in state.pins.filter { !it.removed }) {
        val w = pin.pin.shape
        val progress = pin.progress(state.tick)
        val cx = w.x + w.w / 2
        val cy = w.y + w.h / 2
        translate(25f * progress, 0f) {
            rotate(w.angle, Offset(cx, cy)) {
                drawRoundRect(PinColor.copy(alpha = 1f - progress * .5f), Offset(w.x, w.y),
                    Size(w.w, w.h), CornerRadius(3f))
            }
            drawCircle(Color(0xFF17243A), 14f, Offset(cx, cy))
            drawCircle(PinColor, 12f, Offset(cx, cy), style = androidx.compose.ui.graphics.drawscope.Stroke(4f))
        }
    }
}
