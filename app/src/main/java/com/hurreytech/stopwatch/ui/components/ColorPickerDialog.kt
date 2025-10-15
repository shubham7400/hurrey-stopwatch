package com.hurreytech.stopwatch.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerDialog(
    initialDigitColor: Color,
    initialBackgroundColor: Color,
    initialButtonColor: Color,
    onDismiss: () -> Unit,
    onSave: (Color, Color, Color) -> Unit
) {
    var digitColor by remember { mutableStateOf(initialDigitColor) }
    var backgroundColor by remember { mutableStateOf(initialBackgroundColor) }
    var buttonColor by remember { mutableStateOf(initialButtonColor) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Customize Appearance") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Text("Digit Color")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(Color.White, Color.Yellow, Color.Cyan, Color.Green, Color.Red).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color, shape = CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                ) { digitColor = color }
                                .border(
                                    width = if (digitColor == color) 3.dp else 1.dp,
                                    color = Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Text("Background Color")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(Color.Black, Color.DarkGray, Color.Blue, Color(0xFF222222), Color(0xFF101010)).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color, shape = CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                ) { backgroundColor = color }
                                .border(
                                    width = if (backgroundColor == color) 3.dp else 1.dp,
                                    color = Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Text("Button Color")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(Color(0xFF00C853), Color.Red, Color.Blue, Color.Magenta, Color.Yellow).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color, shape = CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                ) { buttonColor = color }
                                .border(
                                    width = if (buttonColor == color) 3.dp else 1.dp,
                                    color = Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(digitColor, backgroundColor, buttonColor)
                onDismiss()
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
