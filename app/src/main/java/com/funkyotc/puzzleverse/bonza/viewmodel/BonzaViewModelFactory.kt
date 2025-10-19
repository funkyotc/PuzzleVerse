package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funkyotc.puzzleverse.streak.data.StreakRepository

class BonzaViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val forceNewGame: Boolean = false,
    private val streakRepository: StreakRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BonzaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BonzaViewModel(context, mode, forceNewGame, streakRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
