package com.example.dobrashow.utils

import androidx.compose.ui.graphics.Color
import com.example.network.ShowStatus

fun ShowStatus.toColor(): Color {
    return when (this) {
        ShowStatus.Determined -> Color.Blue
        ShowStatus.Ended -> Color.Red
        ShowStatus.Running -> Color.Green
        ShowStatus.Unknown -> Color.DarkGray
    }
}