package com.example.dobrashow.screens.search

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.components.SearchFieldComponent
import com.example.dobrashow.ui.components.ShowItemCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onClickShow: (Int) -> Unit,
    showViewModel: SearchViewModel = hiltViewModel()
) {
    val viewState by showViewModel.listSearchShowState.collectAsState()

    val scrollState = rememberLazyGridState()

    when (val state = viewState) {
        is SearchUiState.Error -> {
            Text(text = "Error load show list")
        }

        SearchUiState.Empty -> {
            Text(text = "Empty")
        }

        is SearchUiState.Success -> {
            SuccessStateShowContent(
                scrollState = scrollState,
                state = state,
                onClickShow = onClickShow,
                onSearch = { showViewModel.searchShow(it) }
            )
        }
    }
}

@Composable
private fun SuccessStateShowContent(
    scrollState: LazyGridState,
    state: SearchUiState.Success,
    onClickShow: (Int) -> Unit,
    onSearch: (String) -> Unit,
) {
    Column {
        CustomTopBarComponent(
            title = "Search shows",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SearchFieldComponent(onSearch = onSearch, modifier = Modifier.padding(vertical = 8.dp))

        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(items = state.listShow) { show ->
                    ShowItemCard(
                        show = show,
                        onClickShow = { onClickShow(show.id) },
                    )
                }
            }
        )
    }
}