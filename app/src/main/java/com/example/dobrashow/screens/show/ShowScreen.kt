package com.example.dobrashow.screens.show

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dobrashow.screens.show_details.LoadingStateContent
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.components.ShowItemCard
import com.example.dobrashow.ui.theme.AppTheme
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun ShowsScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showViewModel: HomeViewModel = hiltViewModel(),
) {

    val viewState by showViewModel.listShowState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { showViewModel.initialPage() })

    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentShowCount = (viewState as? ShowUiState.Success)?.listShow?.size
                ?: return@derivedStateOf false
            val lastDisplayedIndexShow = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndexShow >= currentShowCount - 10
        }
    }
    Log.d("MyLog", "FetchNextPage $fetchNextPage")

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) showViewModel.fetchNextPage()
    })

    when (val state = viewState) {
        is ShowUiState.Error -> {
            Text(text = "Error load show list")
        }

        ShowUiState.Loading -> {
            LoadingStateContent()
        }

        is ShowUiState.Success -> {
            SuccessStateShowContent(
                scrollState = scrollState,
                state = state,
                onClickShow = onClickShow,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun SuccessStateShowContent(
    scrollState: LazyGridState,
    state: ShowUiState.Success,
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haze = remember {
        HazeState()
    }
    Column {
        CustomTopBarComponent(
            title = "All shows",
            modifier = Modifier.padding(horizontal = AppTheme.size.dp16)
        )
        LazyVerticalGrid(
            modifier = Modifier.hazeChild(
                state = haze,
                style = HazeDefaults.style(backgroundColor = AppTheme.colorScheme.onBackground),
            ),
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = AppTheme.size.dp16),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.size.dp16),
            content = {
                items(items = state.listShow, key = { show -> show.id }) { show ->
                    ShowItemCard(
                        show = show,
                        onClickShow = { onClickShow(show.id) },
                    )
                }
            }
        )
    }
}