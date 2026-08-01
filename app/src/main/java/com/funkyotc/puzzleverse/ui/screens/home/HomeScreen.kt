package com.funkyotc.puzzleverse.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.core.data.LocalSaveStateRepository
import com.funkyotc.puzzleverse.core.ui.animateTapFeedback
import com.funkyotc.puzzleverse.streak.data.StreakRepository

data class Game(val id: String, val name: String)

val games = listOf(
    Game("sudoku", "Sudoku"),
    Game("bonza", "Bonza"),
    Game("constellations", "Constellations"),
    Game("wordle", "Wordle"),
    Game("tfe", "2048"),
    Game("minesweeper", "Minesweeper"),
    Game("nonogram", "Nonogram"),
    Game("kakuro", "Kakuro"),
    Game("flowfree", "Flow Free"),
    Game("shikaku", "Shikaku"),
    Game("cubeshooter", "Cube Shooter"),
    Game("pullpin", "Pull the Pin"),
    Game("watersort", "Water Sort"),
    Game("woodnuts", "Wood Screws"),
    Game("hexasort", "Hexa Sort"),
    Game("hexastack", "Hexa Stack"),
    Game("chess", "Chess"),
    Game("hashi", "Hashi"),
    Game("arrowescape", "Arrow Escape"),
    Game("tangrams", "Tangrams")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, streakRepository: StreakRepository) {
    val soundManager = LocalSoundManager.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val saveStateRepository = LocalSaveStateRepository.current
    val savedGameIds by saveStateRepository.savedGameIds.collectAsState()
    var selectedResumeGame by remember { mutableStateOf<Game?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PuzzleVerse") },
                actions = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("settings")
                    }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Daily Challenges",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                androidx.compose.foundation.lazy.LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    val dailyGames = games
                    items(dailyGames) { game ->
                        val streak = streakRepository.getStreak(game.id)
                        val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                        val isDailyCompleted = streak.lastCompletedEpochDay == today

                        Box(
                            modifier = Modifier
                                .width(160.dp)
                                .height(90.dp)
                        ) {
                            GameCard(
                                game = game,
                                streak = streak.count,
                                isDailyCompleted = isDailyCompleted,
                                hasSaveState = false,
                                enabled = !isDailyCompleted
                            ) {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                navController.navigate("game/${game.id}/daily")
                            }
                        }
                    }
                }
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "All Puzzles",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            items(games) { game ->
                val streak = streakRepository.getStreak(game.id)
                val today = com.funkyotc.puzzleverse.core.todayEpochDay()
                val isDailyCompleted = streak.lastCompletedEpochDay == today
                val hasSave = savedGameIds.contains(game.id)

                Box(
                    modifier = Modifier.height(90.dp)
                ) {
                    GameCard(
                        game = game,
                        streak = streak.count,
                        isDailyCompleted = isDailyCompleted,
                        hasSaveState = hasSave
                    ) {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        if (hasSave) {
                            selectedResumeGame = game
                        } else {
                            navController.navigate("gameDetail/${game.id}")
                        }
                    }
                }
            }
        }

        if (selectedResumeGame != null) {
            val gameToResume = selectedResumeGame!!
            val saveMeta = saveStateRepository.getSaveState(gameToResume.id)
            val modeTitle = saveMeta?.mode?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } ?: "Standard"

            AlertDialog(
                onDismissRequest = { selectedResumeGame = null },
                title = { Text("Resume ${gameToResume.name}?") },
                text = { Text("You have a saved $modeTitle game in progress. Would you like to resume your game or clear the saved progress?") },
                confirmButton = {
                    Button(
                        onClick = {
                            val targetGame = selectedResumeGame!!
                            selectedResumeGame = null
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            val mode = saveMeta?.mode ?: "standard"
                            val puzzleId = saveMeta?.puzzleId
                            val route = if (puzzleId != null) {
                                "game/${targetGame.id}/puzzle/$puzzleId"
                            } else {
                                "game/${targetGame.id}/$mode"
                            }
                            navController.navigate(route)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text("Resume")
                        }
                    }
                },
                dismissButton = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = {
                                val targetGame = selectedResumeGame!!
                                selectedResumeGame = null
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                saveStateRepository.clearSaveState(targetGame.id)
                                when (targetGame.id) {
                                    "sudoku" -> com.funkyotc.puzzleverse.sudoku.data.SudokuRepository(context).saveBoard(com.funkyotc.puzzleverse.sudoku.data.SudokuBoard(emptyList()), "standard_sudoku_board")
                                    "shikaku" -> com.funkyotc.puzzleverse.shikaku.data.ShikakuRepository(context).clearBoard("standard")
                                    "hexasort" -> com.funkyotc.puzzleverse.hexasort.data.HexaSortRepository(context).removeKey("grid")
                                    "hexastack" -> com.funkyotc.puzzleverse.hexastack.data.HexaStackRepository(context).removeKey("standard_hexastack_grid")
                                    "chess" -> com.funkyotc.puzzleverse.chess.data.ChessRepository(context).clearPuzzleState("chess_state")
                                }
                                navController.navigate("gameDetail/${targetGame.id}")
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("Clear")
                            }
                        }
                        TextButton(
                            onClick = { selectedResumeGame = null }
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun GameCard(
    game: Game,
    streak: Int,
    isDailyCompleted: Boolean,
    hasSaveState: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val containerColor = if (isDailyCompleted) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    androidx.compose.material3.ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .animateTapFeedback(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                enabled = enabled,
                onClick = onClick
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (hasSaveState) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = CircleShape
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = "Saved Game",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = game.name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    softWrap = false
                )

                if (isDailyCompleted) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Completed Today",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Completed Today",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            fontSize = 11.sp
                        )
                    }
                }

                if (streak > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Whatshot,
                            contentDescription = "Streak",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "$streak", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}