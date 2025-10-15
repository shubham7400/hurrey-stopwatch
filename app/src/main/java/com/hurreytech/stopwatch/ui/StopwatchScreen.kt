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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hurreytech.stopwatch.ui.components.LapList
import com.hurreytech.stopwatch.ui.components.StopwatchButton
import com.hurreytech.stopwatch.utils.formatTime
import com.hurreytech.stopwatch.viewmodel.StopwatchViewModel

//@Composable
//fun StopwatchScreen(
//    viewModel: StopwatchViewModel = hiltViewModel()
//) {
//    val elapsedTime by viewModel.elapsedTime.collectAsState()
//    val isRunning by viewModel.isRunning.collectAsState()
//    val laps by viewModel.laps.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF101010))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = formatTime(elapsedTime),
//            fontSize = 48.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
//        )
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            StopwatchButton(
//                text = if (isRunning) "Stop" else "Start",
//                color = if (isRunning) Color.Red else Color.Green
//            ) {
//                if (isRunning) viewModel.stop() else viewModel.start()
//            }
//
//            StopwatchButton(text = "Lap", color = Color.Cyan) {
//                viewModel.recordLap()
//            }
//
//            StopwatchButton(text = "Reset", color = Color.Yellow) {
//                viewModel.reset()
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        LapList(laps = laps)
//    }
//}



@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val laps by viewModel.laps.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        color = Color(0xFF101010)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Stopwatch display
            Text(
                text = formatTime(elapsedTime),
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (isRunning) viewModel.stop() else viewModel.start()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) Color.Red else Color(0xFF00C853)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (isRunning) "Stop" else "Start")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { viewModel.reset() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reset")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { if (isRunning) viewModel.recordLap() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Lap")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Laps list
            Text(
                text = "Laps",
                color = Color.LightGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            LapList(laps = laps, textColor = Color.White)
        }
    }
}
