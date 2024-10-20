package com.example.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.domain.model.SeasonsShowUi


@Composable
fun ImageSeasonSection(
    season: SeasonsShowUi,
) {
    AsyncImage(
        model = season.image.original,
        contentDescription = null,
        error = painterResource(id = R.drawable.ic_no_image),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}