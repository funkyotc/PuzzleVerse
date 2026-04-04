package com.funkyotc.puzzleverse.shapes.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

object GeometryUtils {

    /**
     * Checks if a point is inside a polygon (Ray Casting algorithm).
     */
    fun isPointInPolygon(point: Offset, vertices: List<Offset>): Boolean {
        var isInside = false
        var j = vertices.size - 1
        for (i in vertices.indices) {
            if (((vertices[i].y > point.y) != (vertices[j].y > point.y)) &&
                (point.x < (vertices[j].x - vertices[i].x) * (point.y - vertices[i].y) / (vertices[j].y - vertices[i].y) + vertices[i].x)
            ) {
                isInside = !isInside
            }
            j = i
        }
        return isInside
    }

    /**
     * Checks if two polygons intersect (Separating Axis Theorem).
     * This is a simplified check that works for convex polygons.
     * For concave polygons (like some tangram sets might form combined), we might need to triangulate or checking edges.
     * Note: Tangram pieces are convex (triangles, squares, parallelograms).
     */
    fun doPolygonsIntersect(polygon1: List<Offset>, polygon2: List<Offset>): Boolean {
        val polygons = listOf(polygon1, polygon2)
        for (i in polygons.indices) {
            val polygon = polygons[i]
            for (j in polygon.indices) {
                val p1 = polygon[j]
                val p2 = polygon[(j + 1) % polygon.size]
                val normal = Offset(p2.y - p1.y, p1.x - p2.x)

                var minA = Float.MAX_VALUE
                var maxA = Float.MIN_VALUE
                for (p in polygon1) {
                    val projected = p.x * normal.x + p.y * normal.y
                    if (projected < minA) minA = projected
                    if (projected > maxA) maxA = projected
                }

                var minB = Float.MAX_VALUE
                var maxB = Float.MIN_VALUE
                for (p in polygon2) {
                    val projected = p.x * normal.x + p.y * normal.y
                    if (projected < minB) minB = projected
                    if (projected > maxB) maxB = projected
                }

                if (maxA <= minB + 1.0f || maxB <= minA + 1.0f) return false
            }
        }
        return true
    }

    /**
     * Checks if polygon1 is completely inside polygon2.
     */
    fun isPolygonInside(inner: List<Offset>, outer: List<Offset>): Boolean {
        // 1. Check if all vertices of inner are inside outer
        for (vertex in inner) {
            if (!isPointInPolygon(vertex, outer)) return false
        }
        // 2. Check for intersections (edges crossing) to prevent cases where vertices are inside but edges cross out
        // (Only needed for concave outer polygons; if convex, vertex check is sufficient)
        // Since the target silhouette might be concave, we strictly need edge intersection checks too.
        // However, if all vertices are inside, can it cross out? Yes, in concave shapes.
        // Simple check: check midpoints of edges? Or full segment intersection.
        
        // Full segment intersection check
        for (i in inner.indices) {
            val p1 = inner[i]
            val p2 = inner[(i + 1) % inner.size]
            
            for (j in outer.indices) {
                val q1 = outer[j]
                val q2 = outer[(j + 1) % outer.size]
                
                if (doSegmentsIntersect(p1, p2, q1, q2)) return false
            }
        }
        
        return true
    }

    private fun doSegmentsIntersect(p1: Offset, p2: Offset, q1: Offset, q2: Offset): Boolean {
        fun crossProduct(a: Offset, b: Offset, c: Offset): Float {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        }

        val cp1 = crossProduct(p1, p2, q1)
        val cp2 = crossProduct(p1, p2, q2)
        val cp3 = crossProduct(q1, q2, p1)
        val cp4 = crossProduct(q1, q2, p2)

        val cross1 = (cp1 > 0f && cp2 < 0f) || (cp1 < 0f && cp2 > 0f)
        val cross2 = (cp3 > 0f && cp4 < 0f) || (cp3 < 0f && cp4 > 0f)

        return cross1 && cross2
    }

    fun transformPolygon(
        vertices: List<Offset>,
        translation: Offset,
        rotationDegrees: Float,
        scale: Float = 1f,
        isFlipped: Boolean = false
    ): List<Offset> {
        val rad = Math.toRadians(rotationDegrees.toDouble())
        val cos = cos(rad).toFloat()
        val sin = sin(rad).toFloat()
        
        // Find center for rotation? Or rotate around (0,0) of the piece definition?
        // Usually pieces are defined around their centroid or top-left.
        // Let's assume rotating around the center of the bounding box of the base vertices?
        // Or just rotate around (0,0) local space.
        
        // Let's find the centroid of the base vertices to rotate around it
        val cx = vertices.map { it.x }.average().toFloat()
        val cy = vertices.map { it.y }.average().toFloat()

        return vertices.map { vertex ->
            // 1. Center
            val dx = vertex.x - cx
            val dy = vertex.y - cy
            
            // 2. Flip
            val fx = if (isFlipped) -dx else dx
            val fy = dy
            
            // 3. Rotate
            val rx = fx * cos - fy * sin
            val ry = fx * sin + fy * cos
            
            // 4. Translate back + Position
            // The position in PuzzlePiece likely represents the center or top-left in WORLD space.
            // If it's world space, we just add it.
            Offset(rx + translation.x, ry + translation.y)
        }
    }
}
