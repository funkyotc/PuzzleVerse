package com.funkyotc.puzzleverse.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.funkyotc.puzzleverse.core.data.LocalSaveStateRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.sudoku.data.SudokuRepository
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(navController: NavController, gameId: String?, streakRepository: StreakRepository) {
    val soundManager = LocalSoundManager.current
    val saveStateRepository = LocalSaveStateRepository.current
    val context = LocalContext.current

    if (gameId == null) {
        navController.popBackStack()
        return
    }

    val gameName = gameId.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val sudokuRepository = remember { SudokuRepository(context) }
    val saveMeta = saveStateRepository.getSaveState(gameId)
    val hasSavedGame = saveMeta != null

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
            if (saveMeta != null) {
                val modeLabel = saveMeta.mode.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                MenuCard(text = "Resume Saved Game ($modeLabel)") {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    val route = if (saveMeta.puzzleId != null) {
                        "game/$gameId/puzzle/${saveMeta.puzzleId}"
                    } else {
                        "game/$gameId/${saveMeta.mode}"
                    }
                    navController.navigate(route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                MenuCard(text = "New Game") {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    saveStateRepository.clearSaveState(gameId)
                    navController.navigate("game/$gameId/standard/new")
                }
            } else {
                when (gameId) {
                    "sudoku" -> {
                        MenuCard(text = "New Game") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/sudoku/standard")
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
                    "cubeshooter" -> {
                        MenuCard(text = "Easy (15x15)") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/cubeshooter/easy")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        MenuCard(text = "Medium (20x20)") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/cubeshooter/medium")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        MenuCard(text = "Hard (30x30)") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/cubeshooter/hard")
                        }
                    }
                    else -> {
                        MenuCard(text = "Play Standard") {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            navController.navigate("game/$gameId/standard")
                        }
                    }
                }
            }

            val hasBrowsePuzzles = listOf(
                "bonza", "flowfree", "kakuro", "nonogram", "constellations",
                "hashi", "shikaku", "cubeshooter", "pullpin", "watersort",
                "woodnuts", "hexasort", "hexastack", "chess", "tangrams", "arrowescape", "sudoku"
            ).contains(gameId)

            if (hasBrowsePuzzles) {
                Spacer(modifier = Modifier.height(16.dp))
                MenuCard(text = "Browse Puzzles") {
                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                    navController.navigate("$gameId/puzzles")
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
    ElevatedCard(
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
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
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