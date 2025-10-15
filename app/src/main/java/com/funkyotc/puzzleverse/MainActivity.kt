package com.funkyotc.puzzleverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.funkyotc.puzzleverse.bonza.ui.BonzaScreen
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.constellations.ui.ConstellationsScreen
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.settings.ui.SettingsScreen
import com.funkyotc.puzzleverse.shapes.ui.ShapesScreen
import com.funkyotc.puzzleverse.ui.screens.detail.GameDetailScreen
import com.funkyotc.puzzleverse.ui.screens.game.GameScreen
import com.funkyotc.puzzleverse.ui.screens.home.HomeScreen
import com.funkyotc.puzzleverse.ui.screens.sudoku.SudokuScreen
import com.funkyotc.puzzleverse.ui.theme.PuzzleVerseTheme
import com.funkyotc.puzzleverse.wordle.ui.WordleScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val LocalSoundManager = staticCompositionLocalOf<SoundManager> { error("No SoundManager provided") }

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundManager = SoundManager(this)
        soundManager.loadSounds()

        setContent {
            val context = LocalContext.current
            val settingsRepository = remember { SettingsRepository(context) }
            val isDarkTheme by settingsRepository.isDarkTheme.collectAsState(initial = false)

            PuzzleVerseTheme(darkTheme = isDarkTheme) {
                CompositionLocalProvider(LocalSoundManager provides soundManager) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        PuzzleVerseNavHost(settingsRepository = settingsRepository)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PuzzleVerseNavHost(settingsRepository: SettingsRepository) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController, 
        startDestination = "home",
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable("home") { HomeScreen(navController) }
        composable("settings") { SettingsScreen(settingsRepository) }
        composable(
            "gameDetail/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.StringType })
        ) { backStackEntry ->
            GameDetailScreen(
                navController = navController,
                gameId = backStackEntry.arguments?.getString("gameId")
            )
        }
        composable(
            "game/{gameId}/{mode}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.StringType },
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val mode = backStackEntry.arguments?.getString("mode")

            when (gameId) {
                "sudoku" -> SudokuScreen(mode = mode)
                "bonza" -> BonzaScreen(mode = mode)
                "constellations" -> ConstellationsScreen()
                "shapes" -> ShapesScreen()
                "wordle" -> WordleScreen()
                else -> {
                    GameScreen(
                        gameId = gameId,
                        mode = mode
                    )
                }
            }
        }
    }
}