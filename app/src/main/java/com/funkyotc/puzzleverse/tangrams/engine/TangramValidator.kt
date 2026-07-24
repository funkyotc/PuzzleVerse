package com.funkyotc.puzzleverse.tangrams.engine

import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.RegionIterator
import android.util.Log
import com.funkyotc.puzzleverse.tangrams.model.TangramPiece

object TangramValidator {

    private const val TAG = "TangramWinCheck"

    private fun calculateTruePathArea(path: Path, bounds: RectF): Float {
        if (bounds.width() < 1f || bounds.height() < 1f) return 0f
        val region = Region()
        val clip = Region(
            (bounds.left - 10f).toInt(),
            (bounds.top - 10f).toInt(),
            (bounds.right + 10f).toInt(),
            (bounds.bottom + 10f).toInt()
        )
        region.setPath(path, clip)

        var area = 0f
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect)) {
            area += (rect.width() * rect.height()).toFloat()
        }
        return area
    }

    /**
     * Check if puzzle is solved by verifying each piece overlaps a unique target path
     * by at least 50% of the piece's own area.
     * 
     * IMPORTANT: We do NOT filter by PieceType because the SVG piece type classification
     * is unreliable (medium triangle and parallelogram have nearly identical geometric properties).
     * Instead, we use a greedy best-match approach: for each piece, find the target path
     * it overlaps the most, require >= 50% overlap, and consume that target.
     */
    fun isPuzzleSolved(pieces: List<TangramPiece>, targetPiecePaths: List<Pair<Path, PieceType>>): Boolean {
        if (pieces.size < 7 || targetPiecePaths.size < 7) {
            Log.d(TAG, "Validation aborted: pieces=${pieces.size}, targets=${targetPiecePaths.size}")
            return false
        }

        Log.d(TAG, "=== Starting victory check (type-agnostic) ===")

        val usedTargets = BooleanArray(targetPiecePaths.size) { false }

        for (piece in pieces) {
            val pPath = piece.getTransformedPath()
            val pBounds = RectF()
            pPath.computeBounds(pBounds, true)
            val pArea = calculateTruePathArea(pPath, pBounds)
            if (pArea <= 0f) {
                Log.d(TAG, "Piece ${piece.id}: pArea=$pArea INVALID")
                return false
            }

            var bestRatio = 0f
            var bestIndex = -1

            for (i in targetPiecePaths.indices) {
                if (usedTargets[i]) continue

                val (tPath, _) = targetPiecePaths[i]

                // Quick bounding box overlap check to skip obviously non-overlapping targets
                val tBounds = RectF()
                tPath.computeBounds(tBounds, true)
                if (!RectF.intersects(pBounds, tBounds)) continue

                val tArea = calculateTruePathArea(tPath, tBounds)
                val intersection = Path()
                intersection.op(tPath, pPath, Path.Op.INTERSECT)
                val iBounds = RectF()
                intersection.computeBounds(iBounds, true)
                val iArea = calculateTruePathArea(intersection, iBounds)
                val ratio = iArea / pArea

                Log.d(TAG, "  vs target[$i]: tBounds=[${tBounds.left.toInt()},${tBounds.top.toInt()},${tBounds.right.toInt()},${tBounds.bottom.toInt()}] tArea=$tArea, iArea=$iArea, ratio=$ratio")

                if (ratio > bestRatio) {
                    bestRatio = ratio
                    bestIndex = i
                }
            }

            Log.d(TAG, "Piece ${piece.id}: pArea=$pArea, bestMatch=target[$bestIndex], ratio=$bestRatio")

            if (bestIndex != -1 && bestRatio >= 0.50f) {
                usedTargets[bestIndex] = true
            } else {
                Log.d(TAG, "  -> FAILED (ratio=$bestRatio < 0.50)")
                return false
            }
        }

        val allMatched = usedTargets.all { it }
        if (allMatched) {
            Log.d(TAG, "=== PUZZLE SOLVED! All 7 pieces matched! ===")
        }
        return allMatched
    }
}
