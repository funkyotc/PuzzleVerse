package com.funkyotc.puzzleverse.wordle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.wordle.model.GameStatus
import com.funkyotc.puzzleverse.wordle.model.LetterState
import com.funkyotc.puzzleverse.wordle.model.WordleLetter
import com.funkyotc.puzzleverse.wordle.viewmodel.WordleViewModel

import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.wordle.viewmodel.WordleViewModelFactory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import androidx.compose.ui.platform.LocalContext

val CorrectColor = Color(0xFF4DB6AC) // Aesthetic Teal/Green
val PresentColor = Color(0xFFFFB74D) // Aesthetic Amber/Orange
val AbsentColor = Color(0xFF546E7A)  // Slate Gray
val EmptyColor = Color(0xFF263238)   // Deep Blue Gray
val TextColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordleScreen(
    navController: NavController,
    mode: String? = "standard",
    streakRepository: StreakRepository,
    settingsRepository: SettingsRepository,
    context: android.content.Context = LocalContext.current,
    viewModel: WordleViewModel = viewModel(
        factory = WordleViewModelFactory(mode, streakRepository, context) 
    )
) {
    val gameState by viewModel.wordleState.collectAsState()
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHintDialog by remember { mutableStateOf(false) }

    if (showHintDialog) {
        AlertDialog(
            onDismissRequest = { showHintDialog = false },
            title = { Text("Use a Hint?") },
            text = { Text("Are you sure you want to use a hint to reveal part of the puzzle?") },
            confirmButton = {
                TextButton(onClick = {
                    showHintDialog = false
                    viewModel.hint()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showHintDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showHowToDialog) {
        AlertDialog(
            onDismissRequest = { showHowToDialog = false },
            title = { Text("How To Play") },
            text = { Text("Guess the word in 6 tries. Colors show if the letter is correct, in the wrong place, or not in the word.") },
            confirmButton = {
                TextButton(onClick = { showHowToDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    val state = gameState
    LaunchedEffect(state?.gameStatus) {
        if (state?.gameStatus == GameStatus.WON) {
            settingsRepository.addWin()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wordle", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showHowToDialog = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "How To")
                    }
                    IconButton(onClick = { showHintDialog = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Hint")
                    }
                    if (mode != "daily") {
                        IconButton(onClick = { viewModel.startNewGame() }) {
                            Icon(Icons.Filled.Shuffle, contentDescription = "New Puzzle")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E272C),
                    titleContentColor = TextColor,
                    navigationIconContentColor = TextColor,
                    actionIconContentColor = TextColor
                )
            )
        },
        containerColor = Color(0xFF161C20)
    ) { paddingValues ->
            val state = gameState
            if (state == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
                return@Scaffold
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Game Grid
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    state.guesses.forEach { guess ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            guess.letters.forEach { letter ->
                                WordleTile(letter)
                            }
                        }
                    }
                }

                // Temporary Feedback (e.g. "Not enough letters")
                val feedback = state.missingFeedback
                if (feedback != null) {
                    Text(
                        text = feedback,
                        color = Color(0xFFEF5350),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Keyboard
                WordleKeyboard(
                    keyboardState = state.keyboardState,
                    onLetterTyped = { viewModel.onLetterTyped(it) },
                    onBackspace = { viewModel.onBackspace() },
                    onSubmit = { viewModel.onSubmitGuess() }
                )

                // Dialogs
                if (state.gameStatus == GameStatus.WON) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text("Magnificent!") },
                        text = { Text("You discovered the word correctly!") },
                        confirmButton = {
                            Button(onClick = { viewModel.startNewGame() }) {
                                Text("Play Again")
                            }
                        }
                    )
                } else if (state.gameStatus == GameStatus.LOST) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text("Game Over") },
                        text = { Text("The correct word was ${state.solution}") },
                        confirmButton = {
                            Button(onClick = { viewModel.startNewGame() }) {
                                Text("Play Again")
                            }
                        }
                    )
                }
            }
        }
    }

@Composable
fun WordleTile(letter: WordleLetter) {
    val bgColor = when (letter.state) {
        LetterState.CORRECT -> CorrectColor
        LetterState.PRESENT -> PresentColor
        LetterState.ABSENT -> AbsentColor
        LetterState.EMPTY -> EmptyColor
    }
    
    val borderColor = if (letter.state == LetterState.EMPTY && letter.char != ' ') {
        Color.White.copy(alpha = 0.5f)
    } else {
        bgColor
    }

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (letter.char != ' ') {
            Text(
                text = letter.char.toString(),
                color = TextColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun WordleKeyboard(
    keyboardState: Map<Char, LetterState>,
    onLetterTyped: (Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit
) {
    val rows = listOf(
        listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'),
        listOf('A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'),
        listOf('Z', 'X', 'C', 'V', 'B', 'N', 'M')
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        rows.forEachIndexed { index, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (index == 2) {
                    KeyboardActionKey(text = "ENTER", onClick = onSubmit)
                }
                
                row.forEach { char ->
                    val state = keyboardState[char] ?: LetterState.EMPTY
                    KeyboardKey(char = char, state = state) {
                        onLetterTyped(char)
                    }
                }
                
                if (index == 2) {
                    KeyboardActionKey(text = "DEL", onClick = onBackspace)
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(char: Char, state: LetterState, onClick: () -> Unit) {
    val bgColor = when (state) {
        LetterState.CORRECT -> CorrectColor
        LetterState.PRESENT -> PresentColor
        LetterState.ABSENT -> AbsentColor
        LetterState.EMPTY -> Color(0xFF37474F) // Light dark for keys
    }

    Box(
        modifier = Modifier
            .height(54.dp)
            .width(32.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char.toString(),
            color = TextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun KeyboardActionKey(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(54.dp)
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF37474F))
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}