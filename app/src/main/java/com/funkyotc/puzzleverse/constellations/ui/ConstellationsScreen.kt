package com.funkyotc.puzzleverse.constellations.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funkyotc.puzzleverse.constellations.data.Star
import com.funkyotc.puzzleverse.constellations.viewmodel.ConstellationsViewModel

@Composable
fun ConstellationsScreen(constellationsViewModel: ConstellationsViewModel = viewModel()) {
    val puzzle by constellationsViewModel.puzzle.collectAsState()
    val drawnConnections by constellationsViewModel.drawnConnections.collectAsState()
    val isGameWon by constellationsViewModel.isGameWon.collectAsState()

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { constellationsViewModel.loadNewPuzzle() },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You completed the constellation!") },
            confirmButton = {
                Button(onClick = { constellationsViewModel.loadNewPuzzle() }) {
                    Text("New Game")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val clickedStar = puzzle?.stars?.find { star ->
                        val starPosition = Offset(star.x * size.width, star.y * size.height)
                        (offset - starPosition).getDistance() < 20.dp.toPx()
                    }

                    if (clickedStar != null) {
                        constellationsViewModel.onStarClicked(clickedStar.id)
                    }
                }
            }) {
            puzzle?.let { p ->
                // Draw stars
                for (star in p.stars) {
                    drawCircle(
                        color = Color.Yellow,
                        radius = 15f,
                        center = Offset(star.x * size.width, star.y * size.height)
                    )
                }

                // Draw connections
                for (connection in drawnConnections) {
                    val fromStar = p.stars.find { it.id == connection.first }!!
                    val toStar = p.stars.find { it.id == connection.second }!!
                    drawLine(
                        color = Color.White,
                        start = Offset(fromStar.x * size.width, fromStar.y * size.height),
                        end = Offset(toStar.x * size.width, toStar.y * size.height),
                        strokeWidth = 5f
                    )
                }
            }
        }
    }
}
