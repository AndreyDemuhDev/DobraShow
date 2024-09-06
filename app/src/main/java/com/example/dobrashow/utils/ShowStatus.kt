package com.example.dobrashow.utils

import androidx.compose.ui.graphics.Color

fun showStatusComponent(statusName: String): ShowStatus {
    return when (statusName) {
        "Running" -> {
            ShowStatus(status = "Running", color = Color.Blue)
        }

        "Ended" -> {
            ShowStatus(status = "Ended", color = Color.Red)
        }

        "To Be Determined" -> {
            ShowStatus(status = "To Be Determined", color = Color.Green)
        }

        else -> {
            ShowStatus(status = "Unknown", color = Color.DarkGray)
        }
    }
}

data class ShowStatus(
    val status: String,
    val color: Color
)
