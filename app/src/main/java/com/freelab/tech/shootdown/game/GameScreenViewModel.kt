package com.freelab.tech.shootdown.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freelab.tech.shootdown.model.LevelInfo
import com.freelab.tech.shootdown.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameScreenViewModel: ViewModel() {

    private val _currentFragmentState = MutableStateFlow(LevelInfo.DASHBOARD)
    val currentFragmentState: StateFlow<LevelInfo>
        get() = _currentFragmentState

    private val _timerLiveData = MutableLiveData<Event<Int>>()
    val timerLiveData: LiveData<Event<Int>>
        get() = _timerLiveData

    private var timer: CountDownTimer? = null
    
    fun startTimer(time: Long) {
        if (timer != null) {
            return
        }
        timer = object: CountDownTimer(time, 1000) {
            override fun onTick(time: Long) {
                _timerLiveData.value = Event((time/1000).toInt())
            }

            override fun onFinish() {
                _timerLiveData.value = Event(-1)
                timer = null
            }

        }

        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer?.onFinish()
    }

    fun startLevel(level: LevelInfo) {
        _currentFragmentState.value = level
    }

    fun returnToDashboard() {
        _currentFragmentState.value = LevelInfo.DASHBOARD
    }

    fun resetTimerValue() {

    }
    
}