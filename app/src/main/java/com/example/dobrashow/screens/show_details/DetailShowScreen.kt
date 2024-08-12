package com.example.dobrashow.screens.show_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.components.CastAndCrewShowPager
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.components.SeasonsItemCard
import com.example.dobrashow.ui.components.ShowStatusComponent
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.models.domain.DomainShowEntity

@Composable
fun DetailShowScreen(
    showId: Int,
    onClickSeason: (Int) -> Unit,
    onClickPerson: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    showViewModel: ShowViewModel = hiltViewModel(),
) {
    val state by showViewModel.showUiState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        showViewModel.getShowInformation(showId)
        showViewModel.getPeoplesShowInformation(showId)
        showViewModel.getListSeasonsShow(showId)
    })

    if (state.isError) {
        //TODO
    }

    SuccessDetailsShowStateContent(
        showState = state.showInformation,
        listPeoplesShow = state.showPeoplesList,
        listSeasons = state.showSeasonsList,
        onClickSeason = onClickSeason,
        onClickPerson = onClickPerson,
        onClickBack = onClickBack
    )
}

@Composable
fun SuccessDetailsShowStateContent(
    showState: ShowInformationUiState,
    listPeoplesShow: ShowPeoplesListUiState,
    listSeasons: ShowSeasonsListUiState,
    onClickSeason: (Int) -> Unit,
    onClickPerson: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CustomTopBarComponent(title = "Details Show", onClickBack = onClickBack)
        LazyColumn {
            item { ShowInformationState(showInfoUiState = showState) }
            item { ShowPeoplesState(peoplesShow = listPeoplesShow, onClickPerson = onClickPerson) }
            item {
                Text(
                    text = "Seasons",
                    style = AppTheme.typography.titleNormal,
                    modifier = Modifier.padding(
                        start = AppTheme.size.dp16,
                        top = AppTheme.size.dp4,
                        bottom = AppTheme.size.dp4
                    )
                )
            }
            item {
                ShowSeasonsState(
                    stateSeasons = listSeasons,
                    onClickSeason = onClickSeason,
                    modifier = Modifier.padding(horizontal = AppTheme.size.dp16)
                )
            }
        }
    }
}

@Composable
private fun ShowInformationState(
    showInfoUiState: ShowInformationUiState,
    modifier: Modifier = Modifier
) {
    when (showInfoUiState) {
        is ShowInformationUiState.Success -> {
            Column(modifier = modifier) {

                var expandedDescription by remember {
                    mutableStateOf(false)
                }

                ImageShowSection(showInfoUiState.show)
                Text(
                    text = "Story Line",
                    style = AppTheme.typography.titleNormal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = AppTheme.size.dp16,
                        top = AppTheme.size.dp10
                    )
                )
                Row {
                    Text(
                        text = HtmlCompat.fromHtml(
                            showInfoUiState.show.summary,
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        ).toString().trim(),
                        style = AppTheme.typography.bodySmall,
                        textAlign = TextAlign.Justify,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (!expandedDescription) 5 else Int.MAX_VALUE,
                        modifier = Modifier
                            .padding(horizontal = AppTheme.size.dp16)
                            .clickable { expandedDescription = !expandedDescription }
                    )
                }

            }
        }

        is ShowInformationUiState.Error -> {
            Text(text = "Ошибка загрузки")
        }

        ShowInformationUiState.Loading -> {
            Text(text = "Loading show information")
        }
    }
}

@Composable
fun ShowPeoplesState(
    peoplesShow: ShowPeoplesListUiState,
    onClickPerson: (Int) -> Unit,
//    modifier: Modifier = Modifier
) {
    when (peoplesShow) {
        is ShowPeoplesListUiState.Error -> {
            Text(text = "Error peoples show information")
        }

        ShowPeoplesListUiState.Loading -> {
            LoadingStateContent()
        }

        is ShowPeoplesListUiState.Success -> {
            CastAndCrewShowPager(
                cast = peoplesShow.castList,
                crew = peoplesShow.crewList,
                onClickPerson = onClickPerson,
                modifier = Modifier.padding(
                    horizontal = AppTheme.size.dp8,
                    vertical = AppTheme.size.dp4
                )
            )
        }
    }
}

@Composable
fun ShowSeasonsState(
    stateSeasons: ShowSeasonsListUiState,
    onClickSeason: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (stateSeasons) {
        is ShowSeasonsListUiState.Error -> {
            Text(text = "Error seasons list")
        }

        ShowSeasonsListUiState.Loading -> {
//            LoadingState()
        }

        is ShowSeasonsListUiState.Success -> {
            LazyRow(modifier = Modifier.padding(horizontal = AppTheme.size.dp12)) {
                items(stateSeasons.listSeasons) { seasonItem ->
                    SeasonsItemCard(
                        seasonItem = seasonItem,
                        onClickSeason = onClickSeason,
                        modifier = Modifier.padding(
                            horizontal = AppTheme.size.dp4,
                            vertical = AppTheme.size.dp2
                        )
                    )
                }
            }
        }
    }
}


@Composable
private fun ImageShowSection(
    show: DomainShowEntity,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        SubcomposeAsyncImage(
            model = show.image.medium, contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .heightIn(max = 400.dp),
            contentScale = ContentScale.FillBounds,
            loading = { LoadingStateContent() }
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .heightIn(min = 400.dp)
                .padding(horizontal = AppTheme.size.dp16)
        ) {
            HeaderInformationShowSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.size.dp24 * 2)
            )
            InformationShowItem(show = show, modifier = Modifier.align(Alignment.Start))
        }
    }
}

@Composable
private fun HeaderInformationShowSection(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_favorite_heart),
            contentDescription = "favorite",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(AppTheme.size.dp24 * 1.5f)
        )
    }
}

@Composable
fun InformationShowItem(
    show: DomainShowEntity,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = show.name,
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colorScheme.text
        )
        Row {
            Text(
                text = show.genres.joinToString(separator = ", "),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.text
            )
            Text(
                text = " • " + show.premiered.substring(startIndex = 0, endIndex = 4),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.text
            )
            Text(
                text = " • " + show.network.country.code,
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.text
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_rating_star),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Yellow),
                modifier = Modifier.size(AppTheme.size.dp24)
            )
            Text(
                text = show.rating.average.toString(),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.text,
                modifier = Modifier.padding(start = AppTheme.size.dp2)
            )
            ShowStatusComponent(
                showStatus = show.status,
                modifier = Modifier.padding(start = AppTheme.size.dp12)
            )
        }
    }
}

@Composable
fun LoadingStateContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(AppTheme.size.dp24)
        )
    }
}