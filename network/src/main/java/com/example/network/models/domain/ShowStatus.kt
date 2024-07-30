package com.example.network.models.domain

import androidx.compose.ui.graphics.Color


sealed class ShowStatus(val statusName: String, val colorStatus: Color) {
    object Running: ShowStatus("Running", Color.Green)
    object Ended: ShowStatus("Ended", Color.Red)
    object Determined: ShowStatus("To Be Determined", Color.Blue)
    object Unknown: ShowStatus("Unknown", Color.DarkGray)
}