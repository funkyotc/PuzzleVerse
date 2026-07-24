package com.funkyotc.puzzleverse.shapes.util

import android.util.Log
import com.funkyotc.puzzleverse.shapes.data.PregeneratedShape
import kotlin.math.abs

/**
 * Debug-only utility to validate all tangram puzzles at startup.
 */
object TangramVerifier {

    private const val TAG = "TangramVerifier"

    fun verifyAllPuzzles(puzzles: List<PregeneratedShape>): List<String> {
        val errors = mutableListOf<String>()
        for (puzzle in puzzles) {
            val puzzleErrors = verifyPuzzle(puzzle)
            if (puzzleErrors.isNotEmpty()) {
                errors.addAll(puzzleErrors)
                for (err in puzzleErrors) {
                    Log.e(TAG, "Puzzle '${puzzle.id}' error: $err")
                }
            }
        }
        if (errors.isEmpty()) {
            Log.i(TAG, "Successfully verified ${puzzles.size} tangram puzzles cleanly!")
        }
        return errors
    }

    fun verifyPuzzle(puzzle: PregeneratedShape): List<String> {
        val errors = mutableListOf<String>()

        // 1. Check piece count
        if (puzzle.placements.size != 7) {
            errors.add("Expected 7 piece placements, found ${puzzle.placements.size}")
            return errors
        }

        // 2. Check rotations are multiples of 45 degrees
        for ((idx, placement) in puzzle.placements.withIndex()) {
            val normRot = (placement.rotation % 360f + 360f) % 360f
            if (abs(normRot % 45f) > 0.01f) {
                errors.add("Piece index $idx has invalid rotation ${placement.rotation}° (must be multiple of 45°)")
            }
        }

        // 3. Compute silhouette target polygons and check non-empty
        val targetPolygons = puzzle.toShapesPuzzle().target.polygons
        if (targetPolygons.size != 7) {
            errors.add("Failed to compute target silhouette polygons, expected 7, found ${targetPolygons.size}")
        }

        return errors
    }
}
