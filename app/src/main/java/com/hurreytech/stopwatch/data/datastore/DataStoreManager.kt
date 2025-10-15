package com.hurreytech.stopwatch.data.datastore

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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

    private val DIGIT_COLOR_KEY = intPreferencesKey("digit_color")
    private val BACKGROUND_COLOR_KEY = intPreferencesKey("background_color")
    private val BUTTON_COLOR_KEY = intPreferencesKey("button_color")

    val digitColorFlow: Flow<Int> = context.dataStore.data
        .map { it[DIGIT_COLOR_KEY] ?: Color.White.toArgb() }

    val backgroundColorFlow: Flow<Int> = context.dataStore.data
        .map { it[BACKGROUND_COLOR_KEY] ?: Color(0xFF101010).toArgb() }

    val buttonColorFlow: Flow<Int> = context.dataStore.data
        .map { it[BUTTON_COLOR_KEY] ?: Color(0xFF00C853).toArgb() }

    suspend fun saveColors(digitColor: Int, backgroundColor: Int, buttonColor: Int) {
        context.dataStore.edit { prefs ->
            prefs[DIGIT_COLOR_KEY] = digitColor
            prefs[BACKGROUND_COLOR_KEY] = backgroundColor
            prefs[BUTTON_COLOR_KEY] = buttonColor
        }
    }


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




