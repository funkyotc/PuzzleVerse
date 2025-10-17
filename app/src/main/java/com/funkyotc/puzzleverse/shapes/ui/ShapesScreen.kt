package com.funkyotc.puzzleverse.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.shapes.model.PuzzlePiece
import com.funkyotc.puzzleverse.shapes.viewmodel.ShapesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapesScreen(navController: NavController, viewModel: ShapesViewModel = viewModel()) {
    val puzzle by viewModel.puzzle.collectAsState()
    var selectedPiece by remember { mutableStateOf<PuzzlePiece?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shapes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        puzzle?.let { puzzleState ->
            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            // Simple tap-to-select logic
                            selectedPiece = puzzleState.pieces.find { piece ->
                                val pieceBounds = piece.vertices.map { it + piece.position }
                                isPointInPolygon(offset, pieceBounds)
                            }
                        },
                        onDoubleTap = { offset ->
                            // Double tap to rotate the selected piece
                            selectedPiece?.let {
                                viewModel.rotatePiece(it.id, 90f)
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures {
                            change, dragAmount ->
                        change.consume()
                        selectedPiece?.let {
                            viewModel.movePiece(it.id, it.position + dragAmount)
                        }
                    }
                }
            ) {
                // Draw the target shape
                val targetPath = Path().apply {
                    val firstVertex = puzzleState.target.vertices.first()
                    moveTo(firstVertex.x, firstVertex.y)
                    for (i in 1 until puzzleState.target.vertices.size) {
                        val vertex = puzzleState.target.vertices[i]
                        lineTo(vertex.x, vertex.y)
                    }
                    close()
                }
                drawPath(targetPath, Color.LightGray, style = Stroke(width = 5f))

                // Draw the puzzle pieces
                puzzleState.pieces.forEach { piece ->
                    translate(left = piece.position.x, top = piece.position.y) {
                        val piecePath = Path().apply {
                            val firstVertex = piece.vertices.first()
                            moveTo(firstVertex.x, firstVertex.y)
                            piece.vertices.drop(1).forEach { vertex ->
                                lineTo(vertex.x, vertex.y)
                            }
                            close()
                        }
                        drawPath(
                            path = piecePath,
                            color = if (piece == selectedPiece) Color.Red else Color.Blue,
                            style = Stroke(width = 3f)
                        )
                    }
                }
            }
        }
    }
}

// Helper to detect if a point is inside a polygon (for piece selection)
fun isPointInPolygon(point: Offset, polygon: List<Offset>): Boolean {
    var isInside = false
    var i = 0
    var j = polygon.size - 1
    while (i < polygon.size) {
        if (((polygon[i].y > point.y) != (polygon[j].y > point.y)) &&
            (point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)
        ) {
            isInside = !isInside
        }
        j = i++
    }
    return isInside
}