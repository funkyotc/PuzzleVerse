package com.funkyotc.puzzleverse.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.funkyotc.puzzleverse.LocalSoundManager
import com.funkyotc.puzzleverse.core.audio.SoundManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardGameLayout(
    title: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = { navController.popBackStack() },
    onHowToClick: (() -> Unit)? = null,
    onNewGameClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    topBarColors: TopAppBarColors? = null,
    bottomBar: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val soundManager = LocalSoundManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = containerColor,
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                colors = topBarColors ?: TopAppBarDefaults.topAppBarColors(),
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = {
                        soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                        onBackPress()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Prepend generic action slots
                    actions()
                    
                    onHowToClick?.let {
                        IconButton(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            it()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "How To Play"
                            )
                        }
                    }
                    
                    onNewGameClick?.let {
                        IconButton(onClick = {
                            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
                            it()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = "New Game"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
