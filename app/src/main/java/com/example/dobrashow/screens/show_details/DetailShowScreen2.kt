package com.example.dobrashow.screens.show_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.components.CastAndCrewShowPager
import com.example.dobrashow.ui.components.SeasonsItemCard
import com.example.dobrashow.ui.components.ShowStatusComponent
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.models.domain.DomainShowEntity
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@Composable
fun DetailShowScreen2(
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

    SuccessDetailsShowStateContent2(
        showState = state.showInformation,
        listPeoplesShow = state.showPeoplesList,
        listSeasons = state.showSeasonsList,
        onClickSeason = onClickSeason,
        onClickPerson = onClickPerson,
        onClickBack = onClickBack
    )
}

@Composable
fun SuccessDetailsShowStateContent2(
    showState: ShowInformationUiState,
    listPeoplesShow: ShowPeoplesListUiState,
    listSeasons: ShowSeasonsListUiState,
    onClickSeason: (Int) -> Unit,
    onClickPerson: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hazeState = remember { HazeState() }
    var expandedDescription by remember {
        mutableStateOf(false)
    }
    when (showState) {
        is ShowInformationUiState.Error -> {
            Text(text = "Error")
        }

        ShowInformationUiState.Loading -> {
            Text(text = "Loading")
        }

        is ShowInformationUiState.Success -> {
            Box(modifier = modifier) {
                SubcomposeAsyncImage(
                    model = showState.show.image.original, contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    loading = { LoadingStateContent2() },

                    )
                Column(
                    modifier = Modifier
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(all = AppTheme.size.dp16)
                            .clip(CircleShape)
                            .hazeChild(
                                state = hazeState,
                                style = HazeStyle(
                                    backgroundColor = MaterialTheme.colorScheme.background,
                                    tint = Color.Black.copy(alpha = .3f),
                                    blurRadius = 30.dp,
                                )
                            )
                            .border(
                                width = 2.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = .5f),
                                        Color.White.copy(alpha = .2f),
                                    ),
                                ),
                                shape = CircleShape
                            )
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier.size(AppTheme.size.dp24 * 1.5f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    startY = 0.8f,
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = .2f),
                                        Color.Black.copy(alpha = .4f),
                                        Color.Black.copy(alpha = .6f),
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    startY = 0.8f,
                                    colors = listOf(
                                        Color.Black.copy(alpha = .6f),
                                        Color.Black.copy(alpha = .8f),
                                        Color.Black,
                                    )
                                )
                            )
                    ) {
                        InformationShowItem2(
                            show = showState.show,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = AppTheme.size.dp16)
                        )
                        Text(
                            text = HtmlCompat.fromHtml(
                                showState.show.summary,
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            ).toString().trim(),
                            style = AppTheme.typography.bodySmall,
                            color = AppTheme.colorScheme.text,
                            textAlign = TextAlign.Justify,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = if (!expandedDescription) 5 else Int.MAX_VALUE,
                            modifier = Modifier
                                .clickable {
                                    expandedDescription = !expandedDescription
                                }
                                .padding(horizontal = AppTheme.size.dp16)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = AppTheme.size.dp4,
                                    horizontal = AppTheme.size.dp16
                                )
                        ) {
                            FunctionalItemCard(
                                title = "Add",
                                icon = painterResource(id = R.drawable.ic_favorite_heart),
                                onClick = { /*TODO*/ })
                            FunctionalItemCard(
                                title = "Share",
                                icon = painterResource(id = R.drawable.ic_no_image),
                                onClick = { /*TODO*/ })
                            FunctionalItemCard(
                                title = "URL",
                                icon = painterResource(id = R.drawable.ic_favorite_heart),
                                onClick = { /*TODO*/ })
                            FunctionalItemCard(
                                title = "Info",
                                icon = painterResource(id = R.drawable.ic_favorite_heart),
                                onClick = { /*TODO*/ })
                        }
                    }

                }

            }

        }
    }
}

@Composable
fun FunctionalItemCard(
    title: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hazeState = remember { HazeState() }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(90.dp)
            .clip(RoundedCornerShape(10.dp))
            .hazeChild(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = .3f),
                    blurRadius = 30.dp,
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = .5f),
                        Color.White.copy(alpha = .2f),
                    ),
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(all = AppTheme.size.dp8)
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = AppTheme.colorScheme.text,
                modifier = Modifier.size(AppTheme.size.dp24 * 2)
            )
            Spacer(modifier = Modifier.height(AppTheme.size.dp4))
            Text(
                text = title,
                style = AppTheme.typography.bodyNormal,
                color = AppTheme.colorScheme.text
            )
        }
    }
}


@Composable
fun ShowPeoplesState2(
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
fun ShowSeasonsState2(
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
fun InformationShowItem2(
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
fun LoadingStateContent2(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(AppTheme.size.dp24)
        )
    }
}
