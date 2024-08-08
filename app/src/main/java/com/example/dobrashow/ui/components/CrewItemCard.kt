package com.example.dobrashow.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.network.models.domain.DomainCrewEntity


@Composable
fun CrewItemCard(
    crew: DomainCrewEntity,
    onClickPerson: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(width = 160.dp, height = 210.dp)
            .clickable { onClickPerson() }
    ) {
        AsyncImage(
            model = crew.person.image.medium,
            contentDescription = "",
            error = painterResource(id = R.drawable.ic_no_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(130.dp)
                .clip(CircleShape)
        )
        Text(
            text = crew.person.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = crew.type,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}