package com.funkyotc.puzzleverse.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle
import com.funkyotc.puzzleverse.core.data.PuzzleCompletionRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleBrowserScreen(
    title: String,
    gameName: String,
    navController: NavController,
    puzzlesByDifficulty: Map<String, List<BrowseablePuzzle>>,
    difficultyOrder: List<String>,
    onPuzzleClick: (BrowseablePuzzle) -> Unit
) {
    val context = LocalContext.current
    val completionRepo = remember { PuzzleCompletionRepository(context, gameName) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var completedIds by remember { mutableStateOf(completionRepo.getCompletedIds()) }

    LaunchedEffect(navController.currentBackStackEntry) {
        completedIds = completionRepo.getCompletedIds()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                difficultyOrder.forEachIndexed { index, diff ->
                    val puzzles = puzzlesByDifficulty[diff] ?: emptyList()
                    val completed = puzzles.count { it.id in completedIds }
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { 
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                GameTypePreview(gameName = gameName, isCompleted = false)
                                Text("$diff ($completed/${puzzles.size})") 
                            }
                        }
                    )
                }
            }

            val currentDifficulty = difficultyOrder[selectedTab]
            val puzzles = puzzlesByDifficulty[currentDifficulty] ?: emptyList()

            // Determine if we should use a dense grid (for numbers) or a wider grid (for names)
            val isNumerical = puzzles.all { it.label.contains("Level") || it.label.contains("puzzle") }
            val columns = if (isNumerical) 4 else 2

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(puzzles) { index, puzzle ->
                    val isCompleted = puzzle.id in completedIds
                    // Sequential locking: unlocked if first, or if previous is completed
                    val isLocked = index > 0 && puzzles[index - 1].id !in completedIds
                    
                    PuzzleBrowserItem(
                        puzzle = puzzle,
                        gameName = gameName,
                        isCompleted = isCompleted,
                        isLocked = isLocked,
                        onClick = { if (!isLocked) onPuzzleClick(puzzle) }
                    )
                }
            }
        }
    }
}

@Composable
fun PuzzleBrowserItem(
    puzzle: BrowseablePuzzle,
    gameName: String,
    isCompleted: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val containerColor = when {
        isLocked -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        isCompleted -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val contentColor = when {
        isLocked -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        isCompleted -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = !isLocked,
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        )
    ) {
        Box(
            modifier = Modifier.padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    GameTypePreview(gameName = gameName, isCompleted = isCompleted)
                }
                
                Text(
                    text = puzzle.shortLabel,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                if (!isLocked && !isCompleted && puzzle.shortLabel == puzzle.label) {
                    // Show subtitle only if it's not a simple number grid, to save space
                    Text(
                        text = puzzle.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
                
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun GameTypePreview(gameName: String, isCompleted: Boolean) {
    val tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val iconSize = 24.dp
    
    // Simple visual representations for each game type
    when (gameName.lowercase()) {
        "sudoku" -> Text("▦", fontSize = 20.sp, color = tint)
        "flow", "flowfree" -> Text("⌇", fontSize = 20.sp, color = tint, fontWeight = FontWeight.Bold)
        "bonza" -> Text("🧩", fontSize = 20.sp, color = tint)
        "kakuro" -> Text("◩", fontSize = 20.sp, color = tint)
        "nonogram" -> Text("⬛", fontSize = 20.sp, color = tint)
        "constellations" -> Text("✨", fontSize = 20.sp, color = tint)
        "shapes" -> Text("▲", fontSize = 20.sp, color = tint)
        "minesweeper" -> Text("💣", fontSize = 20.sp, color = tint)
        else -> Text("●", fontSize = 20.sp, color = tint)
    }
}
