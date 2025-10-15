package com.funkyotc.puzzleverse.sudoku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SudokuViewModelFactory(private val context: Context, private val mode: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SudokuViewModel::class.java)) {
            return SudokuViewModel(context, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
