package com.funkyotc.puzzleverse.core.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

object PuzzleVerseAnimationSpecs {
    // Snappy, subtle physics-based spring for standard movements (e.g., Chess, TFE, Grid slides)
    fun <T> fastMovementSpec() = spring<T>(
        stiffness = Spring.StiffnessMedium,
        dampingRatio = Spring.DampingRatioLowBouncy
    )

    // Snappy, stiffer spring for quick UI adjustments/scalings
    fun <T> stiffSpringSpec() = spring<T>(
        stiffness = Spring.StiffnessMedium,
        dampingRatio = Spring.DampingRatioNoBouncy
    )

    // Fast linear-out-slow-in tween for fades
    fun <T> fastTweenSpec(duration: Int = 150) = tween<T>(
        durationMillis = duration
    )
}

/**
 * Applies a subtle scaling effect (0.96f) on item press to simulate tactile feedback.
 * Works seamlessly with existing click states by sharing the same [interactionSource].
 */
@Composable
fun Modifier.animateTapFeedback(interactionSource: MutableInteractionSource): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        animationSpec = PuzzleVerseAnimationSpecs.stiffSpringSpec(),
        label = "tapScale"
    )
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/**
 * Fades and scales in an item on load or when a state trigger changes.
 * Can be staggered by passing a non-zero [delayMillis].
 */
@Composable
fun Modifier.animateEntrance(
    delayMillis: Int = 0,
    trigger: Any? = Unit
): Modifier {
    var visible by remember(trigger) { mutableStateOf(false) }
    LaunchedEffect(trigger) {
        if (delayMillis > 0) {
            delay(delayMillis.toLong())
        }
        visible = true
    }
    
    val scale by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0.85f,
        animationSpec = PuzzleVerseAnimationSpecs.fastMovementSpec(),
        label = "entranceScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0.0f,
        animationSpec = PuzzleVerseAnimationSpecs.fastTweenSpec(180),
        label = "entranceAlpha"
    )
    
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
        this.alpha = alpha
    }
}

/**
 * A fast, snappy entrance effect designed specifically for game pieces placed/spawned on boards.
 */
@Composable
fun Modifier.animatePiecePlacement(
    trigger: Any? = Unit
): Modifier {
    var visible by remember(trigger) { mutableStateOf(false) }
    LaunchedEffect(trigger) {
        visible = true
    }
    
    val scale by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0.4f,
        animationSpec = spring(
            stiffness = Spring.StiffnessHigh,
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
        label = "placementScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0.0f,
        animationSpec = PuzzleVerseAnimationSpecs.fastTweenSpec(120),
        label = "placementAlpha"
    )
    
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
        this.alpha = alpha
    }
}
