package com.hurreytech.stopwatch.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hurreytech.stopwatch.data.datastore.DataStoreManager
import com.hurreytech.stopwatch.utils.formatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private var job: Job? = null
    private var startTime = 0L
    private var accumulatedTime = 0L

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _laps = MutableStateFlow<List<String>>(emptyList())
    val laps: StateFlow<List<String>> = _laps

    private val _digitColor = MutableStateFlow(Color.White)
    val digitColor: StateFlow<Color> = _digitColor

    private val _backgroundColor = MutableStateFlow(Color(0xFF101010))
    val backgroundColor: StateFlow<Color> = _backgroundColor

    private val _buttonColor = MutableStateFlow(Color(0xFF00C853))
    val buttonColor: StateFlow<Color> = _buttonColor

    init {
        // Load saved state when ViewModel is created
        viewModelScope.launch {
            dataStoreManager.elapsedTimeFlow.collect { _elapsedTime.value = it }
        }

        viewModelScope.launch {
            dataStoreManager.lapsFlow.collect { _laps.value = it }
        }

        viewModelScope.launch {
            launch { dataStoreManager.digitColorFlow.collect { _digitColor.value = Color(it) } }
            launch { dataStoreManager.backgroundColorFlow.collect { _backgroundColor.value = Color(it) } }
            launch { dataStoreManager.buttonColorFlow.collect { _buttonColor.value = Color(it) } }
        }
    }


    fun saveColorSettings(digitColor: Color, backgroundColor: Color, buttonColor: Color) {
        viewModelScope.launch {
            dataStoreManager.saveColors(
                digitColor.toArgb(),
                backgroundColor.toArgb(),
                buttonColor.toArgb()
            )
        }
    }

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
        _isRunning.value = false
        job?.cancel() // stop any running timer
        _elapsedTime.value = 0L
        accumulatedTime = 0L // Reset accumulated time too
        _laps.value = emptyList()
        viewModelScope.launch {
            dataStoreManager.saveElapsedTime(0L)
            dataStoreManager.saveLaps(emptyList())
        }
    }

    fun recordLap() {
        val newLap = formatTime(_elapsedTime.value)
        _laps.value += newLap
        viewModelScope.launch {
            dataStoreManager.saveLaps(_laps.value)
        }
    }
    fun saveElapsedTime() {
        viewModelScope.launch {
            dataStoreManager.saveElapsedTime(_elapsedTime.value)
        }
    }
}
