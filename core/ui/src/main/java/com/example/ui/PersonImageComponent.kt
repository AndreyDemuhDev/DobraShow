package com.example.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.design.theme.AppTheme

@Composable
fun PersonImageComponent(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Character image",
        error = painterResource(id = R.drawable.ic_no_photo),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1f)
            .clip(AppTheme.shape.small)
            .border(
                width = AppTheme.size.dp1,
                brush = Brush.verticalGradient(
                    listOf(
                        AppTheme.colorScheme.transparent,
                        AppTheme.colorScheme.primary
                    )
                ),
                shape = MaterialTheme.shapes.small
            ),
    )
}