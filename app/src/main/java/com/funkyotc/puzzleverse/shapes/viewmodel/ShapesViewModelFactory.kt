package com.funkyotc.puzzleverse.shapes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShapesViewModelFactory(
    private val context: Context,
    private val mode: String?,
    private val puzzleId: String? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShapesViewModel::class.java)) {
            return ShapesViewModel(context, mode, puzzleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
