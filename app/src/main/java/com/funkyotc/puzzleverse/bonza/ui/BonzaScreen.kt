package com.funkyotc.puzzleverse.bonza.ui

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModel
import com.funkyotc.puzzleverse.bonza.viewmodel.BonzaViewModelFactory

@Composable
fun BonzaScreen(mode: String? = null, context: Context = LocalContext.current) {
    val bonzaViewModel: BonzaViewModel = viewModel(factory = BonzaViewModelFactory(context, mode))
    val draggableWords by bonzaViewModel.draggableWords.collectAsState()
    val isGameWon by bonzaViewModel.isGameWon.collectAsState()
    val puzzleClue by bonzaViewModel.puzzleClue.collectAsState()
    val tileSize = 48.dp

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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = puzzleClue)
        draggableWords.forEach { draggableWord ->
            WordView(
                word = draggableWord,
                tileSize = tileSize,
                onDrag = bonzaViewModel::onDrag,
                onDragEnd = { bonzaViewModel.onDragEnd(draggableWord.id, tileSize) }
            )
        }
    }
}

@Composable
fun WordView(
    word: DraggableWord,
    tileSize: Dp,
    onDrag: (String, Dp, Dp) -> Unit,
    onDragEnd: () -> Unit
) {
    LocalDensity.current

    val dragModifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = { onDragEnd() },
            onDrag = { change, dragAmount ->
                change.consume()
                onDrag(word.id, dragAmount.x.toDp(), dragAmount.y.toDp())
            }
        )
    }

    val startX = (word.bonzaWord.x.dp * tileSize.value) + word.offsetX
    val startY = (word.bonzaWord.y.dp * tileSize.value) + word.offsetY

    if (word.bonzaWord.isHorizontal) {
        Row(modifier = Modifier.offset(x = startX, y = startY).then(dragModifier)) {
            word.bonzaWord.letters.forEach {
                LetterView(letter = it, tileSize = tileSize)
            }
        }
    } else {
        Column(modifier = Modifier.offset(x = startX, y = startY).then(dragModifier)) {
            word.bonzaWord.letters.forEach {
                LetterView(letter = it, tileSize = tileSize)
            }
        }
    }
}

@Composable
fun LetterView(letter: Char, tileSize: Dp) {
    Box(
        modifier = Modifier
            .size(tileSize)
            .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter.toString())
    }
}
