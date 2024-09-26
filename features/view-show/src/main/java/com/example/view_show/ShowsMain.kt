package com.example.view_show

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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.uikit.theme.AppTheme

@Composable
fun ShowsMainScreen(modifier: Modifier = Modifier) {
    ShowsScreen(viewModel = viewModel())
}

@Composable
internal fun ShowsScreen(
    viewModel: ShowViewModel,
) {
    Log.d("MyLog", "ShowsScreen")
    val state by viewModel.state.collectAsState()
    val currentState = state

    if (state != State.None) {
        ShowsContent(currentState)
    }
}

@Composable
private fun ShowsContent(currentState: State) {
    val scrollState = rememberLazyGridState()

    if (currentState is State.Error) {
        ErrorContent(currentState)
    }
    if (currentState is State.Loading) {
        ProgressIndicator(currentState)
    }
    if (currentState.shows != null) {
        ListShows(shows = currentState.shows, scrollState = scrollState)
    }
}

@Composable
private fun ErrorContent(state: State.Error) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ShowsErrorContent", color = AppTheme.colorScheme.error)
        }
    }
}

@Composable
private fun ProgressIndicator(state: State.Loading) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ListShows(
    shows: List<ShowsUI>,
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
                        onClickShow = { },
                    )
                }
            }
        )
    }
}

@Composable
fun ShowItemCard(
    show: ShowsUI,
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

@Composable
fun CustomTopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    onClickBack: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onClickBack != null) {
                IconButton(onClick = { onClickBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "arrow_back",
                        tint = AppTheme.colorScheme.text
                    )
                }
            }
            Text(
                text = title,
                color = AppTheme.colorScheme.text,
                style = AppTheme.typography.titleLarge
            )
        }
        HorizontalDivider(
            thickness = AppTheme.size.dp2,
            color = AppTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = AppTheme.size.dp8)
        )
    }
}
