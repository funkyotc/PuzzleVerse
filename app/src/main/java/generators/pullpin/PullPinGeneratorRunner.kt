package generators.pullpin

import com.funkyotc.puzzleverse.pullpin.data.GameStatus
import com.funkyotc.puzzleverse.pullpin.data.PullPinPregenerated
import com.funkyotc.puzzleverse.pullpin.physics.PullPinSession

/** The campaign is authored in Kotlin. Verify it without overwriting its recipes. */
fun main() {
    PullPinPregenerated.ALL_LEVELS.forEach { level ->
        val session = PullPinSession(level)
        repeat(120) { session.step() }
        level.solution.forEach { pin ->
            check(session.pull(pin)) { "${level.id}: cannot pull $pin" }
            repeat(300) { session.step() }
        }
        check(session.state.status == GameStatus.WON) { "${level.id}: ${session.state.lostReason ?: "not all balls saved"}" }
        println("Verified ${level.id}: ${level.title}")
    }
}
