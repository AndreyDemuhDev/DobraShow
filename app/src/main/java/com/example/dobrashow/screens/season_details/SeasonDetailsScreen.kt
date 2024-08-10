package com.example.dobrashow.screens.season_details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.dobrashow.screens.show_details.LoadingStateContent
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.network.models.domain.DomainSeasonEntity


@Composable
fun SeasonDetailsScreen(
    seasonId: Int,
    onClickBack: () -> Unit,
    seasonViewModel: SeasonViewModel = hiltViewModel(),
) {

    val stateSeason by seasonViewModel.seasonState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { seasonViewModel.getSeasonInfo(seasonId) })

    when (val state = stateSeason) {
        is SeasonUiState.Error -> {
            Text(text = "Error season details")
        }

        SeasonUiState.Loading -> {
            LoadingStateContent()
        }

        is SeasonUiState.Success -> {
            SuccessSeasonStateContent(season = state.season, onClickBack = onClickBack)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessSeasonStateContent(
    season: DomainSeasonEntity,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CustomTopBarComponent(title = "Season Details", onClickBack = onClickBack)
        LazyColumn {
            item { ImageSeasonSection(season = season) }
            item { DescriptionSeason(season = season, modifier = Modifier.fillMaxWidth()) }
            stickyHeader { SeasonHeader(season = season, modifier = Modifier.fillMaxWidth()) }
            items(season.listEpisodes.episodes) { episode ->

                ListEpisodesSeason(
                    episode = episode,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ImageSeasonSection(
    season: DomainSeasonEntity,
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
fun SeasonHeader(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Season ${season.number}",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
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
        Text(
            text = "Premiere date: ${season.premiereDate}",
            style = MaterialTheme.typography.titleMedium
        )
        if (season.summary.isNotEmpty()) {
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
    episode: DomainSeasonEntity.DomainEpisode,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, Color.Blue)),
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        Column(modifier = Modifier.padding(all = 6.dp)) {
            Row {
                Text(
                    text = "Episode: ${episode.number}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = episode.airdate, style = MaterialTheme.typography.titleMedium)
            }
            Text(text = episode.name, style = MaterialTheme.typography.headlineSmall)
            if (episode.summary.isNotEmpty()) {
                Row {
                    Text(text = "Show Description")
                    Image(
                        painter = if (!expanded) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                            id = R.drawable.ic_arrow_up
                        ),
                        contentDescription = "arrow_down",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { expanded = !expanded }
                    )
                }
                if (expanded) {
                    Text(
                        text = HtmlCompat.fromHtml(
                            episode.summary,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString(), maxLines = 1
                    )
                }
            }
        }
    }
}
