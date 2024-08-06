package com.example.dobrashow.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.network.models.domain.DomainSeasonEntity

@Composable
fun SeasonsItemCard(
    seasonItem: List<DomainSeasonEntity>,
    modifier: Modifier = Modifier
) {

    seasonItem.forEach { season ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Blue.copy(alpha = 0.5f)
                        )
                    ),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Box() {
                AsyncImage(
                    model = season.image.medium,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_no_image),
                    modifier = Modifier
                        .size(width = 200.dp, height = 250.dp)
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                )
            }
            Text(
                text = "Season ${season.number}",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}
