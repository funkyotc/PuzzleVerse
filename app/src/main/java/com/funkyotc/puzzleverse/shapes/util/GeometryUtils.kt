package com.funkyotc.puzzleverse.shapes.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

object GeometryUtils {

    /**
     * Checks if a point is inside a polygon (Ray Casting algorithm).
     */
    fun isPointInPolygon(point: Offset, vertices: List<Offset>): Boolean {
        // First check if the point is extremely close to any vertex (for snapped pieces)
        for (v in vertices) {
            if (kotlin.math.hypot(point.x - v.x, point.y - v.y) < 1f) return true
        }

        // Check if the point lies on any segment
        for (i in vertices.indices) {
            val p1 = vertices[i]
            val p2 = vertices[(i + 1) % vertices.size]
            if (distanceToSegment(point, p1, p2) < 1f) return true
        }

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

    private fun distanceToSegment(p: Offset, a: Offset, b: Offset): Float {
        val l2 = (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y)
        if (l2 == 0f) return kotlin.math.hypot(p.x - a.x, p.y - a.y)
        var t = ((p.x - a.x) * (b.x - a.x) + (p.y - a.y) * (b.y - a.y)) / l2
        t = t.coerceIn(0f, 1f)
        val projection = Offset(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y))
        return kotlin.math.hypot(p.x - projection.x, p.y - projection.y)
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

        return vertices.map { vertex ->
            // 1. Flip around local (0, 0)
            val fx = if (isFlipped) -vertex.x else vertex.x
            val fy = vertex.y
            
            // 2. Rotate around local (0, 0)
            val rx = fx * cos - fy * sin
            val ry = fx * sin + fy * cos
            
            // 3. Translate to world position
            Offset(rx + translation.x, ry + translation.y)
        }
    }

    fun transformPolygonAnimated(
        vertices: List<Offset>,
        translation: Offset,
        rotationDegrees: Float,
        scaleX: Float
    ): List<Offset> {
        val rad = Math.toRadians(rotationDegrees.toDouble())
        val cos = cos(rad).toFloat()
        val sin = sin(rad).toFloat()

        return vertices.map { vertex ->
            // 1. Flip (scale X) around local (0, 0)
            val fx = vertex.x * scaleX
            val fy = vertex.y
            
            // 2. Rotate around local (0, 0)
            val rx = fx * cos - fy * sin
            val ry = fx * sin + fy * cos
            
            // 3. Translate to world position
            Offset(rx + translation.x, ry + translation.y)
        }
    }
}
