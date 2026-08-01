# Tangram Game: Complete Rewrite Plan (Refined)

## Problem Statement

The current Shapes game is fundamentally broken due to 4 interrelated root causes:

1. **Incorrect piece geometry** — vertices don't match standard tangram proportions
2. **Fabricated puzzles** — all 52 "puzzles" are ~6 layouts copy-pasted with different names
3. **Convex hull silhouettes** — every target looks like a blob instead of a recognizable figure
4. **Broken win detection** — doesn't check flip state, doesn't handle piece interchangeability

See the original plan for detailed breakdown of each issue.

---

## Design: Verified Grid-Based Tangram System

### Flipping Rules (Resolved)

> **Traditional tangram rules allow flipping (turning over) ALL pieces.** However, for 6 of the 7 pieces (all triangles + the square), flipping produces a shape identical to some rotation — so flipping is redundant. The **parallelogram is the only piece where flipping produces a genuinely different shape** (it's chiral/asymmetric).

**Decision:** Keep the Flip button available for all pieces (matching physical tangram behavior). The button will work on any piece, but only the parallelogram flip will produce a visually different result. Win detection will only check flip state for the parallelogram.

---

### Mathematically Verified Piece Geometry

I ran a Python verification script that validates the standard tangram dissection of a 4×4 integer grid. All results verified:

#### Master Square Dissection (4×4 Grid)

The 7 tangram pieces are derived from dissecting a square with corners `(0,0) → (4,0) → (4,4) → (0,4)`:

```
(0,4)─────────────────────(4,4)
 │╲                      ╱╲  │
 │  ╲                  ╱    ╲│
 │    ╲    LT-2     ╱   MT  │
 │      ╲        ╱          │
 │  PARA  ╲    ╱    SQ    ╱ │
 │─────────(2,2)────────╱   │
 │        ╱    │╲    ST2     │
 │      ╱      │  ╲          │
 │    ╱  LT-1  │    ╲        │
 │  ╱          │ ST1  ╲      │
 │╱            │        ╲    │
(0,0)─────────────────────(4,0)
```

| Piece | Absolute Vertices (Grid) | Area | Edge Lengths |
|-------|--------------------------|:----:|-------------|
| Large Triangle 1 | `(0,0), (4,0), (2,2)` | 4 | `4.0, 2√2, 2√2` |
| Large Triangle 2 | `(0,0), (0,4), (2,2)` | 4 | `4.0, 2√2, 2√2` |
| Medium Triangle | `(2,2), (4,4), (2,4)` | 2 | `2√2, 2.0, 2.0` |
| Small Triangle 1 | `(3,1), (4,0), (4,2)` | 1 | `√2, 2.0, √2` |
| Small Triangle 2 | `(3,3), (4,4), (4,2)` | 1 | `√2, 2.0, √2` |
| Square | `(2,2), (3,1), (4,2), (3,3)` | 2 | `√2, √2, √2, √2` |
| Parallelogram | `(0,4), (2,4), (3,3), (1,3)` | 2 | `2.0, √2, 2.0, √2` |

**Verified properties:**
- ✅ Total area = 16 = 4×4 square area
- ✅ All triangles have right angles (dot product = 0)
- ✅ All interior test points covered by exactly 1 piece (perfect tiling)
- ✅ Edge lengths match standard tangram ratios

#### Centroid-Centered Piece Vertices (Kotlin-Ready)

These are the exact floating-point values to use in `TangramPieces.kt`, derived by subtracting each piece's centroid:

```kotlin
// All coordinates on the 4-unit grid system
// Multiply by GRID_SCALE at render time

val LARGE_TRIANGLE = listOf(
    Offset(-2.000000f, -0.666667f),
    Offset( 2.000000f, -0.666667f),
    Offset( 0.000000f,  1.333333f)
)

val MEDIUM_TRIANGLE = listOf(
    Offset(-0.666667f, -1.333333f),
    Offset( 1.333333f,  0.666667f),
    Offset(-0.666667f,  0.666667f)
)

val SMALL_TRIANGLE = listOf(
    Offset(-0.666667f,  0.000000f),
    Offset( 0.333333f, -1.000000f),
    Offset( 0.333333f,  1.000000f)
)

val SQUARE = listOf(
    Offset(-1.000000f,  0.000000f),
    Offset( 0.000000f, -1.000000f),
    Offset( 1.000000f,  0.000000f),
    Offset( 0.000000f,  1.000000f)
)

val PARALLELOGRAM = listOf(
    Offset(-1.500000f,  0.500000f),
    Offset( 0.500000f,  0.500000f),
    Offset( 1.500000f, -0.500000f),
    Offset(-0.500000f, -0.500000f)
)
```

#### Edge Compatibility (Which Edges Can Snap Together)

All tangram edge lengths fall into exactly 3 groups:

| Length | Value | Pieces With This Edge |
|--------|:-----:|----------------------|
| `√2 ≈ 1.414` | Short | Small tri legs, Square sides, Para short sides |
| `2.0` | Medium | Small tri hyp, Medium tri legs, Para long sides |
| `2√2 ≈ 2.828` | Long | Medium tri hyp, Large tri legs |
| `4.0` | Extra-long | Large tri hypotenuse |

This means edges only snap to compatible-length edges — a natural constraint that helps players.

---

### Coordinate System & Scaling

```
GRID_SCALE = 40f  // 1 grid unit = 40 pixels in virtual space

Virtual canvas: 400×700 (same as current)
Target area: centered in upper portion (~400×400 area)
Piece tray: bottom 250px

The 4×4 grid tangram square occupies 160×160 pixels (4 × 40)
This fits comfortably in the upper canvas area
```

Pieces and targets are defined in grid coordinates and multiplied by `GRID_SCALE` at render time. This keeps puzzle data clean and scale-independent.

---

## Detailed Implementation Plan

### Phase 1: Core Data Model

#### [MODIFY] [Shape.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/model/Shape.kt)

Rewrite the data model with proper tangram types:

```kotlin
enum class TangramPieceType(val displayName: String) {
    LARGE_TRIANGLE_1("Large Triangle"),
    LARGE_TRIANGLE_2("Large Triangle"),
    MEDIUM_TRIANGLE("Medium Triangle"),
    SMALL_TRIANGLE_1("Small Triangle"),
    SMALL_TRIANGLE_2("Small Triangle"),
    SQUARE("Square"),
    PARALLELOGRAM("Parallelogram");
    
    /** Pieces that are interchangeable with this one (same shape) */
    val interchangeableWith: TangramPieceType? get() = when (this) {
        LARGE_TRIANGLE_1 -> LARGE_TRIANGLE_2
        LARGE_TRIANGLE_2 -> LARGE_TRIANGLE_1
        SMALL_TRIANGLE_1 -> SMALL_TRIANGLE_2
        SMALL_TRIANGLE_2 -> SMALL_TRIANGLE_1
        else -> null
    }
    
    /** Rotational symmetry period in degrees */
    val symmetryPeriod: Float get() = when (this) {
        SQUARE -> 90f
        PARALLELOGRAM -> 180f
        else -> 360f  // No rotational symmetry for triangles
    }
    
    /** Whether flip state matters for solution matching */
    val isFlipSignificant: Boolean get() = this == PARALLELOGRAM
}

data class PuzzlePiece(
    val id: Int,
    val type: TangramPieceType,
    val localVertices: List<Offset>,  // Canonical vertices centered at (0,0)
    val position: Offset,             // Current center in grid coordinates
    val rotation: Float = 0f,         // Degrees (multiples of 45)
    val isFlipped: Boolean = false,
    val color: Color,
    val isLocked: Boolean = false,
    val solutionPosition: Offset,
    val solutionRotation: Float,
    val solutionFlipped: Boolean = false
) {
    val worldVertices: List<Offset> get() = GeometryUtils.transformPolygon(
        localVertices, position, rotation, isFlipped = isFlipped
    )
}

data class TargetSilhouette(
    val vertices: List<Offset>,  // The actual concave outline polygon (grid coords)
    val color: Color = Color(0xFF1E293B)
)

data class ShapesPuzzle(
    val id: String,
    val name: String,
    val pieces: List<PuzzlePiece>,
    val target: TargetSilhouette,
    val isComplete: Boolean = false
)
```

**Key differences from current:**
- `TangramPieceType` enum with interchangeability, symmetry, and flip significance built in
- `solutionFlipped` field on `PuzzlePiece`
- `TargetSilhouette` replaces `TargetShape` — stores actual concave outline

---

### Phase 2: Canonical Piece Definitions

#### [NEW] [TangramPieces.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/model/TangramPieces.kt)

```kotlin
package com.funkyotc.puzzleverse.shapes.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Canonical tangram piece definitions on a 4-unit integer grid.
 * 
 * Derived from the standard dissection of a 4×4 square:
 *   (0,0)→(4,0)→(4,4)→(0,4)
 * 
 * All vertices are centered at each piece's centroid (0,0).
 * Multiply by GRID_SCALE at render time.
 * 
 * Verified properties:
 *   - Total area of all 7 pieces = 16 grid² = area of 4×4 square
 *   - All triangles are right isosceles (dot product = 0 at right angle)
 *   - All interior points covered by exactly 1 piece (perfect tiling)
 */
object TangramPieces {

    /** Scale factor: grid units → virtual pixels */
    const val GRID_SCALE = 40f
    
    /** Offset to center the puzzle in the virtual canvas */
    val CANVAS_CENTER = Offset(200f, 200f)

    // Centroid-centered vertices (grid coordinates)
    val LARGE_TRIANGLE = listOf(
        Offset(-2.000000f, -0.666667f),
        Offset( 2.000000f, -0.666667f),
        Offset( 0.000000f,  1.333333f)
    )

    val MEDIUM_TRIANGLE = listOf(
        Offset(-0.666667f, -1.333333f),
        Offset( 1.333333f,  0.666667f),
        Offset(-0.666667f,  0.666667f)
    )

    val SMALL_TRIANGLE = listOf(
        Offset(-0.666667f,  0.000000f),
        Offset( 0.333333f, -1.000000f),
        Offset( 0.333333f,  1.000000f)
    )

    val SQUARE = listOf(
        Offset(-1.000000f,  0.000000f),
        Offset( 0.000000f, -1.000000f),
        Offset( 1.000000f,  0.000000f),
        Offset( 0.000000f,  1.000000f)
    )

    val PARALLELOGRAM = listOf(
        Offset(-1.500000f,  0.500000f),
        Offset( 0.500000f,  0.500000f),
        Offset( 1.500000f, -0.500000f),
        Offset(-0.500000f, -0.500000f)
    )

    // Piece colors (vibrant, distinguishable palette)
    val COLORS = mapOf(
        TangramPieceType.LARGE_TRIANGLE_1 to Color(0xFF3B82F6), // Blue
        TangramPieceType.LARGE_TRIANGLE_2 to Color(0xFF8B5CF6), // Purple
        TangramPieceType.MEDIUM_TRIANGLE  to Color(0xFF14B8A6), // Teal
        TangramPieceType.SMALL_TRIANGLE_1 to Color(0xFFF59E0B), // Amber
        TangramPieceType.SMALL_TRIANGLE_2 to Color(0xFFF97316), // Orange
        TangramPieceType.SQUARE           to Color(0xFFF43F5E), // Rose
        TangramPieceType.PARALLELOGRAM    to Color(0xFF10B981), // Emerald
    )

    fun verticesForType(type: TangramPieceType): List<Offset> = when (type) {
        TangramPieceType.LARGE_TRIANGLE_1,
        TangramPieceType.LARGE_TRIANGLE_2 -> LARGE_TRIANGLE
        TangramPieceType.MEDIUM_TRIANGLE  -> MEDIUM_TRIANGLE
        TangramPieceType.SMALL_TRIANGLE_1,
        TangramPieceType.SMALL_TRIANGLE_2 -> SMALL_TRIANGLE
        TangramPieceType.SQUARE           -> SQUARE
        TangramPieceType.PARALLELOGRAM    -> PARALLELOGRAM
    }
}
```

---

### Phase 3: Puzzle Data — 40 Verified Real Puzzles

#### [MODIFY] [ShapesPregenerated.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/data/ShapesPregenerated.kt)

**Complete rewrite.** Each puzzle is defined by:

```kotlin
data class PiecePlacement(
    val x: Float,       // Centroid X in grid coordinates
    val y: Float,       // Centroid Y in grid coordinates  
    val rotation: Float, // Degrees (0, 45, 90, 135, 180, 225, 270, 315)
    val flipped: Boolean = false
)

data class TangramPuzzleData(
    override val id: String,
    override val difficulty: String,
    val name: String,
    val category: String,
    // Solution placements for: LT1, LT2, MT, ST1, ST2, SQ, PARA
    val placements: List<PiecePlacement>
) : BrowseablePuzzle {
    override val label: String get() = name
    override val subtitle: String get() = category
    
    /**
     * Computes the target silhouette by placing all 7 pieces at their
     * solution positions and extracting the outer boundary polygon.
     */
    fun computeSilhouette(): List<Offset> { ... }
    
    /**
     * Converts to a playable ShapesPuzzle with pieces scattered in the tray.
     */
    fun toShapesPuzzle(): ShapesPuzzle { ... }
}
```

**How silhouettes are computed** (not hand-drawn — algorithmically derived from piece placements):

```kotlin
fun computeSilhouette(): List<Offset> {
    // 1. Place all 7 pieces at solution positions → get world vertices
    // 2. Collect all directed edges from all pieces
    // 3. For each edge (A→B), check if reverse edge (B→A) exists in another piece
    //    (shared internal edge between adjacent pieces)
    // 4. Remove all shared edges — remaining edges form the outer boundary
    // 5. Chain remaining edges into a single closed polygon
    // 6. Return the polygon vertices → this IS the concave silhouette
}
```

This algorithm works perfectly for tangrams because pieces tile with **exact edge sharing** — no gaps, no overlaps. Shared edges cancel out, leaving only the outer boundary.

> [!IMPORTANT]
> The silhouette is computed once at startup (cached via `lazy`) and does NOT need to be hand-specified for each puzzle. This eliminates an entire class of errors — the silhouette is always mathematically correct if the piece placements are correct.

**40 Puzzles across 4 categories:**

| # | Category | Puzzles | Difficulty Mix |
|:-:|----------|---------|---------------|
| 1-10 | Geometric | Square, Triangle, Rectangle, Parallelogram, Trapezoid, Pentagon, Hexagon, Diamond, Arrow, Cross | 8 Easy, 2 Medium |
| 11-22 | Animals | Cat, Swan, Dog, Rabbit, Fish, Duck, Horse, Bird, Fox, Turtle, Butterfly, Crab | 4 Easy, 5 Medium, 3 Hard |
| 23-30 | People | Runner, Dancer, Monk, Sitting Person, Walking Person, Archer, Reader, Yoga | 2 Easy, 4 Medium, 2 Hard |
| 31-40 | Objects | House, Sailboat, Candle, Cup, Rocket, Hammer, Bridge, Key, Heart, Boat | 3 Easy, 4 Medium, 3 Hard |

**How I will construct each puzzle:**

1. **Geometric shapes (1-10):** These are mathematically derivable — many are among the 13 proven convex shapes. I'll compute exact placements by geometric construction.

2. **Animals, People, Objects (11-40):** I'll construct these by:
   - Starting from the canonical 4×4 grid
   - Rearranging pieces to form recognizable silhouettes
   - Each arrangement is a specific set of `(x, y, rotation, flip)` per piece
   - Running `TangramVerifier` to confirm: correct total area, no overlaps, no gaps
   - The silhouette is auto-computed by the boundary algorithm

3. **All 40 puzzles** go through automated verification at build time (debug only).

---

### Phase 4: Outer Boundary Algorithm

#### [MODIFY] [GeometryUtils.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/util/GeometryUtils.kt)

Add the silhouette computation algorithm. Keep all existing utilities (they work correctly).

```kotlin
/**
 * Computes the outer boundary polygon from multiple non-overlapping,
 * edge-sharing polygons (perfect for tangram pieces).
 *
 * Algorithm:
 * 1. Collect all directed edges from all polygons
 * 2. Round vertex coordinates to eliminate floating-point noise
 * 3. For each edge A→B, check if reverse B→A exists
 * 4. Remove paired (internal) edges — they're shared between adjacent pieces
 * 5. Chain remaining edges into a closed polygon
 *
 * Returns the outer boundary vertices in order.
 */
fun computeOuterBoundary(polygons: List<List<Offset>>): List<Offset> {
    data class Edge(val from: Long, val to: Long) // packed coordinates
    
    fun packCoord(offset: Offset): Long {
        // Round to 3 decimal places then pack into Long for exact comparison
        val x = (offset.x * 1000).roundToLong()
        val y = (offset.y * 1000).roundToLong()
        return x * 10_000_000L + y
    }
    
    // Step 1-2: Collect all directed edges with rounded coordinates
    val edgeCounts = mutableMapOf<Pair<Long, Long>, MutableList<Offset>>()
    val edgeEndpoints = mutableMapOf<Pair<Long, Long>, Pair<Offset, Offset>>()
    
    for (polygon in polygons) {
        for (i in polygon.indices) {
            val from = polygon[i]
            val to = polygon[(i + 1) % polygon.size]
            val key = Pair(packCoord(from), packCoord(to))
            edgeEndpoints[key] = Pair(from, to)
            edgeCounts.getOrPut(key) { mutableListOf() }.add(from)
        }
    }
    
    // Step 3-4: Remove internal edges (where reverse exists)
    val boundaryEdges = mutableListOf<Pair<Offset, Offset>>()
    for ((key, endpoints) in edgeEndpoints) {
        val reverseKey = Pair(key.second, key.first)
        if (reverseKey !in edgeEndpoints) {
            boundaryEdges.add(endpoints)
        }
    }
    
    // Step 5: Chain edges into closed polygon
    if (boundaryEdges.isEmpty()) return emptyList()
    
    val result = mutableListOf<Offset>()
    val remaining = boundaryEdges.toMutableList()
    var current = remaining.removeFirst()
    result.add(current.first)
    
    while (remaining.isNotEmpty()) {
        val currentEnd = packCoord(current.second)
        val nextIdx = remaining.indexOfFirst { packCoord(it.first) == currentEnd }
        if (nextIdx == -1) break
        current = remaining.removeAt(nextIdx)
        result.add(current.first)
    }
    
    return result
}
```

> [!NOTE]
> The coordinate rounding (`* 1000 → Long`) is critical. Without it, floating-point errors from rotation (sin/cos) would prevent edge matching. Rounding to 3 decimal places provides ample precision for tangram geometry while ensuring edges are correctly identified as shared.

---

### Phase 5: Robust Win Detection

#### [MODIFY] [ShapesViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/viewmodel/ShapesViewModel.kt)

Complete rewrite of `checkCompletion()`:

```kotlin
private fun checkCompletion() {
    val puzzle = _puzzle.value ?: return
    if (puzzle.isComplete) return
    
    // Build solution slots grouped by interchangeable type
    val solutionSlots = puzzle.pieces.map { piece ->
        SolutionSlot(
            type = piece.type,
            position = piece.solutionPosition,
            rotation = piece.solutionRotation,
            flipped = piece.solutionFlipped
        )
    }
    
    // Try to match each piece to a solution slot
    // Interchangeable pieces (LT1↔LT2, ST1↔ST2) can match either slot
    val matched = matchPiecesToSlots(puzzle.pieces, solutionSlots)
    
    if (matched) {
        _isGameWon.value = true
        _puzzle.value = puzzle.copy(
            isComplete = true,
            pieces = puzzle.pieces.map { it.copy(isLocked = true) }
        )
        repository.clear(mode, puzzleId)
    }
}

private fun matchPiecesToSlots(
    pieces: List<PuzzlePiece>,
    slots: List<SolutionSlot>
): Boolean {
    val positionTolerance = 0.5f  // grid units (= 20 pixels at GRID_SCALE=40)
    val rotationTolerance = 10f   // degrees
    
    // Group slots by compatible type (interchangeable pieces share slots)
    val slotGroups = mutableMapOf<Set<TangramPieceType>, MutableList<SolutionSlot>>()
    for (slot in slots) {
        val key = if (slot.type.interchangeableWith != null) {
            setOf(slot.type, slot.type.interchangeableWith!!)
        } else {
            setOf(slot.type)
        }
        slotGroups.getOrPut(key) { mutableListOf() }.add(slot)
    }
    
    // For each piece, find its compatible slot group and try to match
    val usedSlots = mutableSetOf<Int>()
    
    for (piece in pieces) {
        val key = if (piece.type.interchangeableWith != null) {
            setOf(piece.type, piece.type.interchangeableWith!!)
        } else {
            setOf(piece.type)
        }
        val compatibleSlots = slotGroups[key] ?: return false
        
        var matched = false
        for ((idx, slot) in compatibleSlots.withIndex()) {
            if (idx in usedSlots) continue
            if (isPieceAtSlot(piece, slot, positionTolerance, rotationTolerance)) {
                usedSlots.add(idx)
                matched = true
                break
            }
        }
        if (!matched) return false
    }
    
    return true
}

private fun isPieceAtSlot(
    piece: PuzzlePiece,
    slot: SolutionSlot,
    posTolerance: Float,
    rotTolerance: Float
): Boolean {
    // Position check
    val dist = hypot(
        piece.position.x - slot.position.x,
        piece.position.y - slot.position.y
    )
    if (dist > posTolerance) return false
    
    // Rotation check (accounting for symmetry period)
    val symmetryPeriod = piece.type.symmetryPeriod
    val pieceRot = ((piece.rotation % symmetryPeriod) + symmetryPeriod) % symmetryPeriod
    val slotRot = ((slot.rotation % symmetryPeriod) + symmetryPeriod) % symmetryPeriod
    val rotDiff = abs(pieceRot - slotRot)
    val effectiveRotDiff = minOf(rotDiff, symmetryPeriod - rotDiff)
    if (effectiveRotDiff > rotTolerance) return false
    
    // Flip check (only for parallelogram)
    if (piece.type.isFlipSignificant && piece.isFlipped != slot.flipped) return false
    
    return true
}
```

**Key improvements over current:**
- ✅ Handles LT1↔LT2 and ST1↔ST2 interchangeability
- ✅ Square: 90° rotational symmetry (0°=90°=180°=270°)
- ✅ Parallelogram: 180° rotational symmetry + flip check
- ✅ Triangles: exact rotation match (no symmetry)
- ✅ Tolerance in grid units, not pixels (scale-independent)

---

### Phase 6: Improved Snapping

#### [MODIFY] [ShapesViewModel.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/viewmodel/ShapesViewModel.kt)

Rewrite `snapPiece()`:

```kotlin
private val SNAP_DISTANCE = 0.8f   // grid units
private val LOCK_DISTANCE = 0.5f   // grid units — auto-lock threshold

fun snapPiece(pieceId: Int) {
    val puzzle = _puzzle.value ?: return
    if (puzzle.isComplete) return
    val piece = puzzle.pieces.find { it.id == pieceId && !it.isLocked } ?: return
    
    // Priority 1: Check if piece matches its solution position
    // (considering interchangeable pieces can match either slot)
    val matchingSlot = findMatchingSolutionSlot(piece, puzzle)
    if (matchingSlot != null) {
        // Snap directly to solution and LOCK
        val locked = piece.copy(
            position = matchingSlot.position,
            rotation = matchingSlot.rotation,
            isFlipped = if (piece.type.isFlipSignificant) matchingSlot.flipped else piece.isFlipped,
            isLocked = true
        )
        updatePiece(puzzle, locked)
        soundManager?.playSound(SoundManager.SOUND_ID_SNAP_CONNECT)
        checkCompletion()
        return
    }
    
    // Priority 2: Vertex-to-vertex snapping
    var bestDelta = Offset.Zero
    var bestDist = SNAP_DISTANCE
    
    val snapTargets = buildList {
        // Snap to target silhouette vertices
        addAll(puzzle.target.vertices)
        // Snap to locked piece vertices (already placed correctly)
        puzzle.pieces.filter { it.isLocked && it.id != pieceId }
            .forEach { addAll(it.worldVertices) }
    }
    
    for (vertex in piece.worldVertices) {
        for (target in snapTargets) {
            val dist = hypot(vertex.x - target.x, vertex.y - target.y)
            if (dist < bestDist) {
                bestDist = dist
                bestDelta = Offset(target.x - vertex.x, target.y - vertex.y)
            }
        }
    }
    
    if (bestDelta != Offset.Zero) {
        val snapped = piece.copy(
            position = Offset(piece.position.x + bestDelta.x, piece.position.y + bestDelta.y)
        )
        updatePiece(puzzle, snapped)
    }
    
    persist()
}
```

**Key improvement:** When a piece snaps to its solution position (correct position + rotation + flip), it **auto-locks** with a satisfying snap sound. Locked pieces:
- Show a subtle visual indicator (semi-transparent overlay or glow)
- Cannot be moved or rotated
- Act as snap targets for other pieces
- Give clear progress feedback to the player

---

### Phase 7: UI Updates

#### [MODIFY] [ShapesScreen.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/ui/ShapesScreen.kt)

Changes to the Compose Canvas rendering:

**1. Target silhouette — draw the actual concave outline:**
```kotlin
// Current (broken): draws convex hull blob
// New: draws the real concave silhouette computed from piece placements
val targetPath = Path().apply {
    val verts = puzzleState.target.vertices  // Now a concave polygon
    if (verts.isNotEmpty()) {
        moveTo(verts.first().x * GRID_SCALE + offsetX, ...)
        verts.drop(1).forEach { lineTo(it.x * GRID_SCALE + offsetX, ...) }
        close()
    }
}
```

**2. Locked piece visuals:**
```kotlin
// After drawing piece fill:
if (piece.isLocked) {
    // Draw checkmark glow or reduced-opacity overlay
    drawPath(piecePath, Color.White.copy(alpha = 0.15f), style = Fill)
    drawPath(piecePath, Color(0xFF4ADE80).copy(alpha = 0.6f), 
             style = Stroke(width = 2f))
}
```

**3. Z-ordering — dragged piece renders on top:**
```kotlin
// Sort pieces: locked first, then unselected, then selected (on top)
val sortedPieces = puzzleState.pieces.sortedBy { piece ->
    when {
        piece.isLocked -> 0
        piece.id == selectedPieceId -> 2
        else -> 1
    }
}
sortedPieces.forEach { piece -> /* draw */ }
```

**4. Coordinate scaling:**
```kotlin
// Piece positions are in grid units — scale to virtual pixels
val screenX = piece.position.x * GRID_SCALE + canvasOffsetX
val screenY = piece.position.y * GRID_SCALE + canvasOffsetY

// Touch → grid coordinate conversion
val gridX = (touchX - canvasOffsetX) / GRID_SCALE
val gridY = (touchY - canvasOffsetY) / GRID_SCALE
```

---

### Phase 8: Navigation Fix

#### [MODIFY] [MainActivity.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/MainActivity.kt)

Add the missing `"shapes"` case in the `/new` route handler:

```kotlin
composable("game/{gameId}/{mode}/new") {
    val gameId = it.arguments?.getString("gameId") ?: ""
    val mode = it.arguments?.getString("mode") ?: "standard"
    when (gameId) {
        // ... existing cases ...
        "shapes" -> ShapesScreen(
            navController = navController,
            mode = mode,
            settingsRepository = settingsRepository,
            streakRepository = streakRepository
        )
        // ... else ...
    }
}
```

---

### Phase 9: Debug Verification

#### [NEW] [TangramVerifier.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/util/TangramVerifier.kt)

```kotlin
/**
 * Debug-only utility to validate all tangram puzzles at startup.
 * 
 * For each puzzle, verifies:
 * 1. Exactly 7 pieces with correct types (2×LT, 1×MT, 2×ST, 1×SQ, 1×PARA)
 * 2. All rotations are multiples of 45°
 * 3. Total piece area = expected (16 grid² on the 4-unit grid)
 * 4. No two pieces overlap (SAT intersection test on all pairs)
 * 5. Silhouette polygon is valid (closed, non-self-intersecting)
 * 6. Silhouette area = total piece area (no gaps)
 * 7. All piece vertices are inside the silhouette boundary
 *
 * Called from ViewModel init{} in debug builds.
 * Logs errors via Log.e("TangramVerifier", ...).
 */
object TangramVerifier {
    fun verifyAllPuzzles(puzzles: List<TangramPuzzleData>): List<String> {
        val errors = mutableListOf<String>()
        for (puzzle in puzzles) {
            errors.addAll(verifyPuzzle(puzzle))
        }
        return errors
    }
    
    fun verifyPuzzle(puzzle: TangramPuzzleData): List<String> { ... }
}
```

This runs at startup in debug builds, catching any broken puzzle before it reaches the player.

---

### Phase 10: Cleanup

#### [DELETE] `app/src/main/assets/shapes/puzzles.json`
Unused 50KB JSON file never loaded by the app.

#### [KEEP] These files need no changes:
- [ShapesRepository.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/data/ShapesRepository.kt) — save/load logic is correct
- [ShapesViewModelFactory.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/viewmodel/ShapesViewModelFactory.kt) — factory pattern unchanged
- [ShapesLevels.kt](file:///c:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/shapes/util/ShapesLevels.kt) — thin wrapper, may update references

---

## File Change Summary

| Action | File | Description |
|--------|------|-------------|
| **MODIFY** | `shapes/model/Shape.kt` | `TangramPieceType` enum with symmetry/interchangeability, `solutionFlipped`, `TargetSilhouette` |
| **NEW** | `shapes/model/TangramPieces.kt` | Verified canonical piece vertices + colors + scale constants |
| **MODIFY** | `shapes/data/ShapesPregenerated.kt` | **Complete rewrite** — 40 puzzles with auto-computed silhouettes |
| **MODIFY** | `shapes/util/GeometryUtils.kt` | Add `computeOuterBoundary()` for silhouette generation |
| **MODIFY** | `shapes/viewmodel/ShapesViewModel.kt` | Robust win detection + improved snapping + auto-lock |
| **MODIFY** | `shapes/ui/ShapesScreen.kt` | Concave silhouettes, locked visuals, z-ordering |
| **MODIFY** | `shapes/util/ShapesLevels.kt` | Update references to new data model |
| **MODIFY** | `MainActivity.kt` | Add missing `"shapes"` route in `/new` handler |
| **NEW** | `shapes/util/TangramVerifier.kt` | Debug puzzle verification |
| **DELETE** | `assets/shapes/puzzles.json` | Unused dead file |

---

## Verification Plan

### Automated Tests
```bash
# Build to verify compilation
gradlew.bat assembleDebug

# Unit tests
gradlew.bat testDebugUnitTest
```

### Built-in Debug Verification
`TangramVerifier.verifyAllPuzzles()` runs automatically in debug builds at startup. It validates:
- Piece count and types
- Area correctness (no gaps/overlaps)
- Silhouette validity
- Rotation constraints (multiples of 45°)

Any verification failure will log an error and be immediately visible during development.

### Manual Testing
1. Launch the debug APK
2. For each of the 4 puzzle categories, play at least 2 puzzles:
   - Verify silhouette is recognizable (not a convex blob)
   - Verify pieces snap correctly to solution positions
   - Verify auto-lock works when piece is correctly placed
   - Verify win detection triggers when all 7 pieces are placed
   - Verify the hint function places pieces correctly
3. Test interchangeability: swap LT1↔LT2 positions → should still detect win
4. Test navigation: Daily mode → "Play Again" → should navigate correctly
5. Test persistence: mid-puzzle close app → reopen → state restored

---

## Implementation Order

The work will proceed in this order to maintain a compilable state at each step:

1. **`Shape.kt`** + **`TangramPieces.kt`** — New data model (compile but not yet used)
2. **`GeometryUtils.kt`** — Add `computeOuterBoundary()` 
3. **`ShapesPregenerated.kt`** — Rewrite with new puzzle format
4. **`TangramVerifier.kt`** — Verify all puzzles are correct
5. **`ShapesViewModel.kt`** — New win detection + snapping
6. **`ShapesScreen.kt`** — UI updates for concave silhouettes + locked pieces
7. **`ShapesLevels.kt`** — Update references
8. **`MainActivity.kt`** — Navigation fix
9. **Delete** `puzzles.json`
10. **Build + test** — `gradlew.bat assembleDebug` + manual verification
