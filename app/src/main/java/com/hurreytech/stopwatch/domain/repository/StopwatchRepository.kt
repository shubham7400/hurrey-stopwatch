package com.hurreytech.stopwatch.domain.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchRepository @Inject constructor() {
    fun saveLap(lapTime: String) {
        // Placeholder: save lap to DB or list
    }

    fun getLaps(): List<String> {
        // Placeholder: return lap list
        return listOf("00:10:45", "00:20:11")
    }
}
