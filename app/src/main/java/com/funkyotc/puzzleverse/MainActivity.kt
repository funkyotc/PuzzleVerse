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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.funkyotc.puzzleverse.ui.screens.bonza.BonzaScreen
import com.funkyotc.puzzleverse.core.audio.SoundManager
import com.funkyotc.puzzleverse.constellations.ui.ConstellationsScreen
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import com.funkyotc.puzzleverse.settings.ui.SettingsScreen
import com.funkyotc.puzzleverse.shapes.ui.ShapesScreen
import com.funkyotc.puzzleverse.streak.data.StreakRepository
import com.funkyotc.puzzleverse.ui.screens.detail.GameDetailScreen
import com.funkyotc.puzzleverse.ui.screens.game.GameScreen
import com.funkyotc.puzzleverse.ui.screens.home.HomeScreen
import com.funkyotc.puzzleverse.ui.screens.sudoku.SudokuScreen
import com.funkyotc.puzzleverse.ui.theme.PuzzleVerseTheme
import com.funkyotc.puzzleverse.wordle.ui.WordleScreen
import com.funkyotc.puzzleverse.tfe.ui.TfeScreen
import com.funkyotc.puzzleverse.minesweeper.ui.MinesweeperScreen
import com.funkyotc.puzzleverse.nonogram.ui.NonogramScreen
import com.funkyotc.puzzleverse.blockpuzzle.ui.BlockPuzzleScreen
import com.funkyotc.puzzleverse.kakuro.ui.KakuroScreen
import com.funkyotc.puzzleverse.flowfree.ui.FlowFreeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val LocalSoundManager = staticCompositionLocalOf<SoundManager> { error("No SoundManager provided") }

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private lateinit var soundManager: SoundManager
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            isLoading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        soundManager = SoundManager(this)
        soundManager.loadSounds()

        setContent {
            val context = LocalContext.current
            val settingsRepository = remember { SettingsRepository(context) }
            val streakRepository = remember { StreakRepository(context) }
            val activeTheme by settingsRepository.activeTheme.collectAsState(initial = "default")

            PuzzleVerseTheme(activeTheme = activeTheme) {
                CompositionLocalProvider(LocalSoundManager provides soundManager) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        PuzzleVerseNavHost(settingsRepository = settingsRepository, streakRepository = streakRepository) {
                            isLoading = false
                        }
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
fun PuzzleVerseNavHost(settingsRepository: SettingsRepository, streakRepository: StreakRepository, onInitialLoad: () -> Unit) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController, 
        startDestination = "home",
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable("home") { 
            onInitialLoad()
            HomeScreen(navController, streakRepository)
        }
        composable("settings") { 
            SettingsScreen(settingsRepository, onBackPress = { navController.popBackStack() })
        }
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
                "sudoku" -> SudokuScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "bonza" -> BonzaScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "constellations" -> ConstellationsScreen(navController = navController, mode = mode, settingsRepository = settingsRepository)
                "shapes" -> ShapesScreen(navController = navController, mode = mode, settingsRepository = settingsRepository)
                "wordle" -> WordleScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "tfe" -> TfeScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "minesweeper" -> MinesweeperScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "nonogram" -> NonogramScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "blockpuzzle" -> BlockPuzzleScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "kakuro" -> KakuroScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "flowfree" -> FlowFreeScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                else -> {
                    GameScreen(
                        navController = navController,
                        gameId = gameId,
                        mode = mode
                    )
                }
            }
        }
        composable(
            "game/{gameId}/{mode}/new",
            arguments = listOf(
                navArgument("gameId") { type = NavType.StringType },
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val mode = backStackEntry.arguments?.getString("mode")

            when (gameId) {
                "sudoku" -> SudokuScreen(navController = navController, mode = mode, forceNewGame = true, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "bonza" -> BonzaScreen(navController = navController, mode = mode, forceNewGame = true, streakRepository = streakRepository, settingsRepository = settingsRepository)
                else -> {
                    // For other games, you might want to handle the "new" case differently
                    GameScreen(
                        navController = navController,
                        gameId = gameId,
                        mode = mode
                    )
                }
            }
        }
    }
}