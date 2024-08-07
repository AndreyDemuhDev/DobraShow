package com.example.dobrashow.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dobrashow.utils.toColor
import com.example.network.ShowStatus

@Composable
fun ShowStatusComponent(
    showStatus: ShowStatus,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 2.dp,
                color = showStatus.toColor(),
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = "Status: ${showStatus.statusName}",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
    }
}