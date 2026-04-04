package com.funkyotc.puzzleverse.wordle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository

class WordleViewModelFactory(
    private val mode: String?,
    private val streakRepository: StreakRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordleViewModel(mode, streakRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
