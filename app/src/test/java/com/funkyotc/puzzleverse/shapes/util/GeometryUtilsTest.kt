package com.funkyotc.puzzleverse.shapes.util

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.*
import org.junit.Test

class GeometryUtilsTest {

    @Test
    fun testIsPointInPolygon() {
        // A simple 100x100 square centered at 50,50
        val square = listOf(
            Offset(0f, 0f),
            Offset(100f, 0f),
            Offset(100f, 100f),
            Offset(0f, 100f)
        )

        // Inside points
        assertTrue(GeometryUtils.isPointInPolygon(Offset(50f, 50f), square))
        assertTrue(GeometryUtils.isPointInPolygon(Offset(10f, 10f), square))

        // Outside points
        assertFalse(GeometryUtils.isPointInPolygon(Offset(-5f, 50f), square))
        assertFalse(GeometryUtils.isPointInPolygon(Offset(105f, 50f), square))
        assertFalse(GeometryUtils.isPointInPolygon(Offset(50f, -1f), square))

        // Snapped/Boundary tolerance check: exactly on vertices or edges should be inside
        assertTrue(GeometryUtils.isPointInPolygon(Offset(0f, 0f), square))
        assertTrue(GeometryUtils.isPointInPolygon(Offset(100f, 100f), square))
        assertTrue(GeometryUtils.isPointInPolygon(Offset(50f, 0f), square))
    }

    @Test
    fun testDoPolygonsIntersect() {
        // Square 1: [0,0] to [10,10]
        val sq1 = listOf(
            Offset(0f, 0f),
            Offset(10f, 0f),
            Offset(10f, 10f),
            Offset(0f, 10f)
        )

        // Square 2: overlapping [5,5] to [15,15]
        val sq2 = listOf(
            Offset(5f, 5f),
            Offset(15f, 5f),
            Offset(15f, 15f),
            Offset(5f, 15f)
        )

        // Square 3: completely separate [20,20] to [30,30]
        val sq3 = listOf(
            Offset(20f, 20f),
            Offset(30f, 20f),
            Offset(30f, 30f),
            Offset(20f, 30f)
        )

        assertTrue(GeometryUtils.doPolygonsIntersect(sq1, sq2))
        assertFalse(GeometryUtils.doPolygonsIntersect(sq1, sq3))
    }

    @Test
    fun testIsPolygonInside() {
        // Outer square: [0,0] to [100,100]
        val outer = listOf(
            Offset(0f, 0f),
            Offset(100f, 0f),
            Offset(100f, 100f),
            Offset(0f, 100f)
        )

        // Inner square: [10,10] to [90,90]
        val inner = listOf(
            Offset(10f, 10f),
            Offset(90f, 10f),
            Offset(90f, 90f),
            Offset(10f, 90f)
        )

        // Crossing square: [-10,10] to [50,90]
        val crossing = listOf(
            Offset(-10f, 10f),
            Offset(50f, 10f),
            Offset(50f, 90f),
            Offset(-10f, 90f)
        )

        assertTrue(GeometryUtils.isPolygonInside(inner, outer))
        assertFalse(GeometryUtils.isPolygonInside(crossing, outer))
    }

    @Test
    fun testTransformPolygon() {
        // Right triangle at origin: (0,0), (10,0), (0,10)
        val triangle = listOf(
            Offset(0f, 0f),
            Offset(10f, 0f),
            Offset(0f, 10f)
        )

        // 1. Pure translation by (20, 30)
        val trans = GeometryUtils.transformPolygon(
            vertices = triangle,
            translation = Offset(20f, 30f),
            rotationDegrees = 0f,
            isFlipped = false
        )
        assertEquals(Offset(20f, 30f), trans[0])
        assertEquals(Offset(30f, 30f), trans[1])
        assertEquals(Offset(20f, 40f), trans[2])

        // 2. Pure rotation by 90 degrees around origin, then translation (20, 30)
        val rot = GeometryUtils.transformPolygon(
            vertices = triangle,
            translation = Offset(20f, 30f),
            rotationDegrees = 90f,
            isFlipped = false
        )
        // (0,0) -> (20, 30)
        assertEquals(20f, rot[0].x, 0.01f)
        assertEquals(30f, rot[0].y, 0.01f)
        // (10,0) -> (20, 40)
        assertEquals(20f, rot[1].x, 0.01f)
        assertEquals(40f, rot[1].y, 0.01f)
    }
}
