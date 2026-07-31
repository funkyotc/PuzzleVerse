package com.funkyotc.puzzleverse.bonza.generator

import com.funkyotc.puzzleverse.bonza.data.BonzaPuzzleTheme
import com.funkyotc.puzzleverse.bonza.data.PregeneratedBonza
import java.io.File
import kotlin.random.Random

/**
 * Standalone runner to generate Bonza theme grid layouts and output bonza/puzzles.json and BonzaPregenerated.kt.
 */
fun main(args: Array<String>) {
    println("================================================================")
    println("BONZA GENERATOR RUNNER")
    println("================================================================")

    val themes = listOf(
        BonzaPuzzleTheme("Fruits", listOf("APPLE", "BANANA", "ORANGE", "GRAPE", "PEAR", "MANGO", "KIWI", "MELON", "LEMON", "LIME")),
        BonzaPuzzleTheme("Animals", listOf("LION", "TIGER", "BEAR", "ELEPHANT", "MONKEY", "ZEBRA", "GIRAFFE", "HIPPO", "RHINO", "PANDA")),
        BonzaPuzzleTheme("Planets", listOf("MERCURY", "VENUS", "EARTH", "MARS", "JUPITER", "SATURN", "URANUS", "NEPTUNE")),
        BonzaPuzzleTheme("Colors", listOf("RED", "BLUE", "GREEN", "YELLOW", "PURPLE", "ORANGE", "INDIGO", "VIOLET", "CYAN", "MAGENTA", "BROWN", "BLACK", "WHITE")),
        BonzaPuzzleTheme("Countries", listOf("AUSTRALIA", "BRAZIL", "CANADA", "DENMARK", "EGYPT", "FRANCE", "GERMANY", "INDIA", "JAPAN", "KENYA", "MEXICO", "NORWAY", "SPAIN", "CHINA", "ITALY")),
        BonzaPuzzleTheme("Vegetables", listOf("CARROT", "POTATO", "ONION", "CORN", "PEAS", "BEANS", "LETTUCE", "SPINACH", "BROCCOLI", "PEPPER")),
        BonzaPuzzleTheme("Shapes", listOf("CIRCLE", "SQUARE", "TRIANGLE", "OVAL", "STAR", "RECTANGLE", "DIAMOND", "HEXAGON", "PENTAGON", "CUBE")),
        BonzaPuzzleTheme("Sports", listOf("SOCCER", "TENNIS", "GOLF", "RUGBY", "CRICKET", "HOCKEY", "BASEBALL", "BOXING", "JUDO", "KARATE")),
        BonzaPuzzleTheme("Insects", listOf("ANT", "BEE", "WASP", "FLY", "MOTH", "BEETLE", "SPIDER", "WORM", "SNAIL", "SLUG")),
        BonzaPuzzleTheme("Furniture", listOf("CHAIR", "TABLE", "SOFA", "BED", "DESK", "LAMP", "RUG", "SHELF", "STOOL", "BENCH")),
        BonzaPuzzleTheme("Vehicles", listOf("CAR", "BUS", "TRUCK", "BIKE", "TRAIN", "BOAT", "SHIP", "PLANE", "JET", "TAXI")),
        BonzaPuzzleTheme("Flowers", listOf("ROSE", "LILY", "TULIP", "DAISY", "POPPY", "LOTUS", "ORCHID", "VIOLET", "IRIS", "PANSY")),
        BonzaPuzzleTheme("Ocean", listOf("FISH", "SHARK", "WHALE", "CRAB", "SEAL", "CORAL", "WAVE", "SAND", "SHELL", "KELP")),
        BonzaPuzzleTheme("Weather", listOf("SUN", "RAIN", "SNOW", "WIND", "STORM", "CLOUD", "FOG", "HAIL", "MIST", "HEAT")),
        BonzaPuzzleTheme("Tools", listOf("HAMMER", "SAW", "DRILL", "WRENCH", "PLIERS", "NAIL", "SCREW", "BOLT", "AXE", "FILE")),
        BonzaPuzzleTheme("Clothing", listOf("SHIRT", "PANTS", "DRESS", "SKIRT", "HAT", "SOCK", "SHOE", "COAT", "VEST", "TIE")),
        BonzaPuzzleTheme("Body Parts", listOf("HEAD", "EYE", "EAR", "NOSE", "MOUTH", "ARM", "HAND", "LEG", "FOOT", "KNEE")),
        BonzaPuzzleTheme("Family", listOf("MOM", "DAD", "SISTER", "BROTHER", "AUNT", "UNCLE", "COUSIN", "GRANNY", "SON", "NIES")),
        BonzaPuzzleTheme("School", listOf("BOOK", "PEN", "PENCIL", "DESK", "RULER", "PAPER", "GLUE", "CHALK", "CLASS", "MATH")),
        BonzaPuzzleTheme("Music", listOf("DRUM", "GUITAR", "PIANO", "FLUTE", "HORN", "VIOLIN", "CELLO", "HARP", "BASS", "SONG")),
        BonzaPuzzleTheme("Kitchen", listOf("FORK", "SPOON", "KNIFE", "PLATE", "BOWL", "CUP", "MUG", "POT", "PAN", "OVEN")),
        BonzaPuzzleTheme("Computer", listOf("MOUSE", "KEYS", "SCREEN", "CHIP", "CODE", "DATA", "WEB", "NET", "WIFI", "LINK")),
        BonzaPuzzleTheme("Jobs", listOf("DOCTOR", "NURSE", "VET", "CHEF", "FARMER", "BAKER", "JUDGE", "PILOT", "ACTOR", "GUARD")),
        BonzaPuzzleTheme("Camping", listOf("TENT", "FIRE", "WOOD", "HIKE", "BOOT", "MAP", "ROPE", "BAG", "SWIM", "FISH")),
        BonzaPuzzleTheme("Christmas", listOf("SANTA", "ELF", "GIFT", "TOY", "TREE", "BELL", "STAR", "SNOW", "SLED", "DEER")),
        BonzaPuzzleTheme("Programming", listOf("KOTLIN", "JAVA", "PYTHON", "RUBY", "SWIFT", "RUST", "DART", "HTML", "REACT", "NODE")),
        BonzaPuzzleTheme("Supernovae", listOf("STAR", "SPACE", "DEATH", "EXPLODE", "DUST", "GAS", "LIGHT", "ENERGY", "IRON", "CORE")),
        BonzaPuzzleTheme("Geology", listOf("ROCK", "MANTLE", "CORE", "CRUST", "MAGMA", "LAVA", "STONE", "DIRT", "SOIL", "SAND")),
        BonzaPuzzleTheme("Chemistry", listOf("ATOM", "PROTON", "BOND", "ION", "MOLECULE", "GAS", "ACID", "BASE", "GOLD", "OXYGEN")),
        BonzaPuzzleTheme("Fantasy", listOf("DRAGON", "ELF", "ORC", "MAGIC", "SWORD", "KING", "QUEEN", "CASTLE", "SPELL", "WAND"))
    )

    println("Loaded ${themes.size} Bonza puzzle themes.")

    // 1. Export puzzles.json
    exportPuzzlesJson(themes)

    // 2. Validate grid layouts and generate BonzaPregenerated.kt
    generateBonzaPregeneratedFile(themes)

    println("\nBonza generation complete!")
}

