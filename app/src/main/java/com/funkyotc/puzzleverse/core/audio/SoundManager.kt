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
        SoundPool.Builder().setMaxStreams(24).setAudioAttributes(aa).build()
    }
    private val sounds = mutableMapOf<Int, Int>()
    private val lastPlayedAt = mutableMapOf<Int, Long>()

    companion object {
        const val SOUND_ID_CLICK = 1
        const val SOUND_ID_SUCCESS = 2
        const val SOUND_ID_FAILURE = 3
        const val SOUND_ID_VICTORY = 4
        const val SOUND_ID_TILE_PLACE = 5
        const val SOUND_ID_BLOCK_DROP = 6
        const val SOUND_ID_PIECE_SLIDE = 7
        const val SOUND_ID_SNAP_CONNECT = 8
        const val SOUND_ID_LINE_CLEAR = 9
        const val SOUND_ID_MERGE_POP = 10
        const val SOUND_ID_GLASS_CHIME = 11
        const val SOUND_ID_TRIUMPHANT_CHIME = 12
        const val SOUND_ID_THUD_DULL = 13
        const val SOUND_ID_KEY_PRESS = 14
        const val SOUND_ID_PENCIL_ERASE = 15
        const val SOUND_ID_BLAST_EXPLODE = 16
        const val SOUND_ID_LASER_SHOOT = 17
        const val SOUND_ID_METAL_SHING = 18
        const val SOUND_ID_LIQUID_POUR = 19
        const val SOUND_ID_COIN_COLLECT = 20
        const val SOUND_ID_CHESS_LAND = 21
    }

    fun loadSounds() {
        sounds[SOUND_ID_CLICK] = soundPool?.load(context, R.raw.ui_click, 1) ?: 0
        sounds[SOUND_ID_SUCCESS] = soundPool?.load(context, R.raw.success, 1) ?: 0
        sounds[SOUND_ID_FAILURE] = soundPool?.load(context, R.raw.failure, 1) ?: 0
        sounds[SOUND_ID_VICTORY] = soundPool?.load(context, R.raw.victory, 1) ?: 0
        sounds[SOUND_ID_TILE_PLACE] = soundPool?.load(context, R.raw.tile_place, 1) ?: 0
        sounds[SOUND_ID_BLOCK_DROP] = soundPool?.load(context, R.raw.block_drop, 1) ?: 0
        sounds[SOUND_ID_PIECE_SLIDE] = soundPool?.load(context, R.raw.piece_slide, 1) ?: 0
        sounds[SOUND_ID_SNAP_CONNECT] = soundPool?.load(context, R.raw.snap_connect, 1) ?: 0
        sounds[SOUND_ID_LINE_CLEAR] = soundPool?.load(context, R.raw.line_clear, 1) ?: 0
        sounds[SOUND_ID_MERGE_POP] = soundPool?.load(context, R.raw.merge_pop, 1) ?: 0
        sounds[SOUND_ID_GLASS_CHIME] = soundPool?.load(context, R.raw.glass_chime, 1) ?: 0
        sounds[SOUND_ID_TRIUMPHANT_CHIME] = soundPool?.load(context, R.raw.triumphant_chime, 1) ?: 0
        sounds[SOUND_ID_THUD_DULL] = soundPool?.load(context, R.raw.thud_dull, 1) ?: 0
        sounds[SOUND_ID_KEY_PRESS] = soundPool?.load(context, R.raw.key_press, 1) ?: 0
        sounds[SOUND_ID_PENCIL_ERASE] = soundPool?.load(context, R.raw.pencil_erase, 1) ?: 0
        sounds[SOUND_ID_BLAST_EXPLODE] = soundPool?.load(context, R.raw.blast_explode, 1) ?: 0
        sounds[SOUND_ID_LASER_SHOOT] = soundPool?.load(context, R.raw.laser_shoot, 1) ?: 0
        sounds[SOUND_ID_METAL_SHING] = soundPool?.load(context, R.raw.metal_shing, 1) ?: 0
        sounds[SOUND_ID_LIQUID_POUR] = soundPool?.load(context, R.raw.liquid_pour, 1) ?: 0
        sounds[SOUND_ID_COIN_COLLECT] = soundPool?.load(context, R.raw.coin_collect, 1) ?: 0
        sounds[SOUND_ID_CHESS_LAND] = soundPool?.load(context, R.raw.chess_land, 1) ?: 0
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