package com.funkyotc.puzzleverse.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funkyotc.puzzleverse.settings.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    val activeTheme: StateFlow<String> = repository.activeTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "default")

    val unlockedThemes: StateFlow<Set<String>> = repository.unlockedThemes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), setOf("default", "dark", "light"))

    val totalWins: StateFlow<Int> = repository.totalWins
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setActiveTheme(theme: String) {
        viewModelScope.launch {
            repository.setActiveTheme(theme)
        }
    }
}