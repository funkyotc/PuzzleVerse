package com.funkyotc.puzzleverse

import com.github.bhlangonijr.chesslib.Square
import org.junit.Test

class TestSquareValue {
    @Test
    fun testValue() {
        val sq = Square.A8
        println("Value of A8 is: '${sq.value()}'")
        println("Name of A8 is: '${sq.name}'")
    }
}
