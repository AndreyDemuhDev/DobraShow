@file:OptIn(ExperimentalFoundationApi::class)

package com.example.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.ui.CustomTopBarComponent
import com.example.ui.R
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
    val searchState = searchViewModel.searchTextFieldState

    DisposableEffect(key1 = Unit) {
        val job = searchViewModel.observeSearchShow()
        onDispose { job.cancel() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.size.dp16)
    ) {
        CustomTopBarComponent(title = "Search shows")

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
                        .size(28.dp),
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
        val screenState by searchViewModel.uiState.collectAsState()
        when (val state = screenState) {
            SearchViewModel.SearchShowScreenState.None -> {
                Text(
                    text = "Search show name here...",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.size.dp16),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp
                )
            }

            is SearchViewModel.SearchShowScreenState.Loading -> SearchingContent(
                searchingState = state
            )

            is SearchViewModel.SearchShowScreenState.Success -> SuccessSearchShowContent(
                content = state,
                onStatusClickable = searchViewModel::toggleStatus,
                onClickShow = onClickShow
            )

            is SearchViewModel.SearchShowScreenState.Error -> ErrorSearchContent(errorState = state)
        }

    }
}


@Composable
fun SuccessSearchShowContent(
    content: SearchViewModel.SearchShowScreenState.Success,
    onStatusClickable: (String) -> Unit,
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            content.filterState.statusEnded.forEach { status ->
                val isSelected = content.filterState.selectedStatuses.contains(status)
                val contentColor =
                    if (isSelected) AppTheme.colorScheme.onPrimary else AppTheme.colorScheme.error

                val count = content.listShows.filter { it.status == status }.size
                Column(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = contentColor,
                            shape = AppTheme.shape.small
                        )
                        .clickable {
                            onStatusClickable(status)
                        }
                        .clip(AppTheme.shape.medium)
                        .padding(all = AppTheme.size.dp2),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = count.toString(),
                        color = contentColor,
                        modifier = Modifier.padding(vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.bodyLarge
                    )
                    Text(
                        text = status,
                        color = contentColor,
                        modifier = Modifier.padding(horizontal = 6.dp),
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.bodySmall
                    )
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = AppTheme.size.dp4),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
            modifier = Modifier.padding(vertical = AppTheme.size.dp8),
            content = {
                val filterResult = content.listShows.filter {
                    content.filterState.selectedStatuses.contains(
                        it.status
                    )
                }
                items(items = filterResult, key = { show -> show.id }) { show ->
                    SearchShowItemCard(
                        show = show,
                        onClickShow = { onClickShow(show.id) },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        )
    }
}

@Composable
private fun ErrorSearchContent(
    errorState: SearchViewModel.SearchShowScreenState.Error,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorState.error, color = AppTheme.colorScheme.error)
        }
    }
}

@Composable
private fun SearchingContent(
    searchingState: SearchViewModel.SearchShowScreenState.Loading,
    modifier: Modifier = Modifier
) {
    Column {
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
}
