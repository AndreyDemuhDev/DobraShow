package com.example.view_show.screens.details_show

import android.util.Log
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
import com.example.design.theme.AppTheme
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.ShowsUi
import com.example.ui.InfoBottomSheet
import com.example.ui.ShowStatusComponent
import com.example.view_show.R

//
@Composable
fun ShowDetailsMainScreen(modifier: Modifier = Modifier) {
    ShowDetailsScreen(modifier = modifier)
}


@Composable
internal fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsShowViewModel = hiltViewModel(),
) {
    Log.d("MyLog", "ShowsScreen")
    val stateShowInformation by viewModel.showDetailState.collectAsState()
    val stateShowCast by viewModel.showCastState.collectAsState()
    val stateShowCrew by viewModel.showCrewState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getShowDetails(3)
        viewModel.getShowCast(23)
        viewModel.getShowCrew(13)
    })

    if (stateShowInformation != StateShow.None && stateShowCast != StateCast.None && stateShowCrew != StateCrew.None) {
        DetailsShowStateContent(
            showState = stateShowInformation,
            listCastState = stateShowCast,
            listCrewState = stateShowCrew,
            modifier = modifier
        )
    }
}


@Composable
private fun DetailsShowStateContent(
    showState: StateShow,
    listCastState: StateCast,
    listCrewState: StateCrew,
    modifier: Modifier = Modifier
) {
    if (showState is StateShow.Error) {
        ErrorDetailShowContent(stateShow = showState, modifier = modifier)
    }
    if (showState is StateShow.Loading) {
        LoadingStateContent(
            modifier = modifier
        )
    }
    if (showState.show != null && listCastState.listCast != null && listCrewState.listCrew != null) {
        DetailShowInformation(
            show = showState.show,
            listCast = listCastState.listCast,
            listCrew = listCrewState.listCrew,
            onClickPerson = {},
            onClickSeason = {},
            onClickBack = {},
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailShowInformation(
    show: ShowsUi,
    listCast: List<CastShowUi>,
    listCrew: List<CrewShowUi>,
    onClickPerson: (Int) -> Unit,
    onClickSeason: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Box(modifier = modifier.background(color = AppTheme.colorScheme.transparent)) {
        SubcomposeAsyncImage(
            model = show.image.original, contentDescription = null,
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
                DescriptionShowSection(show = show)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = AppTheme.size.dp8,
                            vertical = AppTheme.size.dp4
                        )
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
                        listCast = listCast,
                        listCrew = listCrew,
//                        seasonsShow = listSeasons,
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
@Suppress("LongMethod")
private fun DescriptionShowSection(
    show: ShowsUi,
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
                    text = " • " + show.premiered.substring(
                        startIndex = 0,
                        endIndex = 4
                    ),
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
            Text(
                text = HtmlCompat.fromHtml(
                    show.summary,
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
private fun ErrorDetailShowContent(stateShow: StateShow.Error, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ShowsErrorContent", color = AppTheme.colorScheme.error)
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
