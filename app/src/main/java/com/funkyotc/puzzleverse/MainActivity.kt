package com.funkyotc.puzzleverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.funkyotc.puzzleverse.shikaku.ui.ShikakuScreen
import com.funkyotc.puzzleverse.core.ui.PuzzleBrowserScreen
import com.funkyotc.puzzleverse.sudoku.data.SudokuPregenerated
import com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated
import com.funkyotc.puzzleverse.nonogram.data.NonogramPregenerated
import com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated
import com.funkyotc.puzzleverse.bonza.data.BonzaPregenerated
import com.funkyotc.puzzleverse.constellations.data.ConstellationsPregenerated
import com.funkyotc.puzzleverse.shapes.data.ShapesPregenerated
import com.funkyotc.puzzleverse.shikaku.data.ShikakuPregenerated
import com.funkyotc.puzzleverse.cubeshooter.ui.CubeShooterScreen
import com.funkyotc.puzzleverse.cubeshooter.data.CubeShooterPregenerated
import com.funkyotc.puzzleverse.woodnuts.ui.WoodNutsScreen
import com.funkyotc.puzzleverse.woodnuts.data.WoodNutsPregenerated
import com.funkyotc.puzzleverse.watersort.ui.WaterSortScreen
import com.funkyotc.puzzleverse.watersort.data.WaterSortPregenerated
import com.funkyotc.puzzleverse.pullpin.ui.PullPinScreen
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.hexasort.ui.HexaSortScreen
import com.funkyotc.puzzleverse.hexasort.data.HexaSortPregenerated
import com.funkyotc.puzzleverse.chess.ui.ChessScreen
import com.funkyotc.puzzleverse.chess.data.ChessPregenerated
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
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 300 }, animationSpec = tween(220)) +
                    fadeIn(animationSpec = tween(220))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -300 }, animationSpec = tween(220)) +
                    fadeOut(animationSpec = tween(220))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -300 }, animationSpec = tween(220)) +
                    fadeIn(animationSpec = tween(220))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 300 }, animationSpec = tween(220)) +
                    fadeOut(animationSpec = tween(220))
        }
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
                gameId = backStackEntry.arguments?.getString("gameId"),
                streakRepository = streakRepository
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
                "constellations" -> ConstellationsScreen(navController = navController, mode = mode, settingsRepository = settingsRepository, streakRepository = streakRepository)
                "shapes" -> ShapesScreen(navController = navController, mode = mode, settingsRepository = settingsRepository, streakRepository = streakRepository)
                "wordle" -> WordleScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "tfe" -> TfeScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "minesweeper" -> MinesweeperScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "nonogram" -> NonogramScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "blockpuzzle" -> BlockPuzzleScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "kakuro" -> KakuroScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "flowfree" -> FlowFreeScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "shikaku" -> ShikakuScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "cubeshooter" -> CubeShooterScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "pullpin" -> PullPinScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "watersort" -> WaterSortScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "woodnuts" -> WoodNutsScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "hexasort" -> HexaSortScreen(navController = navController, mode = mode, streakRepository = streakRepository!!, settingsRepository = settingsRepository!!)
                "chess" -> ChessScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
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
                "shikaku" -> ShikakuScreen(navController = navController, mode = mode, forceNewGame = true, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "cubeshooter" -> CubeShooterScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "pullpin" -> PullPinScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "watersort" -> WaterSortScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "woodnuts" -> WoodNutsScreen(navController = navController, mode = mode, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "hexasort" -> HexaSortScreen(navController = navController, mode = mode, forceNewGame = true, streakRepository = streakRepository, settingsRepository = settingsRepository)
                "chess" -> ChessScreen(navController = navController, mode = mode, forceNewGame = true, streakRepository = streakRepository, settingsRepository = settingsRepository)
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
        composable("bonza/puzzles") {
            PuzzleBrowserScreen(
                title = "Bonza Puzzles",
                gameName = "Bonza",
                navController = navController,
                puzzlesByDifficulty = BonzaPregenerated.PUZZLES_BY_THEME,
                difficultyOrder = listOf("Animals", "Colors", "Space"),
                onPuzzleClick = { puzzle -> navController.navigate("game/bonza/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/bonza/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            BonzaScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("cubeshooter/puzzles") {
            PuzzleBrowserScreen(
                title = "Cube Shooter Puzzles",
                gameName = "Cube Shooter",
                navController = navController,
                puzzlesByDifficulty = CubeShooterPregenerated.LEVELS_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/cubeshooter/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/cubeshooter/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            CubeShooterScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("pullpin/puzzles") {
            PuzzleBrowserScreen(
                title = "Pull the Pin Puzzles",
                gameName = "PullPin",
                navController = navController,
                puzzlesByDifficulty = PullPinPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard", "Expert"),
                onPuzzleClick = { puzzle -> navController.navigate("game/pullpin/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/pullpin/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            PullPinScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("flowfree/puzzles") {
            PuzzleBrowserScreen(
                title = "Flow Puzzles",
                gameName = "Flow",
                navController = navController,
                puzzlesByDifficulty = FlowFreePregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard", "Expert"),
                onPuzzleClick = { puzzle -> navController.navigate("game/flowfree/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/flowfree/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            FlowFreeScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("sudoku/puzzles") {
            PuzzleBrowserScreen(
                title = "Sudoku Puzzles",
                gameName = "Sudoku",
                navController = navController,
                puzzlesByDifficulty = SudokuPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard", "Expert"),
                onPuzzleClick = { puzzle -> navController.navigate("game/sudoku/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/sudoku/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            SudokuScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("kakuro/puzzles") {
            PuzzleBrowserScreen(
                title = "Kakuro Puzzles",
                gameName = "Kakuro",
                navController = navController,
                puzzlesByDifficulty = KakuroPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/kakuro/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/kakuro/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            KakuroScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("nonogram/puzzles") {
            PuzzleBrowserScreen(
                title = "Nonogram Puzzles",
                gameName = "Nonogram",
                navController = navController,
                puzzlesByDifficulty = NonogramPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/nonogram/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/nonogram/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            NonogramScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("constellations/puzzles") {
            PuzzleBrowserScreen(
                title = "Constellations Puzzles",
                gameName = "Constellations",
                navController = navController,
                puzzlesByDifficulty = ConstellationsPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/constellations/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/constellations/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            ConstellationsScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                settingsRepository = settingsRepository,
                streakRepository = streakRepository
            )
        }
        composable("shapes/puzzles") {
            PuzzleBrowserScreen(
                title = "Shapes Puzzles",
                gameName = "Shapes",
                navController = navController,
                puzzlesByDifficulty = ShapesPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/shapes/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/shapes/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            ShapesScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                settingsRepository = settingsRepository,
                streakRepository = streakRepository
            )
        }
        composable("shikaku/puzzles") {
            PuzzleBrowserScreen(
                title = "Shikaku Puzzles",
                gameName = "Shikaku",
                navController = navController,
                puzzlesByDifficulty = ShikakuPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/shikaku/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/shikaku/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            ShikakuScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("watersort/puzzles") {
            PuzzleBrowserScreen(
                title = "Water Sort Puzzles",
                gameName = "Water Sort",
                navController = navController,
                puzzlesByDifficulty = WaterSortPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/watersort/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/watersort/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            WaterSortScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("woodnuts/puzzles") {
            PuzzleBrowserScreen(
                title = "Wood Nuts Puzzles",
                gameName = "Wood Nuts",
                navController = navController,
                puzzlesByDifficulty = WoodNutsPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/woodnuts/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/woodnuts/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            WoodNutsScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
        composable("hexasort/puzzles") {
            PuzzleBrowserScreen(
                title = "Hexa Sort Puzzles",
                gameName = "Hexa Sort",
                navController = navController,
                puzzlesByDifficulty = HexaSortPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/hexasort/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/hexasort/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            HexaSortScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository!!,
                settingsRepository = settingsRepository!!
            )
        }
        composable("chess/puzzles") {
            PuzzleBrowserScreen(
                title = "Chess Puzzles",
                gameName = "Chess",
                navController = navController,
                puzzlesByDifficulty = ChessPregenerated.PUZZLES_BY_DIFFICULTY,
                difficultyOrder = listOf("Easy", "Medium", "Hard"),
                onPuzzleClick = { puzzle -> navController.navigate("game/chess/puzzle/${puzzle.id}") }
            )
        }
        composable(
            "game/chess/puzzle/{puzzleId}",
            arguments = listOf(navArgument("puzzleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId")
            ChessScreen(
                navController = navController,
                mode = "puzzle",
                puzzleId = puzzleId,
                streakRepository = streakRepository,
                settingsRepository = settingsRepository
            )
        }
    }
}