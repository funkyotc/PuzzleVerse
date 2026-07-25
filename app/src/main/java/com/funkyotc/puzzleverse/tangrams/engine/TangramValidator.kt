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

    fun calculateTruePathArea(path: Path, bounds: RectF): Float {
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
     * Check if puzzle is solved by verifying:
     * 1. Overall target silhouette coverage >= 98%
     * 2. Inter-piece overlap <= 2.5%
     * 
     * Uses path union optimization: Total Overlap Area = Sum(Piece Areas) - Area(Union of Pieces).
     */
    fun isPuzzleSolved(pieces: List<TangramPiece>, targetSilhouette: Path?): Boolean {
        if (pieces.size < 7 || targetSilhouette == null || targetSilhouette.isEmpty) {
            Log.d(TAG, "Validation aborted: pieces=${pieces.size}, targetSilhouette empty")
            return false
        }

        val silBounds = RectF()
        targetSilhouette.computeBounds(silBounds, true)
        val silArea = calculateTruePathArea(targetSilhouette, silBounds)
        if (silArea <= 0f) {
            Log.d(TAG, "Validation aborted: silArea <= 0")
            return false
        }

        var sumPieceAreas = 0f
        val unionPath = Path()

        for (piece in pieces) {
            val pPath = piece.getTransformedPath()
            val pBounds = RectF()
            pPath.computeBounds(pBounds, true)
            val pArea = calculateTruePathArea(pPath, pBounds)
            if (pArea <= 0f) {
                Log.d(TAG, "Piece ${piece.id}: pArea=$pArea INVALID")
                return false
            }
            sumPieceAreas += pArea
            unionPath.op(pPath, Path.Op.UNION)
        }

        val unionBounds = RectF()
        unionPath.computeBounds(unionBounds, true)
        val unionArea = calculateTruePathArea(unionPath, unionBounds)

        val overlapArea = maxOf(0f, sumPieceAreas - unionArea)
        val overlapRatio = overlapArea / sumPieceAreas

        val intersectionPath = Path()
        intersectionPath.op(unionPath, targetSilhouette, Path.Op.INTERSECT)

        val intersectBounds = RectF()
        intersectionPath.computeBounds(intersectBounds, true)
        val intersectArea = calculateTruePathArea(intersectionPath, intersectBounds)

        val coverageRatio = intersectArea / silArea

        Log.d(TAG, "Victory check: silArea=$silArea, sumPieceAreas=$sumPieceAreas, unionArea=$unionArea, overlapArea=$overlapArea (overlapRatio=${overlapRatio * 100}%), intersectArea=$intersectArea (coverageRatio=${coverageRatio * 100}%)")

        val solved = coverageRatio >= 0.98f && overlapRatio <= 0.025f
        if (solved) {
            Log.d(TAG, "=== PUZZLE SOLVED! Coverage: ${(coverageRatio * 100).toInt()}%, Overlap: ${(overlapRatio * 100).toInt()}% ===")
        }
        return solved
    }
}
