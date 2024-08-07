package com.example.dobrashow.screens.season_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.screens.show_details.LoadingState
import com.example.network.models.domain.DomainSeasonEntity


@Composable
fun SeasonDetailsScreen(
    seasonId: Int,
    modifier: Modifier = Modifier,
    seasonViewModel: SeasonViewModel = hiltViewModel(),
) {

    val stateSeason by seasonViewModel.seasonState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { seasonViewModel.getSeasonInfo(seasonId) })

    when (val state = stateSeason) {
        is SeasonUiState.Error -> {
            Text(text = "Error season details")
        }

        SeasonUiState.Loading -> {
            LoadingState()
        }

        is SeasonUiState.Success -> {
            SeasonContent(season = state.season)
        }
    }
}

@Composable
fun SeasonContent(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        item { ImageSeasonSection(season = season) }
        item { DescriptionSeason(season = season, modifier = Modifier.fillMaxWidth()) }
        item {
            ListEpisodesSeason(
                season = season,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }
}

@Composable
fun ImageSeasonSection(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = season.image.medium,
        contentDescription = null,
        error = painterResource(id = R.drawable.ic_no_image),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}

@Composable
fun DescriptionSeason(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "Season ${season.number}", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Premiere date: ${season.premiereDate}",
            style = MaterialTheme.typography.titleMedium
        )
        if (season.summary.isNotEmpty()){
            Text(
                text = season.summary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Justify
            )
        }

    }
}

@Composable
fun ListEpisodesSeason(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier,
) {
    season.listEpisodes.episodes.forEach { seasonItem ->
        Row(
            modifier = modifier
                .padding(vertical = 4.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(Color.Transparent, Color.Blue)),
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
        ) {
            Box {
                AsyncImage(
                    model = seasonItem.image?.medium,
                    contentDescription = null,
                    error = painterResource(
                        id = R.drawable.ic_no_image
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(width = 150.dp, height = 200.dp)
                )
                if (seasonItem.rating.average != 0.0)
                    Row(modifier = Modifier.padding(4.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rating_star),
                            contentDescription = null
                        )
                        Text(text = seasonItem.rating.average.toString())
                    }
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = "Name ${seasonItem.name}")
                Text(text = "Description")
                Text(
                    text = HtmlCompat.fromHtml(
                        seasonItem.summary,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString(), maxLines = 1
                )
            }
        }
    }
}