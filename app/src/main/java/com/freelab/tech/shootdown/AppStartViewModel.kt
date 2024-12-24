package com.freelab.tech.shootdown

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStartViewModel: ViewModel() {

    companion object {
        private const val LOADING_TIME = 8_000L
        private const val TIMER_INTERVAL = 1_000L
    }

    private val systemUtils = ShootDownApp.INSTANCE.getSystemUtils()

    private val _progressLiveData = MutableLiveData<Int>()
    val progressLiveData: LiveData<Int>
        get() = _progressLiveData

    private val _volumeAlertLiveData = MutableLiveData<Int>()
    val volumeAlertLiveData: LiveData<Int>
        get() = _volumeAlertLiveData

    fun showProgress() {
        val timer = object: CountDownTimer(LOADING_TIME, TIMER_INTERVAL) {
            override fun onTick(timeInMillis: Long) {
                val progress = (((LOADING_TIME - timeInMillis).toDouble() / LOADING_TIME) * 100).toInt()
                _progressLiveData.value = progress
            }

            override fun onFinish() {
                _progressLiveData.value = -1
            }

        }

        timer.start()
    }

    fun fetchSystemVolume() {
        systemUtils.fetchSystemVolume { volume ->
            _volumeAlertLiveData.postValue(volume)
        }
    }

}