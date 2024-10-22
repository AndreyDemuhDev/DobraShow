package com.example.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.domain.model.ShowsUi
import com.example.ui.CustomTopBarComponent
import com.example.ui.SearchFieldComponent
import com.example.ui.SearchShowItemCard

@Composable
fun SearchMainScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchState by searchViewModel.searchState.collectAsState()

    if (searchState != SearchShowState.None) {
        SearchShowContentState(
            searchState = searchState,
            onClickShow = onClickShow,
            onSearch = { searchViewModel.searchShow(query = "breaking")
                     Log.d("MyLog", "search query $it")  },
            modifier = modifier
        )
    }

}

@Composable
internal fun SearchShowContentState(
    searchState: SearchShowState,
    onClickShow: (Int) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (searchState is SearchShowState.Error) {
        ErrorSearchContent(searchState, modifier = modifier)
    }
    if (searchState is SearchShowState.Loading) {
        ProgressSearchContent(searchState, modifier = modifier)
    }
    if (searchState.listShow != null) {
        SuccessSearchShowContent(
            searchShowState = searchState.listShow,
            onClickShow = onClickShow,
            onSearch = onSearch,
            modifier = modifier
        )
    }
}

@Composable
fun SuccessSearchShowContent(
    searchShowState: List<ShowsUi>,
    onClickShow: (Int) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        CustomTopBarComponent(
            title = "Search shows",
            modifier = modifier.padding(horizontal = AppTheme.size.dp16)
        )
        SearchFieldComponent(
            onSearch = onSearch,
            modifier = Modifier.padding(vertical = AppTheme.size.dp8)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = AppTheme.size.dp16),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
            modifier = Modifier.padding(vertical = AppTheme.size.dp8),
            content = {
                Log.d("MyLog", "listShows $searchShowState")
                items(items = searchShowState) { show ->
                    SearchShowItemCard(
                        show = show,
                        onClickShow = { onClickShow(show.id) },
                    )
                }
            }
        )
    }
}

@Composable
private fun ErrorSearchContent(
    searchShowState: SearchShowState.Error,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Search Shows Error Content", color = AppTheme.colorScheme.error)
        }
    }
}

@Composable
private fun ProgressSearchContent(
    searchShowState: SearchShowState.Loading,
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