private fun exportPuzzlesJson(themes: List<BonzaPuzzleTheme>) {
    val candidates = listOf(
        File("app/src/main/assets/bonza/puzzles.json"),
        File("src/main/assets/bonza/puzzles.json"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/assets/bonza/puzzles.json")
    )
    val file = candidates.find { it.exists() } ?: candidates.first()
    file.parentFile?.mkdirs()

    val sb = StringBuilder()
    sb.append("[\n")
    for ((index, t) in themes.withIndex()) {
        sb.append("  {\n")
        sb.append("    \"theme\": \"${t.theme}\",\n")
        sb.append("    \"words\": [\n")
        for ((wIndex, w) in t.words.withIndex()) {
            val comma = if (wIndex < t.words.size - 1) "," else ""
            sb.append("      \"$w\"$comma\n")
        }
        sb.append("    ]\n")
        val themeComma = if (index < themes.size - 1) "," else ""
        sb.append("  }$themeComma\n")
    }
    sb.append("]\n")

    file.writeText(sb.toString())
    println("Successfully wrote ${themes.size} themes to puzzles.json (${file.absolutePath})")
}

private fun generateBonzaPregeneratedFile(themes: List<BonzaPuzzleTheme>) {
    val candidates = listOf(
        File("app/src/main/java/com/funkyotc/puzzleverse/bonza/data/BonzaPregenerated.kt"),
        File("src/main/java/com/funkyotc/puzzleverse/bonza/data/BonzaPregenerated.kt"),
        File("C:/Users/funky/AppDev/PuzzleVerse/app/src/main/java/com/funkyotc/puzzleverse/bonza/data/BonzaPregenerated.kt")
    )
    val file = candidates.find { it.exists() } ?: candidates.first()

    val sb = StringBuilder()
    sb.append("package com.funkyotc.puzzleverse.bonza.data\n\n")
    sb.append("import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator\n")
    sb.append("import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle\n\n")
    sb.append("data class PregeneratedBonza(\n")
    sb.append("    override val id: String,\n")
    sb.append("    val theme: String,\n")
    sb.append("    val words: List<String>,\n")
    sb.append("    val fragments: List<WordFragment>\n")
    sb.append(") : BrowseablePuzzle {\n")
    sb.append("    override val difficulty: String get() = theme\n")
    sb.append("    override val label: String get() = theme\n")
    sb.append("    override val subtitle: String get() = \"\${words.size} words\"\n")
    sb.append("    \n")
    sb.append("    fun toBonzaPuzzle() = BonzaPuzzle(\n")
    sb.append("        theme = theme,\n")
    sb.append("        words = words,\n")
    sb.append("        fragments = fragments,\n")
    sb.append("        connections = emptyList(),\n")
    sb.append("        solvedFragments = emptyList()\n")
    sb.append("    )\n")
    sb.append("}\n\n")
    sb.append("object BonzaPregenerated {\n\n")
    sb.append("    private val THEMES = listOf(\n")

    for (t in themes) {
        val wordListStr = t.words.joinToString(", ") { "\"$it\"" }
        sb.append("        BonzaPuzzleTheme(\"${t.theme}\", listOf($wordListStr)),\n")
    }

    sb.append("    )\n\n")
    sb.append("    val ALL_PUZZLES: List<PregeneratedBonza> by lazy {\n")
    sb.append("        val list = mutableListOf<PregeneratedBonza>()\n")
    sb.append("        for ((index, themeObj) in THEMES.withIndex()) {\n")
    sb.append("            for (puzzleNum in 1..3) {\n")
    sb.append("                val seed = (index * 100 + puzzleNum + 42).toLong()\n")
    sb.append("                val generator = BonzaPuzzleGenerator(listOf(themeObj))\n")
    sb.append("                val generated = generator.generate(seed)\n")
    sb.append("                list.add(\n")
    sb.append("                    PregeneratedBonza(\n")
    sb.append("                        id = \"Bonza_\${themeObj.theme}_puzzle_\${puzzleNum.toString().padStart(3, '0')}\",\n")
    sb.append("                        theme = themeObj.theme,\n")
    sb.append("                        words = generated.words,\n")
    sb.append("                        fragments = generated.fragments\n")
    sb.append("                    )\n")
    sb.append("                )\n")
    sb.append("            }\n")
    sb.append("        }\n")
    sb.append("        list\n")
    sb.append("    }\n\n")
    sb.append("    val PUZZLES_BY_THEME: Map<String, List<PregeneratedBonza>> by lazy { ALL_PUZZLES.groupBy { it.theme } }\n\n")
    sb.append("    fun getPuzzleById(id: String): PregeneratedBonza? = ALL_PUZZLES.find { it.id == id }\n")
    sb.append("}\n")

    file.writeText(sb.toString())
    println("Successfully wrote updated BonzaPregenerated.kt (${file.absolutePath})")
}
