package com.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.design.theme.AppTheme


@Composable
fun DescriptionPersonComponent(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(vertical = AppTheme.size.dp2)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.titleSmall,
            color = AppTheme.colorScheme.text
        )
        Text(
            text = description,
            style = AppTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colorScheme.text
        )
    }
}