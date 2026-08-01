package com.funkyotc.puzzleverse.core.audio

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import com.funkyotc.puzzleverse.core.data.InMemorySharedPreferences
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FakeSoundContext : ContextWrapper(null) {
    val prefsMap = mutableMapOf<String, SharedPreferences>()

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        return prefsMap.getOrPut(name ?: "default") { InMemorySharedPreferences() }
    }

    override fun getApplicationContext(): Context = this
}

@org.junit.Ignore("SoundManager requires Android SoundPool hardware context")
class SoundManagerTest {

    private lateinit var context: FakeSoundContext
    private lateinit var soundManager: SoundManager
    private lateinit var settingsPrefs: SharedPreferences

    @Before
    fun setUp() {
        context = FakeSoundContext()
        soundManager = SoundManager(context)
        settingsPrefs = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
    }

    @Test
    fun testSoundDisabledSuppression() {
        // Disable sound effects in SharedPreferences
        settingsPrefs.edit().putBoolean("sound_effects_enabled", false).apply()

        // Calling playSound when sound_effects_enabled is false should return safely without error
        try {
            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
            soundManager.playSound(SoundManager.SOUND_ID_GLASS_CHIME, volume = 0.2f)
        } catch (e: Exception) {
            fail("playSound threw exception when sound was disabled: ${e.message}")
        }
    }

    @Test
    fun testSoundEnabledAndVolumeOverrides() {
        // Enable sound effects
        settingsPrefs.edit().putBoolean("sound_effects_enabled", true).apply()

        // Calling playSound with default volume and custom volume override
        try {
            // Glass chime (softened default 0.35f)
            soundManager.playSound(SoundManager.SOUND_ID_GLASS_CHIME)
            // Custom volume override
            soundManager.playSound(SoundManager.SOUND_ID_GLASS_CHIME, volume = 0.2f)
            // Normal click (default 0.6f)
            soundManager.playSound(SoundManager.SOUND_ID_CLICK)
        } catch (e: Exception) {
            fail("playSound threw exception when playing sounds: ${e.message}")
        }
    }

    @Test
    fun testRapidTappingNoCrashOrLeak() {
        settingsPrefs.edit().putBoolean("sound_effects_enabled", true).apply()

        // Simulate rapid tapping (e.g. 100 rapid playSound calls with cooldowns and without cooldowns)
        try {
            for (i in 1..100) {
                soundManager.playSound(SoundManager.SOUND_ID_GLASS_CHIME, cooldownMs = 50L)
                soundManager.playSound(SoundManager.SOUND_ID_CLICK, volume = 0.1f)
            }
        } catch (e: Exception) {
            fail("Rapid tapping caused exception: ${e.message}")
        } finally {
            soundManager.release()
        }
    }
}
