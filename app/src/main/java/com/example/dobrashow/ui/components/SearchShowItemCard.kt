package com.example.dobrashow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.models.domain.DomainSearchShowEntity


@Composable
fun SearchShowItemCard(
    show: DomainSearchShowEntity?,
    onClickShow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.background),
        border = BorderStroke(width = AppTheme.size.dp2, color = AppTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(AppTheme.size.dp4),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickShow() }
    ) {
        Row() {
            AsyncImage(
                model = show?.searchShows?.image?.medium ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_no_image),
                modifier = Modifier.size(width = 120.dp, height = 180.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
                modifier = Modifier.padding(all = AppTheme.size.dp8)
            ) {
                Text(
                    text = show?.searchShows?.name ?: "",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colorScheme.text,
                    maxLines = 2
                )
                Text(
                    text = show?.searchShows?.premiered ?: "",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colorScheme.text
                )
            }
        }
    }
}