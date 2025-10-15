package com.funkyotc.puzzleverse.bonza.data

data class BonzaWord(
    val word: String,
    val letters: List<Char>,
    val x: Int,
    val y: Int,
    val isHorizontal: Boolean
)
