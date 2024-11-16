package com.example.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.design.theme.AppTheme


@Composable
fun FilterShowComponent(
    statusFilter: String,
    onStatusClickable: (String) -> Unit,
    contentColor: Color,
    countCategory: Int
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = contentColor,
                shape = AppTheme.shape.small
            )
            .clickable {
                onStatusClickable(statusFilter)
            }
            .clip(AppTheme.shape.medium)
            .padding(all = AppTheme.size.dp2),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = countCategory.toString(),
            color = contentColor,
            modifier = Modifier.padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.bodyLarge
        )
        Text(
            text = statusFilter,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 6.dp),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.bodySmall
        )
    }
}