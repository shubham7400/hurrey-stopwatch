package com.hurreytech.stopwatch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.hurreytech.stopwatch.ui.components.ColorPickerDialog
import com.hurreytech.stopwatch.ui.components.LapList
import com.hurreytech.stopwatch.utils.formatTime
import com.hurreytech.stopwatch.viewmodel.StopwatchViewModel


@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val laps by viewModel.laps.collectAsState()
    val digitColor by viewModel.digitColor.collectAsState()
    val backgroundColor by viewModel.backgroundColor.collectAsState()
    val buttonColor by viewModel.buttonColor.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ⚙ Customize button at top
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showDialog = true }) {
                    Text("⚙ Customize", color = Color.LightGray)
                }
            }

            // Stopwatch display
            Text(
                text = formatTime(elapsedTime),
                color = digitColor,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

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
                        containerColor = if (isRunning) Color.Red else buttonColor
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (isRunning) "Stop" else "Start")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { viewModel.reset() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reset")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { if (isRunning) viewModel.recordLap() },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Lap")
                }
            }

            // Laps
            Text(
                text = "Laps",
                color = Color.LightGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            LapList(laps = laps, textColor = digitColor)
        }
    }

    if (showDialog) {
        ColorPickerDialog(
            initialDigitColor = digitColor,
            initialBackgroundColor = backgroundColor,
            initialButtonColor = buttonColor,
            onDismiss = { showDialog = false },
            onSave = { d, b, btn -> viewModel.saveColorSettings(d, b, btn) }
        )
    }
}
