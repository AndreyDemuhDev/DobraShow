package com.example.dobrashow.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dobrashow.ui.components.ShowItemCard

@Composable
fun HomeScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val viewState by viewModel.listShowState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { viewModel.initialPage() })

    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentShowCount = (viewState as? HomeUiState.Success)?.listShow?.size
                ?: return@derivedStateOf false
            val lastDisplayedIndexShow = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndexShow >= currentShowCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) viewModel.fetchNextPage()
    })

    when (val state = viewState) {
        is HomeUiState.Error -> {
            Text(text = "Error load show list")
        }

        HomeUiState.Loading -> {
            Text(text = "Loading list show")
        }

        is HomeUiState.Success -> {
            LazyVerticalGrid(
                state = scrollState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(items = state.listShow, key = { it.id }) { show ->
                        ShowItemCard(
                            show = show,
                            onClickShow = { onClickShow(show.id) },
                        )
                    }
                }
            )
        }
    }
}