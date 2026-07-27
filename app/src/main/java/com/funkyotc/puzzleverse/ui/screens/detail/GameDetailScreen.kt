package com.funkyotc.puzzleverse.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(navController: NavController, gameId: String?, streakRepository: StreakRepository) {
    val soundManager = LocalSoundManager.current
    val context = LocalContext.current

    if (gameId == null) {
        // Handle null gameId, maybe navigate back or show an error
        navController.popBackStack()
        return
    }

    val gameName = gameId.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val sudokuRepository = remember { SudokuRepository(context) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gameName) },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (gameId) {
                "sudoku" -> {
                    val hasStandardGame = sudokuRepository.loadBoard("standard_sudoku_board") != null
                    if (hasStandardGame) {
                        MenuCard(text = "Resume") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/sudoku/standard")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        MenuCard(text = "New Game") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/sudoku/standard/new")
                        }
                    } else {
                        MenuCard(text = "New Game") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/sudoku/standard")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("sudoku/puzzles")
                    }
                }
                "tfe" -> {
                    MenuCard(text = "Play") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/tfe/standard")
                    }
                }
                "bonza" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/bonza/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("bonza/puzzles")
                    }
                }
                "flowfree" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/flowfree/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("flowfree/puzzles")
                    }
                }
                "kakuro" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/kakuro/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("kakuro/puzzles")
                    }
                }
                "nonogram" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/nonogram/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("nonogram/puzzles")
                    }
                }
                "minesweeper" -> {
                    MenuCard(text = "Easy (9x9)") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/minesweeper/easy")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Medium (16x16)") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/minesweeper/medium")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Hard (16x30)") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/minesweeper/hard")
                    }
                }
                "constellations" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/constellations/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("constellations/puzzles")
                    }
                }
                "hashi" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/hashi/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("hashi/puzzles")
                    }
                }
                "shikaku" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/shikaku/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("shikaku/puzzles")
                    }
                }
                "cubeshooter" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/cubeshooter/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("cubeshooter/puzzles")
                    }
                }
                "pullpin" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/pullpin/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("pullpin/puzzles")
                    }
                }
                "watersort" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/watersort/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("watersort/puzzles")
                    }
                }
                "woodnuts" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/woodnuts/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("woodnuts/puzzles")
                    }
                }
                "hexasort" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/hexasort/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("hexasort/puzzles")
                    }
                }
                "chess" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/chess/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("chess/puzzles")
                    }
                }
                "tangrams" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/tangrams/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("tangrams/puzzles")
                    }
                }
                "arrowescape" -> {
                    MenuCard(text = "Random Puzzle") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/arrowescape/standard")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuCard(text = "Browse Puzzles") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("arrowescape/puzzles")
                    }
                }
                else -> {
                    MenuCard(text = "Standard") {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        navController.navigate("game/$gameId/standard")
                    }
                }
            }

            val streak = streakRepository.getStreak(gameId)
            val today = com.funkyotc.puzzleverse.core.todayEpochDay()
            val isDailyCompleted = streak.lastCompletedEpochDay == today

            Spacer(modifier = Modifier.height(16.dp))
            DailyChallengeMenuCard(
                streakCount = streak.count,
                isDailyCompleted = isDailyCompleted
            ) {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                navController.navigate("game/$gameId/daily")
            }
        }
    }
}

@Composable
fun DailyChallengeMenuCard(
    streakCount: Int,
    isDailyCompleted: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.material3.ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        enabled = !isDailyCompleted,
        onClick = onClick,
        colors = if (isDailyCompleted) {
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        } else {
            CardDefaults.elevatedCardColors()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Daily Challenge",
                    style = MaterialTheme.typography.titleMedium
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
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            if (streakCount > 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Whatshot,
                        contentDescription = "Streak",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$streakCount",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun MenuCard(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    androidx.compose.material3.ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, textAlign = TextAlign.Center)
        }
    }
}