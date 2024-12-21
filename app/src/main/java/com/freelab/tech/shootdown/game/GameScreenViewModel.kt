package com.freelab.tech.shootdown.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freelab.tech.shootdown.game.adapters.LevelsAdapter
import com.freelab.tech.shootdown.model.LevelInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameScreenViewModel: ViewModel() {

    private val _currentFragmentState = MutableStateFlow(LevelInfo.DASHBOARD)
    val currentFragmentState: StateFlow<LevelInfo>
        get() = _currentFragmentState

    private val _timerLiveData = MutableLiveData<Int>()
    val timerLiveData: LiveData<Int>
        get() = _timerLiveData
    
    fun startTimer(time: Long) {
        val timer = object: CountDownTimer(time, 1000) {
            override fun onTick(time: Long) {
                _timerLiveData.value = (time/1000).toInt()
            }

            override fun onFinish() {
                _timerLiveData.value = -1
            }

        }

        timer.start()
    }

    fun startLevel(level: LevelInfo) {
        _currentFragmentState.value = level
    }

    fun returnToDashboard() {
        _currentFragmentState.value = LevelInfo.DASHBOARD
    }
    
}