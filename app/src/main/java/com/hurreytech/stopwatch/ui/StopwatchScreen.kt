package com.hurreytech.stopwatch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hurreytech.stopwatch.ui.components.StopwatchButton
import com.hurreytech.stopwatch.viewmodel.StopwatchViewModel

@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val laps by viewModel.laps.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(elapsedTime),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            StopwatchButton(
                text = if (isRunning) "Stop" else "Start",
                color = if (isRunning) Color.Red else Color.Green
            ) {
                if (isRunning) viewModel.stop() else viewModel.start()
            }

            StopwatchButton(text = "Lap", color = Color.Cyan) {
                viewModel.recordLap()
            }

            StopwatchButton(text = "Reset", color = Color.Yellow) {
                viewModel.reset()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(laps.reversed()) { lap ->
                Text(
                    text = "Lap: ${formatTime(lap)}",
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

private fun formatTime(timeMillis: Long): String {
    val totalSeconds = timeMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val millis = (timeMillis % 1000) / 10
    return "%02d:%02d:%02d".format(minutes, seconds, millis)
}
