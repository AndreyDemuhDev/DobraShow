package com.example.dobrashow.screens.show_details

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.dobrashow.ui.components.InfoBottomSheet
import com.example.dobrashow.ui.components.SeasonsItemCard
import com.example.dobrashow.ui.components.ShowStatusComponent
import com.example.dobrashow.ui.theme.AppTheme


@Composable
fun DetailShowScreen(
    showId: Int,
    onClickSeason: (Int) -> Unit,
    onClickPerson: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    showViewModel: DetailsShowViewModel = hiltViewModel(),
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

    DetailsShowStateContent(
        showState = state.showInformation,
        listPeoplesShow = state.showPeoplesList,
        listSeasons = state.showSeasonsList,
        onClickSeason = onClickSeason,
        onClickPerson = onClickPerson,
        onClickBack = onClickBack,
        modifier = modifier
    )
}

@Composable
fun DetailsShowStateContent(
    showState: ShowInformationUiState,
    listPeoplesShow: ShowPeoplesListUiState,
    listSeasons: ShowSeasonsListUiState,
    onClickSeason: (Int) -> Unit,
    onClickPerson: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (showState) {
        is ShowInformationUiState.Error -> {
            Text(text = "Error")
        }

        ShowInformationUiState.Loading -> {
            Text(text = "Loading")
        }

        is ShowInformationUiState.Success -> {
            SuccessShowInformationState(
                listPeoplesShow = listPeoplesShow,
                listSeasons = listSeasons,
                onClickPerson = onClickPerson,
                onClickSeason = onClickSeason,
                onClickBack = onClickBack,
                showState = showState,
//                hazeState = hazeState,
                modifier = modifier,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SuccessShowInformationState(
    showState: ShowInformationUiState.Success,
//    hazeState: HazeState,
    onClickBack: () -> Unit,
    listPeoplesShow: ShowPeoplesListUiState,
    listSeasons: ShowSeasonsListUiState,
    onClickPerson: (Int) -> Unit,
    onClickSeason: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Box(modifier = modifier.background(color = AppTheme.colorScheme.transparent)) {
        SubcomposeAsyncImage(
            model = showState.show.image.original, contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            loading = { LoadingStateContent() },
        )
        OutlinedIconButton(
            onClick = { onClickBack() },
            shape = CircleShape,

            border = BorderStroke(
                width = AppTheme.size.dp1,
                color = Color.White.copy(alpha = 0.4f),
            ),
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(40.dp),
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier
                .background(AppTheme.colorScheme.transparent)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f),
                                Color.Black.copy(alpha = 1f),
                            )
                        )
                    )
            ) {
                DescriptionShowSection(showState)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.size.dp8, vertical = AppTheme.size.dp4)
                ) {
                    FunctionalItemCard(
                        title = "Add",
                        icon = painterResource(id = R.drawable.ic_bookmark),
                        onClick = { /*TODO*/ })
                    FunctionalItemCard(
                        title = "Share",
                        icon = painterResource(id = R.drawable.ic_share),
                        onClick = { /*TODO*/ })
                    FunctionalItemCard(
                        title = "URL",
                        icon = painterResource(id = R.drawable.ic_world),
                        onClick = { /*TODO*/ })
                    FunctionalItemCard(
                        title = "Info",
                        icon = painterResource(id = R.drawable.ic_info),
                        onClick = { isSheetOpen = true })
                }
                if (isSheetOpen) {
                    InfoBottomSheet(
                        peoplesShow = listPeoplesShow,
                        seasonsShow = listSeasons,
                        onClickPerson = onClickPerson,
                        onClickSeason = onClickSeason,
                        sheetState = sheetState,
                        onDismiss = { isSheetOpen = false },
                    )
                }
            }
        }
    }
}


@Composable
private fun DescriptionShowSection(
    showState: ShowInformationUiState.Success,
    modifier: Modifier = Modifier,
) {
    var expandedDescription by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = AppTheme.size.dp8)
        ) {
            Text(
                text = showState.show.name,
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.text
            )
            Row {
                Text(
                    text = showState.show.genres.joinToString(separator = ", "),
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.text
                )
                Text(
                    text = " • " + showState.show.premiered.substring(
                        startIndex = 0,
                        endIndex = 4
                    ),
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.text
                )
                Text(
                    text = " • " + showState.show.network.country.code,
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
                    text = showState.show.rating.average.toString(),
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.text,
                    modifier = Modifier.padding(start = AppTheme.size.dp2)
                )
                ShowStatusComponent(
                    showStatus = showState.show.status,
                    modifier = Modifier.padding(start = AppTheme.size.dp12)
                )
            }
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
            )
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
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .width(90.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(AppTheme.colorScheme.primary.copy(alpha = 0.1f))
            .border(
                width = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(15.dp)
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
fun ShowPeoplesState(
    peoplesShow: ShowPeoplesListUiState,
    onClickPerson: (Int) -> Unit,
    modifier: Modifier = Modifier
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
                modifier = modifier.padding(
                    horizontal = AppTheme.size.dp8,
                    vertical = AppTheme.size.dp4
                )
            )
        }
    }
}

@Composable
fun ShowSeasonsState(
    seasonsShow: ShowSeasonsListUiState,
    onClickSeason: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (seasonsShow) {
        is ShowSeasonsListUiState.Error -> {
            Text(text = "Error seasons list")
        }

        ShowSeasonsListUiState.Loading -> {
//            LoadingState()
        }

        is ShowSeasonsListUiState.Success -> {
            Column {
                Text(
                    text = "Seasons",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colorScheme.text,
                    modifier = Modifier.padding(start = AppTheme.size.dp16)
                )
            }
            LazyRow(modifier = modifier.padding(horizontal = AppTheme.size.dp12)) {
                items(seasonsShow.listSeasons) { seasonItem ->
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
