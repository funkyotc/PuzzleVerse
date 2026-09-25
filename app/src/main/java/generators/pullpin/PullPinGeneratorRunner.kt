package generators.pullpin

import com.funkyotc.puzzleverse.pullpin.rescue.RescueCampaign
import com.funkyotc.puzzleverse.pullpin.rescue.RescueSession
import com.funkyotc.puzzleverse.pullpin.rescue.RescueStatus

/** Validate authored, tick-stamped rescue solutions without changing checked-in content. */
fun main() {
    RescueCampaign.levels.forEach { level ->
        val session = RescueSession(level)
        session.start()
        while (session.state.status == RescueStatus.RUNNING && session.state.tick < 4500) {
            level.solution.filter { it.tick == session.state.tick }.forEach { action ->
                check(session.pull(action.pinId)) { "${level.id}: rejected ${action.pinId} at ${action.tick}" }
            }
            session.step()
        }
        check(session.state.status == RescueStatus.WON) { "${level.id}: ${session.state.status} at ${session.state.tick}" }
        println("Verified ${level.id}: ${level.title} at tick ${session.state.tick}")
    }
}
