package com.hurreytech.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {

    private var job: Job? = null
    private var startTime = 0L
    private var accumulatedTime = 0L

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _laps = MutableStateFlow<List<Long>>(emptyList())
    val laps: StateFlow<List<Long>> = _laps

    fun start() {
        if (_isRunning.value) return
        _isRunning.value = true
        startTime = System.currentTimeMillis()

        job = viewModelScope.launch {
            while (_isRunning.value) {
                delay(10)
                val now = System.currentTimeMillis()
                _elapsedTime.value = accumulatedTime + (now - startTime)
            }
        }
    }

    fun stop() {
        if (!_isRunning.value) return
        _isRunning.value = false
        job?.cancel()
        accumulatedTime += System.currentTimeMillis() - startTime
    }

    fun reset() {
        job?.cancel()
        _isRunning.value = false
        startTime = 0L
        accumulatedTime = 0L
        _elapsedTime.value = 0L
        _laps.value = emptyList()
    }

    fun recordLap() {
        if (_isRunning.value) {
            _laps.value = _laps.value + _elapsedTime.value
        }
    }
}
