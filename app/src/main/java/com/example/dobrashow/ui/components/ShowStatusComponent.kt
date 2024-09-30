package com.example.dobrashow.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.design.theme.AppTheme
import com.example.dobrashow.utils.showStatusComponent

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
