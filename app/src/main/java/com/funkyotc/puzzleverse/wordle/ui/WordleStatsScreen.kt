package com.funkyotc.puzzleverse.wordle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.funkyotc.puzzleverse.wordle.data.WordleStatsRepository

@Composable
fun WordleStatsDialog(
    statsRepository: WordleStatsRepository,
    onDismiss: () -> Unit
) {
    val gamesPlayed = statsRepository.getGamesPlayed()
    val gamesWon = statsRepository.getGamesWon()
    val winRate = if (gamesPlayed > 0) (gamesWon * 100 / gamesPlayed) else 0
    val currentStreak = statsRepository.getCurrentStreak()
    val maxStreak = statsRepository.getMaxStreak()
    val guessDist = statsRepository.getGuessDistribution()
    val maxDist = guessDist.values.maxOrNull()?.coerceAtLeast(1) ?: 1

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STATISTICS",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(value = gamesPlayed.toString(), label = "Played")
                    StatItem(value = "$winRate%", label = "Win %")
                    StatItem(value = currentStreak.toString(), label = "Current Streak")
                    StatItem(value = maxStreak.toString(), label = "Max Streak")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "GUESS DISTRIBUTION",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    guessDist.forEach { (guessNum, count) ->
                        GuessDistBar(guessNum = guessNum, count = count, maxCount = maxDist)
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Use Outfit font if available, but for now stick to default
        Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(text = label, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp)
    }
}

@Composable
fun GuessDistBar(guessNum: Int, count: Int, maxCount: Int) {
    val barColor = if (count > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = guessNum.toString(), modifier = Modifier.width(12.dp), style = MaterialTheme.typography.bodySmall)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            val progress = count.toFloat() / maxCount
            // Ensure some minimum width for zero count bar with background
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceAtLeast(0.07f))
                    .background(barColor, RoundedCornerShape(2.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = count.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
