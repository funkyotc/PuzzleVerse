package com.funkyotc.puzzleverse.arrowescape.ui

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.funkyotc.puzzleverse.arrowescape.model.Arrow
import com.funkyotc.puzzleverse.arrowescape.model.Direction
import kotlinx.coroutines.launch
import kotlin.math.sin

import com.funkyotc.puzzleverse.arrowescape.model.LevelShape

@Composable
fun ArrowEscapeGrid(
    arrows: List<Arrow>,
    gridWidth: Int,
    gridHeight: Int,
    shape: LevelShape = LevelShape.SQUARE,
    onArrowTapped: (Int, () -> Unit, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val coroutineScope = rememberCoroutineScope()

    val scaleAnim = remember { Animatable(1f) }
    val panAnim = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    var containerSize by remember { mutableStateOf(Size.Zero) }

    // Track bump animations per arrow
    val bumpOffsets = remember { mutableStateMapOf<Int, Animatable<Float, androidx.compose.animation.core.AnimationVector1D>>() }

    var lastTapTime by remember { mutableLongStateOf(0L) }
    var lastTapOffset by remember { mutableStateOf(Offset.Zero) }

    // Colors mapping (soft pastel tones)
    val colorPalette = listOf(
        Color(0xFFE57373), // Red pastel
        Color(0xFF81C784), // Green pastel
        Color(0xFF64B5F6), // Blue pastel
        Color(0xFFFFD54F), // Yellow pastel
        Color(0xFFBA68C8), // Purple pastel
        Color(0xFF4DB6AC), // Teal pastel
        Color(0xFFFF8A65)  // Orange pastel
    )

    fun clampPan(pan: Offset, scale: Float, width: Float, height: Float): Offset {
        if (scale <= 1.0f || width <= 0f || height <= 0f) return Offset.Zero
        val maxPanX = (width * (scale - 1f)) / 2f
        val maxPanY = (height * (scale - 1f)) / 2f
        return Offset(
            x = pan.x.coerceIn(-maxPanX, maxPanX),
            y = pan.y.coerceIn(-maxPanY, maxPanY)
        )
    }

    Box(
        modifier = modifier
            .aspectRatio(gridWidth.toFloat() / gridHeight.toFloat())
            .onSizeChanged {
                containerSize = Size(it.width.toFloat(), it.height.toFloat())
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .pointerInput(gridWidth, gridHeight, arrows) {
                    awaitEachGesture {
                        var isTransforming = false
                        var initialTapOffset = Offset.Zero
                        val touchSlop = viewConfiguration.touchSlop
                        var totalDragOffset = Offset.Zero

                        val down = awaitFirstDown(requireUnconsumed = false)
                        val startTime = System.currentTimeMillis()
                        initialTapOffset = down.position

                        do {
                            val event = awaitPointerEvent()
                            val canceled = event.changes.any { it.isConsumed }
                            if (canceled) break

                            val pointers = event.changes.filter { it.pressed }
                            if (pointers.isEmpty()) break

                            if (pointers.size >= 2) {
                                isTransforming = true
                                val zoomChange = event.calculateZoom()
                                val panChange = event.calculatePan()
                                val centroid = event.calculateCentroid()

                                event.changes.forEach { it.consume() }

                                if (zoomChange != 1.0f || panChange != Offset.Zero) {
                                    coroutineScope.launch {
                                        val oldScale = scaleAnim.value
                                        val newScale = (oldScale * zoomChange).coerceIn(1.0f, 5.0f)
                                        val midX = containerSize.width / 2f
                                        val midY = containerSize.height / 2f

                                        val oldPan = panAnim.value
                                        val newPanUnclamped = Offset(
                                            x = (centroid.x - midX) * (1f - newScale / oldScale) + oldPan.x * (newScale / oldScale) + panChange.x,
                                            y = (centroid.y - midY) * (1f - newScale / oldScale) + oldPan.y * (newScale / oldScale) + panChange.y
                                        )
                                        val clampedPan = clampPan(newPanUnclamped, newScale, containerSize.width, containerSize.height)

                                        scaleAnim.snapTo(newScale)
                                        panAnim.snapTo(clampedPan)
                                    }
                                }
                            } else if (pointers.size == 1) {
                                val change = pointers.first()
                                val dragDelta = change.position - change.previousPosition
                                totalDragOffset += dragDelta

                                if (scaleAnim.value > 1.0f) {
                                    if (totalDragOffset.getDistance() > touchSlop) {
                                        isTransforming = true
                                        change.consume()
                                        coroutineScope.launch {
                                            val newPan = clampPan(
                                                panAnim.value + dragDelta,
                                                scaleAnim.value,
                                                containerSize.width,
                                                containerSize.height
                                            )
                                            panAnim.snapTo(newPan)
                                        }
                                    }
                                }
                            }
                        } while (event.changes.any { it.pressed })

                        val upTime = System.currentTimeMillis()
                        if (!isTransforming && totalDragOffset.getDistance() < touchSlop && (upTime - startTime) < 500) {
                            val tapOffset = initialTapOffset
                            val currentScale = scaleAnim.value
                            val currentPan = panAnim.value
                            val width = containerSize.width
                            val height = containerSize.height

                            if (width > 0 && height > 0) {
                                val canvasX = width / 2f + (tapOffset.x - width / 2f - currentPan.x) / currentScale
                                val canvasY = height / 2f + (tapOffset.y - height / 2f - currentPan.y) / currentScale

                                val cellWidth = width / gridWidth
                                val cellHeight = height / gridHeight

                                val tapX = (canvasX / cellWidth).toInt()
                                val tapY = (canvasY / cellHeight).toInt()

                                if (tapX in 0 until gridWidth && tapY in 0 until gridHeight) {
                                    val now = System.currentTimeMillis()
                                    if (now - lastTapTime < 300 && (tapOffset - lastTapOffset).getDistance() < touchSlop * 2) {
                                        // Double tap: toggle zoom between 1.0f and 2.5f centered around tap location
                                        coroutineScope.launch {
                                            if (currentScale > 1.2f) {
                                                scaleAnim.animateTo(1.0f, tween(250))
                                                panAnim.animateTo(Offset.Zero, tween(250))
                                            } else {
                                                val targetScale = 2.5f
                                                val targetPan = clampPan(
                                                    Offset(
                                                        x = (width / 2f - tapOffset.x) * (targetScale - 1f),
                                                        y = (height / 2f - tapOffset.y) * (targetScale - 1f)
                                                    ),
                                                    targetScale,
                                                    width,
                                                    height
                                                )
                                                scaleAnim.animateTo(targetScale, tween(250))
                                                panAnim.animateTo(targetPan, tween(250))
                                            }
                                        }
                                        lastTapTime = 0L
                                    } else {
                                        lastTapTime = now
                                        lastTapOffset = tapOffset

                                        val tappedArrow = arrows.find { arrow ->
                                            arrow.segments.any { it.x == tapX && it.y == tapY }
                                        }
                                        if (tappedArrow != null) {
                                            onArrowTapped(tappedArrow.id, {
                                                // Bump
                                                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                                                coroutineScope.launch {
                                                    val anim = bumpOffsets.getOrPut(tappedArrow.id) { Animatable(0f) }
                                                    anim.animateTo(1f, animationSpec = tween(150))
                                                    anim.snapTo(0f)
                                                }
                                            }, {
                                                // Move
                                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scaleAnim.value
                        scaleY = scaleAnim.value
                        translationX = panAnim.value.x
                        translationY = panAnim.value.y
                    }
            ) {
                val cellWidth = size.width / gridWidth
                val cellHeight = size.height / gridHeight

                // Draw background shape tiles
                for (r in 0 until gridHeight) {
                    for (c in 0 until gridWidth) {
                        if (shape.isCellInside(c, r, gridWidth, gridHeight)) {
                            drawRoundRect(
                                color = Color(0x10FFFFFF),
                                topLeft = Offset(c * cellWidth + 1f, r * cellHeight + 1f),
                                size = Size(cellWidth - 2f, cellHeight - 2f),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                            )
                        }
                    }
                }

                // Draw arrows
                for (arrow in arrows) {
                    if (arrow.segments.isEmpty()) continue

                    val bumpAnim = bumpOffsets[arrow.id]?.value ?: 0f
                    var bumpOffsetX = 0f
                    var bumpOffsetY = 0f
                    if (bumpAnim > 0f) {
                        val bumpMagnitude = 0.2f * cellWidth // 20% of cell
                        val wave = sin(bumpAnim * Math.PI).toFloat()
                        bumpOffsetX = arrow.direction.dx * bumpMagnitude * wave
                        bumpOffsetY = arrow.direction.dy * bumpMagnitude * wave
                    }

                    val cellSize = minOf(cellWidth, cellHeight)
                    val strokeWidth = cellSize * 0.22f
                    val arrowHeadSize = cellSize * 0.72f

                    // Determine direction of the line segment entering the head
                    val headDir = if (arrow.segments.size > 1) {
                        val h = arrow.segments[0]
                        val n = arrow.segments[1]
                        val dx = h.x - n.x
                        val dy = h.y - n.y
                        when {
                            dx == 0 && dy < 0 -> Direction.UP
                            dx == 0 && dy > 0 -> Direction.DOWN
                            dx < 0 && dy == 0 -> Direction.LEFT
                            dx > 0 && dy == 0 -> Direction.RIGHT
                            else -> arrow.direction
                        }
                    } else {
                        arrow.direction
                    }

                    val path = Path()
                    val points = arrow.segments.reversed()

                    for (i in points.indices) {
                        val pt = points[i]
                        var cx = pt.x * cellWidth + cellWidth / 2f + bumpOffsetX
                        var cy = pt.y * cellHeight + cellHeight / 2f + bumpOffsetY

                        // For the head segment (the last point in reversed list), offset slightly backward along headDir
                        if (i == points.lastIndex) {
                            cx -= headDir.dx * arrowHeadSize * 0.15f
                            cy -= headDir.dy * arrowHeadSize * 0.15f
                        }

                        if (i == 0) {
                            path.moveTo(cx, cy)
                        } else {
                            path.lineTo(cx, cy)
                        }
                    }

                    val color = colorPalette[arrow.color % colorPalette.size]

                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    // Draw the prominent head pointer aligned with headDir
                    val head = arrow.head
                    val hx = head.x * cellWidth + cellWidth / 2f + bumpOffsetX
                    val hy = head.y * cellHeight + cellHeight / 2f + bumpOffsetY

                    val headPath = Path()
                    val hs = arrowHeadSize

                    // High contrast tone shift for head visibility
                    val tipColor = Color(
                        red = (color.red * 0.65f).coerceIn(0f, 1f),
                        green = (color.green * 0.65f).coerceIn(0f, 1f),
                        blue = (color.blue * 0.65f).coerceIn(0f, 1f),
                        alpha = 1f
                    )
                    val outlineColor = Color.Black.copy(alpha = 0.40f)

                    when (headDir) {
                        Direction.UP -> {
                            headPath.moveTo(hx, hy - hs * 0.45f)
                            headPath.lineTo(hx - hs * 0.42f, hy + hs * 0.35f)
                            headPath.lineTo(hx, hy + hs * 0.10f)
                            headPath.lineTo(hx + hs * 0.42f, hy + hs * 0.35f)
                        }
                        Direction.DOWN -> {
                            headPath.moveTo(hx, hy + hs * 0.45f)
                            headPath.lineTo(hx - hs * 0.42f, hy - hs * 0.35f)
                            headPath.lineTo(hx, hy - hs * 0.10f)
                            headPath.lineTo(hx + hs * 0.42f, hy - hs * 0.35f)
                        }
                        Direction.LEFT -> {
                            headPath.moveTo(hx - hs * 0.45f, hy)
                            headPath.lineTo(hx + hs * 0.35f, hy - hs * 0.42f)
                            headPath.lineTo(hx + hs * 0.10f, hy)
                            headPath.lineTo(hx + hs * 0.35f, hy + hs * 0.42f)
                        }
                        Direction.RIGHT -> {
                            headPath.moveTo(hx + hs * 0.45f, hy)
                            headPath.lineTo(hx - hs * 0.35f, hy - hs * 0.42f)
                            headPath.lineTo(hx - hs * 0.10f, hy)
                            headPath.lineTo(hx - hs * 0.35f, hy + hs * 0.42f)
                        }
                    }
                    headPath.close()

                    // Draw filled head and crisp outline
                    drawPath(path = headPath, color = tipColor)
                    drawPath(
                        path = headPath,
                        color = outlineColor,
                        style = Stroke(width = cellSize * 0.04f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
            }
        }

        // Overlay Zoom Controls
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
                tonalElevation = 6.dp,
                shadowElevation = 4.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    if (scaleAnim.value > 1.05f) {
                        Text(
                            text = "${(scaleAnim.value * 100).toInt()}%",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    scaleAnim.animateTo(1.0f, tween(250))
                                    panAnim.animateTo(Offset.Zero, tween(250))
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CenterFocusStrong,
                                contentDescription = "Reset Zoom",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val newScale = (scaleAnim.value / 1.35f).coerceAtLeast(1.0f)
                                val newPan = clampPan(panAnim.value, newScale, containerSize.width, containerSize.height)
                                scaleAnim.animateTo(newScale, tween(200))
                                panAnim.animateTo(newPan, tween(200))
                            }
                        },
                        enabled = scaleAnim.value > 1.0f,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Zoom Out",
                            tint = if (scaleAnim.value > 1.0f) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val newScale = (scaleAnim.value * 1.35f).coerceAtMost(5.0f)
                                val newPan = clampPan(panAnim.value, newScale, containerSize.width, containerSize.height)
                                scaleAnim.animateTo(newScale, tween(200))
                                panAnim.animateTo(newPan, tween(200))
                            }
                        },
                        enabled = scaleAnim.value < 5.0f,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Zoom In",
                            tint = if (scaleAnim.value < 5.0f) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
