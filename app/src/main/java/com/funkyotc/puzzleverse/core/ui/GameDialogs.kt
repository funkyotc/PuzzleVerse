package com.funkyotc.puzzleverse.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@Composable
fun GameHowToDialog(
    instructions: String,
    title: String = "How To Play",
    onDismiss: () -> Unit
) {
    val soundManager = LocalSoundManager.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(instructions) },
        confirmButton = {
            TextButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onDismiss()
            }) {
                Text("OK")
            }
        }
    )
}

@Composable
fun GameConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String = "Confirm",
    cancelLabel: String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val soundManager = LocalSoundManager.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onConfirm()
            }) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                onDismiss()
            }) {
                Text(cancelLabel)
            }
        }
    )
}

@Composable
fun GameEndDialog(
    isWon: Boolean,
    title: String,
    message: String,
    mode: String?,
    onMainMenuClick: () -> Unit,
    onPlayAgainClick: (() -> Unit)? = null,
    onNextPuzzleClick: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    streakCount: Int? = null,
    gameId: String? = null
) {
    val soundManager = LocalSoundManager.current
    val context = LocalContext.current
    val streakRepo = remember { StreakRepository(context) }
    val saveStateRepo = remember { com.funkyotc.puzzleverse.core.data.SaveStateRepository(context) }
    val effectiveStreak = streakCount ?: (if (gameId != null) streakRepo.getStreak(gameId).count else null)

    androidx.compose.runtime.LaunchedEffect(isWon, gameId) {
        if (isWon && gameId != null) {
            saveStateRepo.clearSaveState(gameId)
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = if (isWon) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = if (isWon) "Success" else "Failure",
                    tint = if (isWon) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                Text(text = title)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (isWon && mode == "daily") {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Daily Challenge Completed",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "Daily Challenge Completed!",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                if (effectiveStreak != null && effectiveStreak > 0) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Whatshot,
                                            contentDescription = "Streak",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Streak: $effectiveStreak ${if (effectiveStreak == 1) "day" else "days"}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Text(text = message)
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                when (mode) {
                    "daily" -> {
                        Button(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                onMainMenuClick()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Main Menu")
                        }
                        if (onPlayAgainClick != null) {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    onPlayAgainClick()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Random Puzzles")
                            }
                        }
                    }
                    "puzzle" -> {
                        Button(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                onMainMenuClick() // Maps to "Back to List" in puzzle mode
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Back to List")
                        }
                        if (onNextPuzzleClick != null) {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    onNextPuzzleClick()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Next Puzzle")
                            }
                        }
                    }
                    else -> {
                        // Standard mode
                        if (onPlayAgainClick != null) {
                            Button(
                                onClick = {
                                    soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                    onPlayAgainClick()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(if (isWon) "Play Again" else "Try Again")
                            }
                        }
                        TextButton(
                            onClick = {
                                soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                                onMainMenuClick()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Main Menu")
                        }
                    }
                }
            }
        }
    )
}
