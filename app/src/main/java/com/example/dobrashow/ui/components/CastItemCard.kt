package com.example.dobrashow.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.network.models.domain.DomainCastEntity

@Composable
fun CastItemCard(
    cast: DomainCastEntity,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.size(width = 160.dp, height = 210.dp)
    ) {
        AsyncImage(
            model = cast.person.image.medium,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(130.dp)
                .clip(CircleShape)
        )
        Text(
            text = cast.person.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "as " + "'" + cast.character.name + "'",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}