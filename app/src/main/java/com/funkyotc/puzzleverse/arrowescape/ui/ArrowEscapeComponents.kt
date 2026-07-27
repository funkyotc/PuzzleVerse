package com.funkyotc.puzzleverse.arrowescape.ui

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import com.funkyotc.puzzleverse.arrowescape.model.Arrow
import com.funkyotc.puzzleverse.arrowescape.model.Direction
import kotlinx.coroutines.launch
import kotlin.math.sin

@Composable
fun ArrowEscapeGrid(
    arrows: List<Arrow>,
    gridWidth: Int,
    gridHeight: Int,
    onArrowTapped: (Int, () -> Unit, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val coroutineScope = rememberCoroutineScope()
    
    // Track bump animations per arrow
    val bumpOffsets = remember { mutableStateMapOf<Int, androidx.compose.animation.core.Animatable<Float, androidx.compose.animation.core.AnimationVector1D>>() }

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

    Box(
        modifier = modifier
            .aspectRatio(gridWidth.toFloat() / gridHeight.toFloat())
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cellWidth = size.width / gridWidth
                        val cellHeight = size.height / gridHeight
                        
                        val tapX = (offset.x / cellWidth).toInt()
                        val tapY = (offset.y / cellHeight).toInt()
                        
                        // Find which arrow was tapped
                        val tappedArrow = arrows.find { arrow ->
                            arrow.segments.any { it.x == tapX && it.y == tapY }
                        }
                        
                        if (tappedArrow != null) {
                            onArrowTapped(tappedArrow.id, {
                                // Bump
                                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                                coroutineScope.launch {
                                    val anim = bumpOffsets.getOrPut(tappedArrow.id) { Animatable(0f) }
                                    // simple sine wave bump
                                    anim.animateTo(1f, animationSpec = tween(150))
                                    anim.snapTo(0f)
                                }
                            }, {
                                // Move (play a lighter tick)
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                            })
                        }
                    }
                }
        ) {
            val cellWidth = size.width / gridWidth
            val cellHeight = size.height / gridHeight
            
            // Draw grid background (optional, for debugging)
            // for (x in 0 until gridWidth) {
            //     for (y in 0 until gridHeight) {
            //         drawRect(
            //             color = Color.LightGray.copy(alpha = 0.2f),
            //             topLeft = Offset(x * cellWidth, y * cellHeight),
            //             size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight),
            //             style = Stroke(width = 1f)
            //         )
            //     }
            // }

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
                drawPath(path = headPath, color = outlineColor, style = Stroke(width = cellSize * 0.04f, cap = StrokeCap.Round, join = StrokeJoin.Round))
            }
        }
    }
}
