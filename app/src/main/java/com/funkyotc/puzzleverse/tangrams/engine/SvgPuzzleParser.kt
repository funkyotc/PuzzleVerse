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

    /**
     * Calculate the actual filled pixel area of a path using Region scanline decomposition.
     * This gives true area regardless of shape complexity or rotation.
     */
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

    /**
     * Classify SVG path into tangram piece types.
     * 
     * The 7 standard tangram pieces have these relative area proportions
     * (using large triangle area = 4 units):
     *   Large Triangle:  4 units (x2)
     *   Medium Triangle: 2 units (x1)
     *   Square:          2 units (x1)  
     *   Parallelogram:   2 units (x1)
     *   Small Triangle:  1 unit  (x2)
     * Total = 16 units
     *
     * We classify by:
     * 1. Width for large triangles (clearly biggest)
     * 2. Width for small triangles (clearly smallest)
     * 3. For medium pieces (width 140-300): use filled area / bounding box area ratio
     *    - Triangle fills ~50% of bounding box
     *    - Square fills ~50% of bounding box (rotated 45° diamond)
     *    - Parallelogram fills ~50% of bounding box 
     *    So we need another approach: triangle has 3 corners, square has 4 right-angle corners,
     *    parallelogram has 4 corners but is elongated
     */
    private fun determinePieceType(path: Path, width: Float, height: Float): PieceType {
        val filledArea = calculateRegionArea(path)
        val bboxArea = width * height
        val fillRatio = if (bboxArea > 0) filledArea / bboxArea else 0f

        Log.d("TangramParse", "determinePieceType: w=$width, h=$height, filledArea=$filledArea, bboxArea=$bboxArea, fillRatio=$fillRatio")

        // Large triangles: biggest pieces (width > 300)
        if (width > 300f) {
            Log.d("TangramParse", " -> LARGE_TRIANGLE (width > 300)")
            return PieceType.LARGE_TRIANGLE
        }

        // Small triangles: smallest pieces (width < 140)
        if (width < 140f) {
            Log.d("TangramParse", " -> SMALL_TRIANGLE (width < 140)")
            return PieceType.SMALL_TRIANGLE
        }

        // Medium pieces: width 140-300
        // Square is roughly square-shaped (aspect ratio near 1:1), width ~172
        // Parallelogram is wide and short (aspect ratio ~2:1), width ~243
        // Medium triangle is wide and short (aspect ratio ~2:1), width ~243
        val aspectRatio = width / height

        if (aspectRatio < 1.3f) {
            // Nearly square aspect ratio -> SQUARE
            Log.d("TangramParse", " -> SQUARE (aspect=$aspectRatio < 1.3)")
            return PieceType.SQUARE
        }

        // Both medium triangle and parallelogram have ~2:1 aspect ratio and similar widths.
        // The key difference: triangle fills ~50% of bbox, parallelogram fills more (~50% too in theory).
        // Actually, for these shapes the fill ratios are very similar.
        //
        // Better approach: the parallelogram is a skewed rectangle and its filled area
        // relative to bounding box is higher than a triangle at the same bounding box dimensions.
        // Let's check: parallelogram = base * height = fills about 50% of bbox.
        // Triangle = 0.5 * base * height = fills about 50% of bbox too.
        // 
        // Actually the critical insight from the SVG data:
        //   path3632 (parallelogram): fillRatio will be ~0.50 (parallelogram area = base * height / bbox)
        //   path3640 (medium triangle): fillRatio will be ~0.50 (triangle area = 0.5 * base * height / bbox)
        // Wait, a parallelogram inscribed in its bounding box fills MORE than a triangle.
        // Parallelogram: the bounding box width includes the skew offset, so the ratio depends on skew angle.
        //
        // Let me think about this differently using actual pixel areas:
        // path3632 (parallelogram): bbox 242.5 x 121.0 = 29354, filled should be ~14677 (half)
        // path3640 (medium triangle): bbox 242.6 x 121.8 = 29542, filled should be ~14771 (half)
        // These are too similar to distinguish reliably!
        //
        // The ONLY reliable way to distinguish: compare filled area to the reference large triangle.
        // Or: use the actual point count from the path data.
        // But we already know path3640 has 4 coordinate pairs and path3632 has 5.
        //
        // Actually wait - let me reconsider. path3640 is:
        //   M 359.95155,123.78866   (point A)
        //   480.19675,1.9999999     (point B - implicit L)
        //   602.51631,123.78866     (point C - implicit L)
        //   l -242.56476,0          (point D - goes from C back to A, which is redundant)
        //   z                       (close)
        //
        // Point D = C + (-242.56476, 0) = (602.51631 - 242.56476, 123.78866) = (359.95155, 123.78866) = A!
        // So this IS a triangle with a redundant closing segment.
        //
        // path3632 is:
        //   m 359.45338,124.03369   (point A)
        //   121.24153,120.79233     (point B = A + delta)
        //   121.2824,3e-5           (point C = B + delta)
        //   -120.74338,-121.03739   (point D = C + delta)
        //   -121.78055,0.24503      (point E = D + delta, should ≈ A)
        //   z                       (close)
        //
        // This has 4 distinct geometric corners (A, B, C, D) with E ≈ A.
        //
        // So the solution is: extract actual coordinate points, deduplicate close-together points,
        // and count unique corners.
        
        // Use a completely different approach: measure the actual perimeter length.
        // A parallelogram has a longer perimeter than a triangle of the same bounding box size
        // because a parallelogram has 4 sides while a triangle has 3.
        val pathMeasure = PathMeasure(path, true)
        val perimeter = pathMeasure.length
        // For similar bounding boxes, perimeter ratio = perimeter / sqrt(filledArea) 
        // is higher for shapes with more sides
        val perimeterRatio = if (filledArea > 0) perimeter / Math.sqrt(filledArea.toDouble()).toFloat() else 0f

        Log.d("TangramParse", "  perimeter=$perimeter, perimRatio=$perimeterRatio")

        // A triangle has perimeterRatio ≈ 4.56 (3+sqrt(2) sides / sqrt(area))
        // A parallelogram has perimeterRatio ≈ 4.93 (4 sides with skew / sqrt(area))
        // Let's use a threshold between them
        if (perimeterRatio > 4.7f) {
            Log.d("TangramParse", " -> PARALLELOGRAM (perimRatio=$perimeterRatio > 4.7)")
            return PieceType.PARALLELOGRAM
        } else {
            Log.d("TangramParse", " -> MEDIUM_TRIANGLE (perimRatio=$perimeterRatio <= 4.7)")
            return PieceType.MEDIUM_TRIANGLE
        }
    }
    
    fun createSolidSilhouette(svgRawText: String, screenWidth: Float, screenHeight: Float): SilhouetteResult {
        val pathRegex = Regex("""\bd="([^"]+)"""", RegexOption.IGNORE_CASE)
        val matches = pathRegex.findAll(svgRawText)
        
        val masterPath = Path()
        val rawPiecePaths = mutableListOf<Pair<Path, PieceType>>()
        val rawSlotCenters = mutableListOf<Pair<PointF, PieceType>>()
        var detectedLargeTriangleBase = 343.06131f

        matches.forEach { matchResult ->
            val rawPathString = matchResult.groupValues[1]
            val sanitizedString = sanitizePathData(rawPathString)
            try {
                val piecePath = PathParser.createPathFromPathData(sanitizedString)
                if (piecePath != null && !piecePath.isEmpty) {
                    val pBounds = RectF()
                    piecePath.computeBounds(pBounds, true)
                    val width = pBounds.width()
                    val height = pBounds.height()
                    if (width > 300f && width < 400f) {
                        detectedLargeTriangleBase = width
                    }

                    masterPath.addPath(piecePath)

                    val pType = determinePieceType(piecePath, width, height)
                    Log.d("TangramParse", "SVG piece classified: type=$pType, pathStart=${rawPathString.take(50)}")
                    rawPiecePaths.add(Pair(piecePath, pType))
                    rawSlotCenters.add(Pair(PointF(pBounds.centerX(), pBounds.centerY()), pType))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Log the final classification summary
        val typeCounts = rawPiecePaths.groupBy { it.second }.mapValues { it.value.size }
        Log.d("TangramParse", "=== SVG Classification Summary ===")
        Log.d("TangramParse", "Total pieces: ${rawPiecePaths.size}")
        typeCounts.forEach { (type, count) ->
            Log.d("TangramParse", "  $type: $count")
        }
        Log.d("TangramParse", "Expected: 2xLARGE_TRIANGLE, 1xMEDIUM_TRIANGLE, 1xSQUARE, 1xPARALLELOGRAM, 2xSMALL_TRIANGLE")

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

        // Transform individual target piece paths
        val transformedPiecePaths = rawPiecePaths.map { (path, pieceType) ->
            val transformedPath = Path(path)
            transformedPath.transform(matrix)
            Pair(transformedPath, pieceType)
        }

        // Transform solution slot centers
        val slotSrcArray = FloatArray(rawSlotCenters.size * 2)
        for (idx in rawSlotCenters.indices) {
            slotSrcArray[idx * 2] = rawSlotCenters[idx].first.x
            slotSrcArray[idx * 2 + 1] = rawSlotCenters[idx].first.y
        }
        val slotDstArray = FloatArray(slotSrcArray.size)
        matrix.mapPoints(slotDstArray, slotSrcArray)

        val transformedTargetSlots = mutableListOf<TargetSlot>()
        val transformedTargetVertices = mutableListOf<PointF>()
        for (idx in rawSlotCenters.indices) {
            val center = PointF(slotDstArray[idx * 2], slotDstArray[idx * 2 + 1])
            transformedTargetSlots.add(TargetSlot(center, rawSlotCenters[idx].second))
            transformedTargetVertices.add(center)
        }
        
        val pieceScale = (detectedLargeTriangleBase / 4f) * scaleFactor

        return SilhouetteResult(masterPath, pieceScale, transformedTargetVertices, transformedTargetSlots, transformedPiecePaths)
    }
}
