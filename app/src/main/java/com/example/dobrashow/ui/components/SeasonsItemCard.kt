package com.example.dobrashow.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.models.domain.DomainSeasonEntity

@Composable
fun SeasonsItemCard(
    seasonItem: DomainSeasonEntity,
    onClickSeason: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(
                width = AppTheme.size.dp1,
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Blue.copy(alpha = 0.5f)
                    )
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClickSeason(seasonItem.id) }
    ) {
        Box() {
            AsyncImage(
                model = seasonItem.image.medium,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_no_image),
                modifier = Modifier
                    .size(width = 200.dp, height = 250.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = AppTheme.size.dp16,
                            topEnd = AppTheme.size.dp16,
                        )
                    )
            )
        }
        Text(
            text = "Season ${seasonItem.number}",
            style = AppTheme.typography.titleLarge,
        )
    }
}
