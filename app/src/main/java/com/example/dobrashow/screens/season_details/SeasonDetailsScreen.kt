package com.example.dobrashow.screens.season_details

import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.screens.show_details.LoadingStateContent
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.uikit.AppTheme
import com.example.dobrashow.utils.dateConverter
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
                    modifier = Modifier.padding(horizontal = com.example.uikit.AppTheme.size.dp16)
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
        model = season.image.original,
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
        style = com.example.uikit.AppTheme.typography.titleNormal,
        color = com.example.uikit.AppTheme.colorScheme.text,
        textAlign = TextAlign.Center,
        modifier = modifier.background(com.example.uikit.AppTheme.colorScheme.background)
    )
}

@Composable
fun DescriptionSeason(
    season: DomainSeasonEntity,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = com.example.uikit.AppTheme.size.dp16, vertical = com.example.uikit.AppTheme.size.dp4)
    ) {
        Text(
            text = "Premiere date: ${dateConverter(season.premiereDate)}",
            color = com.example.uikit.AppTheme.colorScheme.text,
            style = com.example.uikit.AppTheme.typography.bodyLarge,
        )
        if (season.summary.isNotEmpty()) {
            Text(
                text = Html.fromHtml(season.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    .trim(),
                style = com.example.uikit.AppTheme.typography.bodySmall,
                color = com.example.uikit.AppTheme.colorScheme.text,
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
            .padding(bottom = com.example.uikit.AppTheme.size.dp4)
            .border(
                width = com.example.uikit.AppTheme.size.dp2,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        com.example.uikit.AppTheme.colorScheme.transparent,
                        com.example.uikit.AppTheme.colorScheme.primary
                    )
                ),
                shape = com.example.uikit.AppTheme.shape.medium
            )
            .clip(com.example.uikit.AppTheme.shape.medium)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        Column(modifier = Modifier.padding(all = com.example.uikit.AppTheme.size.dp8)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Episode: ${episode.number}",
                    color = com.example.uikit.AppTheme.colorScheme.text,
                    style = com.example.uikit.AppTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = episode.airdate,
                    style = com.example.uikit.AppTheme.typography.titleNormal,
                    color = com.example.uikit.AppTheme.colorScheme.text,
                )
            }
            Text(
                text = episode.name,
                style = com.example.uikit.AppTheme.typography.titleSmall,
                color = com.example.uikit.AppTheme.colorScheme.text,
            )
            if (episode.summary.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Show Description",
                        style = com.example.uikit.AppTheme.typography.labelNormal,
                        color = com.example.uikit.AppTheme.colorScheme.text,
                    )
                    Image(
                        painter = if (!expanded) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                            id = R.drawable.ic_arrow_up
                        ),
                        contentDescription = "arrow_down",
                        colorFilter = ColorFilter.tint(com.example.uikit.AppTheme.colorScheme.text),
                        modifier = Modifier
                            .size(com.example.uikit.AppTheme.size.dp24)
                            .clickable { expanded = !expanded }
                    )
                }
                if (expanded) {
                    Text(
                        text = HtmlCompat.fromHtml(
                            episode.summary,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString().trim(),
                        color = com.example.uikit.AppTheme.colorScheme.text,
                        style = com.example.uikit.AppTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}
