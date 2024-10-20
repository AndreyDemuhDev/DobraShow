package com.example.view_show.screens.details_season

import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.domain.model.EpisodeUI
import com.example.domain.model.SeasonsShowUi
import com.example.ui.CustomTopBarComponent
import com.example.ui.ImageSeasonSection
import com.example.ui.dateConverter
import com.example.view_show.R

@Composable
fun DetailSeasonMainScreen(
    idSeason: Int,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    SeasonDetailScreen(
        idSeason = idSeason,
        onClickBack = onClickBack,
        modifier = modifier
    )
}


@Composable
internal fun SeasonDetailScreen(
    idSeason: Int,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailSeasonViewModel = hiltViewModel()
) {
    val stateSeason by viewModel.seasonState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getSeasonInfo(seasonId = idSeason)
    })

    if (stateSeason != StateSeason.None) {
        SeasonShowStateContent(
            seasonState = stateSeason,
            onClickBack = onClickBack,
            modifier = modifier
        )
    }

}

@Composable
fun SeasonShowStateContent(
    seasonState: StateSeason,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (seasonState is StateSeason.Error) {
        DetailSeasonErrorContent(stateSeason = seasonState, modifier = modifier)
    }
    if (seasonState is StateSeason.Loading) {
        DetailSeasonLoadingContent(
            modifier = modifier
        )
    }
    if (seasonState.season != null
    ) {
        DetailSeasonSuccessContent(
            season = seasonState.season,
            onClickBack = onClickBack,
            modifier = modifier
        )
    }
}


@Composable
private fun DetailSeasonErrorContent(
    stateSeason: StateSeason.Error,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "SeasonErrorContent", color = AppTheme.colorScheme.error)
        }
    }
}


@Composable
fun DetailSeasonLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(AppTheme.size.dp24)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailSeasonSuccessContent(
    season: SeasonsShowUi,
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
                    modifier = Modifier.padding(horizontal = AppTheme.size.dp16)
                )
            }
        }
    }
}


@Composable
fun SeasonHeader(
    season: SeasonsShowUi,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Season ${season.number}",
        style = AppTheme.typography.titleNormal,
        color = AppTheme.colorScheme.text,
        textAlign = TextAlign.Center,
        modifier = modifier.background(AppTheme.colorScheme.background)
    )
}

@Composable
fun DescriptionSeason(
    season: SeasonsShowUi,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            horizontal = AppTheme.size.dp16,
            vertical = AppTheme.size.dp4
        )
    ) {
        Text(
            text = "Premiere date: ${dateConverter(season.premiereDate)}",
            color = AppTheme.colorScheme.text,
            style = AppTheme.typography.bodyLarge,
        )
        if (season.summary.isNotEmpty()) {
            Text(
                text = Html.fromHtml(season.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    .trim(),
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.text,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
@Suppress("LongMethod")
fun ListEpisodesSeason(
    episode: EpisodeUI,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(bottom = AppTheme.size.dp4)
            .border(
                width = AppTheme.size.dp2,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppTheme.colorScheme.transparent,
                        AppTheme.colorScheme.primary
                    )
                ),
                shape = AppTheme.shape.medium
            )
            .clip(AppTheme.shape.medium)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        Column(modifier = Modifier.padding(all = AppTheme.size.dp8)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Episode: ${episode.number}",
                    color = AppTheme.colorScheme.text,
                    style = AppTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = episode.airdate,
                    style = AppTheme.typography.titleNormal,
                    color = AppTheme.colorScheme.text,
                )
            }
            Text(
                text = episode.name,
                style = AppTheme.typography.titleSmall,
                color = AppTheme.colorScheme.text,
            )
            if (episode.summary.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Show Description",
                        style = AppTheme.typography.labelNormal,
                        color = AppTheme.colorScheme.text,
                    )
                    Image(
                        painter = if (!expanded) painterResource(id = R.drawable.ic_arrow_down) else painterResource(
                            id = R.drawable.ic_arrow_up
                        ),
                        contentDescription = "arrow_down",
                        colorFilter = ColorFilter.tint(AppTheme.colorScheme.text),
                        modifier = Modifier
                            .size(AppTheme.size.dp24)
                            .clickable { expanded = !expanded }
                    )
                }
                if (expanded) {
                    Text(
                        text = HtmlCompat.fromHtml(
                            episode.summary,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString().trim(),
                        color = AppTheme.colorScheme.text,
                        style = AppTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}
