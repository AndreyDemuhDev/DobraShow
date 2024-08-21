package com.example.dobrashow.screens.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dobrashow.ui.theme.AppTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
) {
    Text(text = "Favorite screens", color = AppTheme.colorScheme.text)
}