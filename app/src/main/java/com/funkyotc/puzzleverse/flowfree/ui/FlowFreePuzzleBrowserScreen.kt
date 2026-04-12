package com.funkyotc.puzzleverse.flowfree.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.flowfree.data.FlowFreePregenerated
import com.funkyotc.puzzleverse.flowfree.data.PregeneratedPuzzle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowFreePuzzleBrowserScreen(
    navController: NavController
) {
    val puzzlesByDifficulty = remember { FlowFreePregenerated.PUZZLES_BY_DIFFICULTY }
    val difficultyOrder = listOf("Easy", "Medium", "Hard", "Expert")
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flow Puzzles") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        ) {
            // Difficulty tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                difficultyOrder.forEachIndexed { index, diff ->
                    val count = puzzlesByDifficulty[diff]?.size ?: 0
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text("$diff ($count)") }
                    )
                }
            }

            // Puzzle list for selected difficulty
            val currentDifficulty = difficultyOrder[selectedTab]
            val puzzles = puzzlesByDifficulty[currentDifficulty] ?: emptyList()

            // Group by size
            val puzzlesBySize = puzzles.groupBy { it.size }.toSortedMap()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                puzzlesBySize.forEach { (size, sizePuzzles) ->
                    // Size subheading
                    item {
                        Text(
                            text = "${size}×${size} Grid",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        HorizontalDivider()
                    }

                    // Puzzle cards
                    items(sizePuzzles) { puzzle ->
                        PuzzleCard(
                            puzzle = puzzle,
                            onClick = {
                                navController.navigate("game/flowfree/puzzle/${puzzle.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PuzzleCard(puzzle: PregeneratedPuzzle, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
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
                    text = puzzle.id.replace("_", " ").replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${puzzle.size}×${puzzle.size} • ${puzzle.dots.size} colors",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "▶",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
