package com.funkyotc.puzzleverse.bonza.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BonzaViewModelFactory(private val context: Context, private val mode: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BonzaViewModel::class.java)) {
            return BonzaViewModel(context, mode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}