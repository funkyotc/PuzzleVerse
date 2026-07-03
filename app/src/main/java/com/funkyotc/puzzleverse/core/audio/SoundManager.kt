package com.funkyotc.puzzleverse.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.funkyotc.puzzleverse.R

class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = run {
        val aa = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(16).setAudioAttributes(aa).build()
    }
    private val sounds = mutableMapOf<Int, Int>()
    private val lastPlayedAt = mutableMapOf<Int, Long>()

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

    fun playSound(soundId: Int, rate: Float = 1f, cooldownMs: Long = 0L) {
        val sharedPreferences = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean("sound_effects_enabled", true)) return

        if (cooldownMs > 0) {
            val now = System.currentTimeMillis()
            val last = lastPlayedAt[soundId] ?: 0L
            if (now - last < cooldownMs) return
            lastPlayedAt[soundId] = now
        }

        sounds[soundId]?.let { soundPool?.play(it, 0.6f, 0.6f, 1, 0, rate) }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}