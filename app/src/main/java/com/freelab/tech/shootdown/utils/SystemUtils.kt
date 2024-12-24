package com.freelab.tech.shootdown.utils

import android.app.Application
import android.content.Context
import android.media.AudioManager

class SystemUtils private constructor(private val app: Application) {

    companion object {

        private var INSTANCE: SystemUtils? = null

        fun getInstance(app: Application): SystemUtils {
            if (INSTANCE == null) {
                INSTANCE = SystemUtils(app)
            }

            return INSTANCE!!
        }
    }

    fun fetchSystemVolume(listener: (volume: Int) -> Unit) {
        val audioManager: AudioManager = app.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        listener.invoke(volume)
    }

}