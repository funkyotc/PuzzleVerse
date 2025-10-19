package com.funkyotc.puzzleverse.bonza.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.bonza.data.DraggableWord
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModelFactory
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonzaScreen(
    navController: NavController, 
    mode: String? = null, 
    forceNewGame: Boolean = false, 
    context: Context = LocalContext.current, 
    streakRepository: StreakRepository
) {
    val bonzaViewModel: BonzaViewModel = viewModel(factory = BonzaViewModelFactory(context, mode, forceNewGame, streakRepository))
    val draggableWords by bonzaViewModel.draggableWords.collectAsState()
    val isGameWon by bonzaViewModel.isGameWon.collectAsState()
    val puzzleClue by bonzaViewModel.puzzleClue.collectAsState()
    val tileSize = 48.dp
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }
    var showNewGameDialog by remember { mutableStateOf(false) }

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

    if (showNewGameDialog) {
        AlertDialog(
            onDismissRequest = { showNewGameDialog = false },
            title = { Text("New Puzzle") },
            text = { Text("Are you sure you want to start a new puzzle? Your current progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    bonzaViewModel.newGame()
                    showNewGameDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewGameDialog = false }) {
                    Text("Cancel")
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
                actions = {
                    if (mode == "standard") {
                        IconButton(onClick = { showNewGameDialog = true }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (draggableWords.isEmpty()) {
                CircularProgressIndicator()
            } else {
                Text(text = puzzleClue, modifier = Modifier.padding(bottom = 16.dp).align(Alignment.TopCenter), style = MaterialTheme.typography.headlineSmall)
                draggableWords.forEach { draggableWord ->
                    WordView(
                        word = draggableWord,
                        tileSize = tileSize,
                        onDrag = bonzaViewModel::onDrag,
                        onDragEnd = { bonzaViewModel.onDragEnd(draggableWord.id, tileSizePx) }
                    )
                }
            }
        }
    }
}

@Composable
fun WordView(
    word: DraggableWord,
    tileSize: Dp,
    onDrag: (String, Float, Float) -> Unit,
    onDragEnd: () -> Unit
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    val dragModifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = { onDragEnd() },
            onDrag = { change, dragAmount ->
                change.consume()
                onDrag(word.id, dragAmount.x, dragAmount.y)
            }
        )
    }

    val startX = (word.bonzaWord.x * tileSizePx) + word.offsetX
    val startY = (word.bonzaWord.y * tileSizePx) + word.offsetY

    val wordModifier = Modifier.offset { IntOffset(startX.roundToInt(), startY.roundToInt()) }.then(dragModifier)

    if (word.bonzaWord.isHorizontal) {
        Row(modifier = wordModifier) {
            word.bonzaWord.letters.forEach {
                LetterView(letter = it, tileSize = tileSize)
            }
        }
    } else {
        Column(modifier = wordModifier) {
            word.bonzaWord.letters.forEach {
                LetterView(letter = it, tileSize = tileSize)
            }
        }
    }
}

@Composable
fun LetterView(letter: Char, tileSize: Dp) {
    Surface(
        modifier = Modifier.size(tileSize),
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = letter.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
