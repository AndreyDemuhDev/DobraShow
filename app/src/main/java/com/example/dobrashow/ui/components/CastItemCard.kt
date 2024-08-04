package com.example.dobrashow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.network.models.domain.DomainCastEntity


@Composable
fun CastItemCard(
    cast: DomainCastEntity,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        modifier = modifier.size(width = 250.dp, height = 120.dp)
    ) {
        Row() {
            AsyncImage(
                model = cast.person.image.medium,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(0.8f)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
            ) {
                Text(text = cast.person.name)
                Text(text = "---------")
                Text(text = cast.character.name)
            }
        }
    }
}