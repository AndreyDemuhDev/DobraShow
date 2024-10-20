package com.example.view_show.screens.main_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.design.theme.AppTheme
import com.example.domain.model.ShowsUi
import com.example.ui.CustomTopBarComponent

import com.example.view_show.R

@Composable
fun ShowsMainScreen(onClickShow: (Int) -> Unit, modifier: Modifier = Modifier) {
    ShowsScreen(onClickShow = onClickShow, modifier = modifier)
}

@Composable
internal fun ShowsScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShowViewModel = hiltViewModel(),
) {
    Log.d("MyLog", "ShowsScreen")
    val state by viewModel.listShowsState.collectAsState()
    val currentState = state

    if (state != ListShowsState.None) {
        ShowsContent(
            currentListShowsState = currentState,
            onClickShow = onClickShow,
            modifier = modifier
        )
    }
}

@Composable
private fun ShowsContent(
    currentListShowsState: ListShowsState,
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyGridState()

    if (currentListShowsState is ListShowsState.Error) {
        ErrorContent(currentListShowsState, modifier = modifier)
    }
    if (currentListShowsState is ListShowsState.Loading) {
        ProgressIndicator(currentListShowsState, modifier = modifier)
    }
    if (currentListShowsState.listShows != null) {
        ListShows(
            shows = currentListShowsState.listShows,
            onClickShow = onClickShow,
            scrollState = scrollState,
            modifier = modifier
        )
    }
}

@Composable
private fun ErrorContent(listShowsState: ListShowsState.Error, modifier: Modifier = Modifier) {
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
private fun ProgressIndicator(
    listShowsState: ListShowsState.Loading,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = AppTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ListShows(
    shows: List<ShowsUi>,
    onClickShow: (Int) -> Unit,
    scrollState: LazyGridState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CustomTopBarComponent(
            title = "All shows",
            modifier = Modifier.padding(horizontal = AppTheme.size.dp16)
        )
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = AppTheme.size.dp16),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.size.dp16),
            content = {
                items(items = shows, key = { currentShow -> currentShow.id }) { show ->
                    ShowItemCard(
                        show = show,
                        onClickShow = { onClickShow(show.id) },
                    )
                }
            }
        )
    }
}

@Composable
fun ShowItemCard(
    show: ShowsUi,
    onClickShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(
            width = AppTheme.size.dp2,
            color = AppTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(AppTheme.size.dp4),
        modifier = modifier
            .size(width = 250.dp, height = 270.dp)
            .clickable { onClickShow() }
    ) {
        AsyncImage(
            model = show.image.medium,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_no_image),
            error = painterResource(id = R.drawable.ic_no_image),
            modifier = Modifier.fillMaxSize()
        )
    }
}

