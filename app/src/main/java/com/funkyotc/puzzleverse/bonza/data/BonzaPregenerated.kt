package com.funkyotc.puzzleverse.bonza.data

import com.funkyotc.puzzleverse.bonza.generator.BonzaPuzzleGenerator
import com.funkyotc.puzzleverse.core.data.BrowseablePuzzle

data class PregeneratedBonza(
    override val id: String,
    val theme: String,
    val words: List<String>,
    val fragments: List<WordFragment>
) : BrowseablePuzzle {
    override val difficulty: String get() = theme
    override val label: String get() = theme
    override val subtitle: String get() = "${words.size} words"
    
    fun toBonzaPuzzle() = BonzaPuzzle(
        theme = theme,
        words = words,
        fragments = fragments,
        connections = emptyList(),
        solvedFragments = emptyList()
    )
}

object BonzaPregenerated {

    private val THEMES = listOf(
        BonzaPuzzleTheme("Fruits", listOf("APPLE", "BANANA", "ORANGE", "GRAPE", "PEAR", "MANGO", "KIWI", "MELON", "LEMON", "LIME")),
        BonzaPuzzleTheme("Animals", listOf("LION", "TIGER", "BEAR", "ELEPHANT", "MONKEY", "ZEBRA", "GIRAFFE", "HIPPO", "RHINO", "PANDA")),
        BonzaPuzzleTheme("Planets", listOf("MERCURY", "VENUS", "EARTH", "MARS", "JUPITER", "SATURN", "URANUS", "NEPTUNE")),
        BonzaPuzzleTheme("Colors", listOf("RED", "BLUE", "GREEN", "YELLOW", "PURPLE", "ORANGE", "INDIGO", "VIOLET", "CYAN", "MAGENTA")),
        BonzaPuzzleTheme("Countries", listOf("AUSTRALIA", "BRAZIL", "CANADA", "DENMARK", "EGYPT", "FRANCE", "GERMANY", "INDIA", "JAPAN", "KENYA")),
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
        BonzaPuzzleTheme("Programming", listOf("KOTLIN", "JAVA", "PYTHON", "RUBY", "SWIFT", "RUST", "DART", "HTML", "REACT", "NODE"))
    )

    val ALL_PUZZLES: List<PregeneratedBonza> by lazy {
        val list = mutableListOf<PregeneratedBonza>()
        for ((index, themeObj) in THEMES.withIndex()) {
            for (puzzleNum in 1..3) {
                val seed = (index * 100 + puzzleNum + 42).toLong()
                val generator = BonzaPuzzleGenerator(listOf(themeObj))
                val generated = generator.generate(seed)
                list.add(
                    PregeneratedBonza(
                        id = "Bonza_${themeObj.theme}_puzzle_${puzzleNum.toString().padStart(3, '0')}",
                        theme = themeObj.theme,
                        words = generated.words,
                        fragments = generated.fragments
                    )
                )
            }
        }
        list
    }

    val PUZZLES_BY_THEME: Map<String, List<PregeneratedBonza>> by lazy { ALL_PUZZLES.groupBy { it.theme } }

    fun getPuzzleById(id: String): PregeneratedBonza? = ALL_PUZZLES.find { it.id == id }
}
