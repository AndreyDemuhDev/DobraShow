package com.example.dobrashow.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.dobrashow.R
import com.example.dobrashow.ui.theme.AppTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_package),
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colorScheme.primary),
            modifier = Modifier.size(AppTheme.size.dp24 * 8)
        )
    }
}