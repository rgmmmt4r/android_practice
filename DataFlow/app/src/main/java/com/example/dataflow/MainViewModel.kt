package com.example.dataflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.max

class MainViewModel : ViewModel() {
    companion object{
        private const val DEFAULT_TIME = 60
        private const val DEFAULT_PROGRESS = 100
        private const val DEFAULT_MULTIPLIER = 1
    }

    private val _timeLeft = MutableStateFlow(DEFAULT_TIME)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    private val _progress = MutableStateFlow(DEFAULT_PROGRESS)
    val progress: StateFlow<Int> = _progress.asStateFlow()


    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _multiplier = MutableStateFlow(DEFAULT_MULTIPLIER)
    val multiplier: StateFlow<Int> = _multiplier

    private val _formattedTimeLeft = MutableStateFlow(formatTime(DEFAULT_TIME))
    val formattedTimeLeft: StateFlow<String> = _formattedTimeLeft.asStateFlow()

    private var job: Job? = null


    private var maxTime = DEFAULT_TIME

    fun startOrPauseTimer(){
        if (job == null){
            _isRunning.value = true
            job = viewModelScope.launch {
                while (_timeLeft.value>0){
                    delay(1000/_multiplier.value.toLong())
                    _timeLeft.value -=1
                    updateProgress()
                }
                job = null
                _isRunning.value = false
            }
        }else{
            job?.cancel()
            job = null
            _isRunning.value = false
        }
    }

    fun addFiveSeconds(){
        _timeLeft.value += 5
        if (_timeLeft.value> maxTime){
            maxTime = _timeLeft.value
        }
        updateProgress()
    }

    fun addMultiplier(){
        _multiplier.value = when(_multiplier.value){
            1->2
            2->4
            4->8
            else->1
        }
    }

    fun resetTimer(){
        _timeLeft.value = DEFAULT_TIME
        maxTime = DEFAULT_TIME
        _progress.value = DEFAULT_PROGRESS
        _isRunning.value = false
        job?.cancel()
        job = null
        _formattedTimeLeft.value = formatTime(DEFAULT_TIME)
    }

    private fun updateProgress(){
    _progress.value = (_timeLeft.value.toDouble()/maxTime*100).toInt()
        _formattedTimeLeft.value = formatTime(_timeLeft.value)
    }

    private fun formatTime(seconds: Int):String{
        val minutes = seconds/60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d",minutes,remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}