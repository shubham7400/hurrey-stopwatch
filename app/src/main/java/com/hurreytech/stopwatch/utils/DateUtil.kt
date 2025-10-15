package com.hurreytech.stopwatch.utils

fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    val milliseconds = (timeInMillis % 1000) / 10  // show 2-digit ms
    return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
}
