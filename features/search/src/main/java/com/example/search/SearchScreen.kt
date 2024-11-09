@file:OptIn(ExperimentalFoundationApi::class)

package com.example.search

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.delete
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.domain.model.ShowsUi
import com.example.ui.CustomTopBarComponent
import com.example.ui.R
import com.example.ui.SearchFieldComponent
import com.example.ui.SearchShowItemCard

@Composable
fun SearchMainScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchScreen(
        onClickShow = onClickShow,
        modifier = modifier
    )
}


@Composable
internal fun SearchScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
//    val searchState by searchViewModel.searchState.collectAsState()
    val searchState = searchViewModel.searchTextFieldState

    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "SearchScreen", color = AppTheme.colorScheme.primary)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.size.dp8)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = AppTheme.size.dp8)
                    .background(
                        color = AppTheme.colorScheme.onPrimary,
                        shape = AppTheme.shape.small
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.size.dp8)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    modifier = Modifier
                        .padding(all = AppTheme.size.dp8)
                        .size(28.dp)
                        .clickable { },
                    colorFilter = ColorFilter.tint(AppTheme.colorScheme.background)
                )
                BasicTextField2(
                    state = searchState,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 20.sp)
                )
            }
            AnimatedVisibility(visible = searchState.text.isNotBlank()) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "delete icon",
                    tint = AppTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(end = AppTheme.size.dp4)
                        .clickable {
                            searchState.edit { delete(0, length) }
                        }
                )
            }
        }
        val searchShowText by searchViewModel.searchTextState.collectAsState()
        Text(text = searchShowText, color = AppTheme.colorScheme.onBackground)

    }

//     if (searchState != SearchShowState.None) {
//        SearchShowContentState(
//            searchState = searchState,
//            onClickShow = onClickShow,
//            onSearch = { searchViewModel.searchShow(query = it)
//                Log.d("MyLog", "ввод в поиск = $it")},
//            modifier = modifier
//        )
//    }
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

    Log.d("MyLog", "список шоу $searchShowState")
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
