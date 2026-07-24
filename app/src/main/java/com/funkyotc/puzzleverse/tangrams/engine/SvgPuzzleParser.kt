package com.funkyotc.puzzleverse.tangrams.engine

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.RegionIterator
import android.util.Log
import androidx.core.graphics.PathParser

enum class PieceType {
    LARGE_TRIANGLE,
    MEDIUM_TRIANGLE,
    SQUARE,
    PARALLELOGRAM,
    SMALL_TRIANGLE
}

data class TargetSlot(
    val center: PointF,
    val pieceType: PieceType
)

data class SilhouetteResult(
    val path: Path,
    val pieceScaleFactor: Float,
    val targetVertices: List<PointF>,
    val targetSlots: List<TargetSlot>,
    val targetPiecePaths: List<Pair<Path, PieceType>>
)

object SvgPuzzleParser {

    private fun sanitizePathData(pathString: String): String {
        val sciRegex = Regex("""([+-]?\d+(?:\.\d+)?)[eE]([+-]?\d+)""")
        return pathString.replace(sciRegex) { match ->
            try {
                match.value.toDouble().toBigDecimal().toPlainString()
            } catch (e: Exception) {
                "0"
            }
        }
    }

    private fun calculateRegionArea(path: Path): Float {
        val bounds = RectF()
        path.computeBounds(bounds, true)
        if (bounds.width() < 1f || bounds.height() < 1f) return 0f

        val region = Region()
        val clip = Region(
            (bounds.left - 5f).toInt(),
            (bounds.top - 5f).toInt(),
            (bounds.right + 5f).toInt(),
            (bounds.bottom + 5f).toInt()
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

    private fun classifyPiece(path: Path, area: Float, expectedUnitArea: Float): PieceType {
        val relativeArea = area / expectedUnitArea

        if (relativeArea > 3.0f) {
            return PieceType.LARGE_TRIANGLE
        }
        if (relativeArea < 1.5f) {
            return PieceType.SMALL_TRIANGLE
        }

        // Medium pieces (~2.0 relative area)
        var totalPerimeter = 0f
        var pm = PathMeasure(path, false)
        do {
            totalPerimeter += pm.length
        } while (pm.nextContour())

        val perimeterRatio = if (area > 0) totalPerimeter / Math.sqrt(area.toDouble()).toFloat() else 0f

        return when {
            perimeterRatio < 4.2f -> PieceType.SQUARE
            perimeterRatio < 4.75f -> PieceType.MEDIUM_TRIANGLE
            else -> PieceType.PARALLELOGRAM
        }
    }

    fun createSolidSilhouette(svgRawText: String, screenWidth: Float, screenHeight: Float): SilhouetteResult {
        val pathRegex = Regex("""\bd="([^"]+)"""", RegexOption.IGNORE_CASE)
        val matches = pathRegex.findAll(svgRawText)

        val masterPath = Path()
        val rawPaths = mutableListOf<Path>()

        matches.forEach { matchResult ->
            val rawPathString = matchResult.groupValues[1]
            val sanitizedString = sanitizePathData(rawPathString)
            try {
                val piecePath = PathParser.createPathFromPathData(sanitizedString)
                if (piecePath != null && !piecePath.isEmpty) {
                    masterPath.addPath(piecePath)
                    rawPaths.add(piecePath)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (masterPath.isEmpty) return SilhouetteResult(masterPath, 100f, emptyList(), emptyList(), emptyList())

        val bounds = RectF()
        masterPath.computeBounds(bounds, true)

        val maxTargetWidth = screenWidth * 0.85f
        val maxTargetHeight = screenHeight * 0.48f

        val scaleX = maxTargetWidth / bounds.width()
        val scaleY = maxTargetHeight / bounds.height()
        val scaleFactor = minOf(scaleX, scaleY)

        val matrix = Matrix()
        matrix.postScale(scaleFactor, scaleFactor)

        val scaledBounds = RectF()
        matrix.mapRect(scaledBounds, bounds)

        val playfieldTop = screenHeight * 0.12f
        val playfieldHeight = screenHeight * 0.48f

        val translateX = (screenWidth - scaledBounds.width()) / 2f - scaledBounds.left
        val translateY = playfieldTop + (playfieldHeight - scaledBounds.height()) / 2f - scaledBounds.top

        matrix.postTranslate(translateX, translateY)
        masterPath.transform(matrix)

        // Transform individual paths first so area calculations are highly accurate
        val transformedPaths = rawPaths.map { path ->
            val transformedPath = Path(path)
            transformedPath.transform(matrix)
            transformedPath
        }

        var scaledTotalArea = 0f
        val pathAreas = mutableListOf<Float>()
        
        for (p in transformedPaths) {
            val area = calculateRegionArea(p)
            scaledTotalArea += area
            pathAreas.add(area)
        }

        // Base UI pieces have total area of 16.0 (Large Tri=4x2, Medium Tri=2, Square=2, Parallelogram=2, Small Tri=1x2)
        // Scaled area = UI_area * scale^2
        val pieceScale = Math.sqrt((scaledTotalArea / 16.0).toDouble()).toFloat()
        val expectedUnitArea = scaledTotalArea / 16.0f

        val finalPiecePaths = mutableListOf<Pair<Path, PieceType>>()
        val targetSlots = mutableListOf<TargetSlot>()
        val targetVertices = mutableListOf<PointF>()

        for (i in transformedPaths.indices) {
            val p = transformedPaths[i]
            val area = pathAreas[i]
            val type = classifyPiece(p, area, expectedUnitArea)
            
            finalPiecePaths.add(Pair(p, type))
            
            val pBounds = RectF()
            p.computeBounds(pBounds, true)
            val center = PointF(pBounds.centerX(), pBounds.centerY())
            targetSlots.add(TargetSlot(center, type))
            targetVertices.add(center)
            
            Log.d("TangramParse", "Piece $i classified as $type (Area: $area, Unit expected: $expectedUnitArea)")
        }

        return SilhouetteResult(masterPath, pieceScale, targetVertices, targetSlots, finalPiecePaths)
    }
}
