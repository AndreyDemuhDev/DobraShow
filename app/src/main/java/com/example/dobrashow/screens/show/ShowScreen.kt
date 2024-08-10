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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dobrashow.screens.show_details.LoadingStateContent
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.components.SearchFieldComponent
import com.example.dobrashow.ui.components.ShowItemCard

@Composable
fun SeriesScreen(
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
            )
        }
    }
}

@Composable
private fun SuccessStateShowContent(
    scrollState: LazyGridState,
    state: ShowUiState.Success,
    onClickShow: (Int) -> Unit,
) {
    Column {
        CustomTopBarComponent(
            title = "All shows",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
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