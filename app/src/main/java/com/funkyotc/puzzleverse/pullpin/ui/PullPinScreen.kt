package com.funkyotc.puzzleverse.pullpin.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.pullpin.rescue.RescueCampaign
import com.funkyotc.puzzleverse.pullpin.rescue.RescueCampaignScreen
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.streak.data.StreakRepository

@Composable
fun PullPinScreen(navController: NavController, mode: String?, streakRepository: StreakRepository?,
    settingsRepository: SettingsRepository?, puzzleId: String? = null, forceNewGame: Boolean = false) {
    if (puzzleId != null && RescueCampaign.level(puzzleId) == null) {
        AlertDialog(onDismissRequest = { navController.popBackStack() },
            title = { Text("Puzzle replaced") },
            text = { Text("This older Pull the Pin puzzle has been retired. Browse the new king rescue levels.") },
            confirmButton = { TextButton(onClick = { navController.navigate("pullpin/puzzles") {
                popUpTo("game/pullpin/puzzle/{puzzleId}") { inclusive = true }
            } }) { Text("Browse rescues") } },
            dismissButton = { TextButton(onClick = { navController.popBackStack() }) { Text("Back") } })
        return
    }
    RescueCampaignScreen(navController, mode, puzzleId, forceNewGame, streakRepository)
}
