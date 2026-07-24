package com.funkyotc.puzzleverse.tangrams.model

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Region
import androidx.compose.ui.graphics.Color

data class TangramPiece(
    val id: Int,
    val unitPoints: List<PointF>, 
    var x: Float = 0f,            
    var y: Float = 0f,
    var rotationAngle: Float = 0f, 
    var isFlipped: Boolean = false,
    val color: Color,
    val scaleFactor: Float = 100f
) {
    fun getTransformedPath(): Path {
        val path = Path()
        if (unitPoints.isEmpty()) return path
        
        // Map unit points to scaled points
        path.moveTo(unitPoints[0].x * scaleFactor, unitPoints[0].y * scaleFactor)
        for (i in 1 until unitPoints.size) {
            path.lineTo(unitPoints[i].x * scaleFactor, unitPoints[i].y * scaleFactor)
        }
        path.close()

        // Apply flip (mirror), rotation around center of bounding box, and translation
        val matrix = Matrix()
        val bounds = RectF()
        path.computeBounds(bounds, true)
        
        if (isFlipped) {
            matrix.postScale(-1f, 1f, bounds.centerX(), bounds.centerY())
        }
        matrix.postRotate(rotationAngle, bounds.centerX(), bounds.centerY())
        
        matrix.postTranslate(x, y)
        path.transform(matrix)
        
        return path
    }

    fun containsPoint(px: Float, py: Float): Boolean {
        val transformedPath = getTransformedPath()
        val bounds = RectF()
        transformedPath.computeBounds(bounds, true)
        
        // Fast bounding box check with padding
        if (px < bounds.left - 10f || px > bounds.right + 10f || py < bounds.top - 10f || py > bounds.bottom + 10f) {
            return false
        }

        // Precise Region containment check
        val region = Region()
        val clip = Region(
            (bounds.left - 5f).toInt(),
            (bounds.top - 5f).toInt(),
            (bounds.right + 5f).toInt(),
            (bounds.bottom + 5f).toInt()
        )
        region.setPath(transformedPath, clip)
        return region.contains(px.toInt(), py.toInt())
    }
}
