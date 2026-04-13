package com.funkyotc.puzzleverse.kakuro.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.kakuro.data.KakuroCompletionRepository
import com.funkyotc.puzzleverse.kakuro.data.KakuroPregenerated
import com.funkyotc.puzzleverse.kakuro.data.PregeneratedKakuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakuroPuzzleBrowserScreen(navController: NavController) {
    val context = LocalContext.current
    val completionRepo = remember { KakuroCompletionRepository(context) }
    val puzzlesByDifficulty = remember { KakuroPregenerated.PUZZLES_BY_DIFFICULTY }
    val difficultyOrder = listOf("Easy", "Medium", "Hard")
    var selectedTab by remember { mutableIntStateOf(0) }
    var completedIds by remember { mutableStateOf(completionRepo.getCompletedIds()) }

    LaunchedEffect(navController.currentBackStackEntry) {
        completedIds = completionRepo.getCompletedIds()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kakuro Puzzles") },
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
                        text = { Text("$diff ($completed/${puzzles.size})") }
                    )
                }
            }

            val currentDifficulty = difficultyOrder[selectedTab]
            val puzzles = puzzlesByDifficulty[currentDifficulty] ?: emptyList()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(puzzles) { puzzle ->
                    val isCompleted = puzzle.id in completedIds
                    KakuroPuzzleCard(
                        puzzle = puzzle,
                        isCompleted = isCompleted,
                        onClick = {
                            navController.navigate("game/kakuro/puzzle/${puzzle.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun KakuroPuzzleCard(puzzle: PregeneratedKakuro, isCompleted: Boolean, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = puzzle.id.replace("_", " ").replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${puzzle.cols}x${puzzle.rows} • ${puzzle.difficulty}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("▶", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
