package com.example.dobrashow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.models.domain.DomainShowEntity

@Composable
fun ShowItemCard(
    show: DomainShowEntity,
    onClickShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(width = 2.dp, color = AppTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .size(width = 250.dp, height = 270.dp)
            .clickable { onClickShow() }
    ) {
        AsyncImage(
            model = show.image.medium,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_no_image),
            modifier = Modifier.fillMaxSize()
        )
    }
}