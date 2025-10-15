package com.hurreytech.stopwatch.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("stopwatch_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val ELAPSED_TIME_KEY = longPreferencesKey("elapsed_time")
    private val LAPS_KEY = stringSetPreferencesKey("laps")

    // Save elapsed time
    suspend fun saveElapsedTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[ELAPSED_TIME_KEY] = time
        }
    }

    // Read elapsed time
    val elapsedTimeFlow: Flow<Long> = context.dataStore.data
        .map { prefs -> prefs[ELAPSED_TIME_KEY] ?: 0L }

    suspend fun saveLaps(laps: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[LAPS_KEY] = laps.toSet() // ✅ must be Set<String>
        }
    }

    val lapsFlow: Flow<List<String>> = context.dataStore.data
        .map { prefs -> prefs[LAPS_KEY]?.toList() ?: emptyList() } // convert Set -> List
}




