package com.funkyotc.puzzleverse.core.audio

import android.content.Context
import android.media.SoundPool
import com.funkyotc.puzzleverse.R

class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = SoundPool.Builder().setMaxStreams(5).build()
    private val sounds = mutableMapOf<Int, Int>()

    companion object {
        const val SOUND_ID_CLICK = 1
        const val SOUND_ID_SUCCESS = 2
        const val SOUND_ID_FAILURE = 3
        const val SOUND_ID_VICTORY = 4
    }

    fun loadSounds() {
        sounds[SOUND_ID_CLICK] = soundPool?.load(context, R.raw.click, 1) ?: 0
        sounds[SOUND_ID_SUCCESS] = soundPool?.load(context, R.raw.success, 1) ?: 0
        sounds[SOUND_ID_FAILURE] = soundPool?.load(context, R.raw.failure, 1) ?: 0
        sounds[SOUND_ID_VICTORY] = soundPool?.load(context, R.raw.victory, 1) ?: 0
    }

    fun playSound(soundId: Int, rate: Float = 1f) {
        val sharedPreferences = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean("sound_effects_enabled", true)) return
        sounds[soundId]?.let { soundPool?.play(it, 1f, 1f, 1, 0, rate) }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}