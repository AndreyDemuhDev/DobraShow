package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.design.theme.AppTheme


@Composable
fun FunctionalDetailShowCard(
    title: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .width(90.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(AppTheme.colorScheme.primary.copy(alpha = 0.1f))
            .border(
                width = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(all = AppTheme.size.dp8)
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = AppTheme.colorScheme.text,
                modifier = Modifier.size(AppTheme.size.dp24 * 2)
            )
            Spacer(modifier = Modifier.height(AppTheme.size.dp4))
            Text(
                text = title,
                style = AppTheme.typography.bodyNormal,
                color = AppTheme.colorScheme.text
            )
        }
    }
}