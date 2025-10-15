package com.funkyotc.puzzleverse.core.audio

import android.content.Context
import android.media.SoundPool
import com.funkyotc.puzzleverse.R

class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = SoundPool.Builder().setMaxStreams(5).build()
    private val sounds = mutableMapOf<Int, Int>()

    companion object {
        const val SOUND_ID_CLICK = 1
    }

    fun loadSounds() {
        sounds[SOUND_ID_CLICK] = soundPool?.load(context, R.raw.click, 1) ?: 0
    }

    fun playSound(soundId: Int) {
        sounds[soundId]?.let { soundPool?.play(it, 1f, 1f, 1, 0, 1f) }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}