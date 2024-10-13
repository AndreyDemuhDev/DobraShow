package com.example.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.design.theme.AppTheme

@Composable
fun ShowStatusComponent(
    showStatus: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = AppTheme.size.dp2,
                color = showStatusComponent(statusName = showStatus).color,
                shape = MaterialTheme.shapes.medium
            )
            .padding(
                vertical = AppTheme.size.dp4,
                horizontal = AppTheme.size.dp8
            )
    ) {
        Text(
            text = "Status: ${showStatusComponent(statusName = showStatus).status}",
            style = AppTheme.typography.bodyNormal,
            color = AppTheme.colorScheme.text
        )
    }
}

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
