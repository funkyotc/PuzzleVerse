package com.funkyotc.puzzleverse.pullpin.rescue

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.funkyotc.puzzleverse.core.data.SaveStateRepository

/** Retire only Pull the Pin's old route metadata; leave completion and streak history intact. */
class RescueMigration(private val prefs: SharedPreferences, private val saves: SaveStateRepository) {
    fun migrate() {
        if (prefs.getInt("campaign_version", 0) >= RescueCampaign.VERSION) return
        saves.clearSaveState("pullpin")
        prefs.edit(commit = true) { putInt("campaign_version", RescueCampaign.VERSION) }
    }

    companion object {
        fun forContext(context: Context, saves: SaveStateRepository) = RescueMigration(
            context.getSharedPreferences("PullPinRescueMigration", Context.MODE_PRIVATE), saves)
    }
}
