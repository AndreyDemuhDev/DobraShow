package com.example.dobrashow.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.dobrashow.R

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_package),
            contentDescription = null,
            colorFilter = ColorFilter.tint(com.example.uikit.AppTheme.colorScheme.primary),
            modifier = Modifier.size(com.example.uikit.AppTheme.size.dp24)
        )
    }
}
