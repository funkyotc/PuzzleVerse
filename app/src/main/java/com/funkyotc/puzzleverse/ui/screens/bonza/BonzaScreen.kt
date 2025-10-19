package com.funkyotc.puzzleverse.ui.screens.bonza

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzle
import com.funkyotc.puzzleverse.bonza.data.ConnectionDirection
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonzaScreen(
    navController: NavController,
    mode: String?,
    forceNewGame: Boolean = false,
    context: Context = LocalContext.current,
    streakRepository: StreakRepository,
    bonzaViewModel: BonzaViewModel = viewModel(factory = BonzaViewModelFactory(context, mode, forceNewGame, streakRepository))
) {
    val isGameWon by bonzaViewModel.isGameWon.collectAsState()
    val puzzle by bonzaViewModel.puzzle.collectAsState()

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You solved the puzzle!") },
            confirmButton = {
                Button(onClick = { bonzaViewModel.newGame() }) {
                    Text("New Game")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bonza") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            BonzaBoard(
                puzzle = puzzle,
                viewModel = bonzaViewModel
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BonzaBoard(puzzle: BonzaPuzzle, viewModel: BonzaViewModel) {
    val textMeasurer = rememberTextMeasurer()
    val primaryColor = MaterialTheme.colorScheme.primary
    val letterBoxSize = 80f
    val letterBoxCornerRadius = 16f

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = puzzle.theme, style = MaterialTheme.typography.headlineMedium)
        Canvas(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset -> viewModel.onDragStart(offset) },
                onDrag = { change, dragAmount ->
                    change.consume()
                    viewModel.onDrag(dragAmount)
                },
                onDragEnd = { viewModel.onDragEnd() }
            )
        }) {
            puzzle.fragments.forEach { fragment ->
                val shadowOffset = Offset(5f, 5f)
                fragment.text.forEachIndexed { index, char ->
                    val letterOffset =
                        if (fragment.direction == ConnectionDirection.HORIZONTAL) {
                            Offset(
                                fragment.currentPosition.x + index * letterBoxSize,
                                fragment.currentPosition.y
                            )
                        } else {
                            Offset(
                                fragment.currentPosition.x,
                                fragment.currentPosition.y + index * letterBoxSize
                            )
                        }

                    drawRoundRect(
                        color = Color.Gray,
                        topLeft = letterOffset + shadowOffset,
                        size = Size(letterBoxSize, letterBoxSize),
                        cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius)
                    )

                    drawRoundRect(
                        color = primaryColor,
                        topLeft = letterOffset,
                        size = Size(letterBoxSize, letterBoxSize),
                        cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius)
                    )

                    drawRoundRect(
                        color = Color.Black,
                        topLeft = letterOffset,
                        size = Size(letterBoxSize, letterBoxSize),
                        cornerRadius = CornerRadius(letterBoxCornerRadius, letterBoxCornerRadius),
                        style = Stroke(width = 2f)
                    )

                    val textLayoutResult = textMeasurer.measure(
                        text = char.toString(),
                        style = TextStyle(color = Color.White, fontSize = 24.sp)
                    )

                    drawText(
                        textLayoutResult,
                        topLeft = letterOffset + Offset(
                            (letterBoxSize - textLayoutResult.size.width) / 2,
                            (letterBoxSize - textLayoutResult.size.height) / 2
                        )
                    )
                }
            }
        }
    }
}
