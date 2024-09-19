package com.example.dobrashow.screens.show

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShowsScreen(
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showViewModel: HomeViewModel = hiltViewModel(),
) {
//    val viewState by showViewModel.listShowState.collectAsState()
//
//    LaunchedEffect(key1 = Unit, block = { showViewModel.initialPage() })
//
//    val scrollState = rememberLazyGridState()
//    val fetchNextPage: Boolean by remember {
//        derivedStateOf {
//            val currentShowCount = (viewState as? ShowUiState.Success)?.listShow?.size
//                ?: return@derivedStateOf false
//            val lastDisplayedIndexShow = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
//                ?: return@derivedStateOf false
//            return@derivedStateOf lastDisplayedIndexShow >= currentShowCount - 10
//        }
//    }
//    Log.d("MyLog", "FetchNextPage $fetchNextPage")
//
//    LaunchedEffect(key1 = fetchNextPage, block = {
//        if (fetchNextPage) showViewModel.fetchNextPage()
//    })
//
//    when (val state = viewState) {
//        is ShowUiState.Error -> {
//            Text(text = "Error load show list")
//        }
//
//        ShowUiState.Loading -> {
//            LoadingStateContent()
//        }
//
//        is ShowUiState.Success -> {
//            SuccessStateShowContent(
//                scrollState = scrollState,
//                state = state,
//                onClickShow = onClickShow,
//                modifier = modifier
//            )
//        }
//    }
}

@Composable
private fun SuccessStateShowContent(
    scrollState: LazyGridState,
//    state: ShowUiState.Success,
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
//    Column(modifier = modifier) {
//        CustomTopBarComponent(
//            title = "All shows",
//            modifier = Modifier.padding(horizontal = com.example.uikit.AppTheme.size.dp16)
//        )
//        LazyVerticalGrid(
//            state = scrollState,
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(horizontal = com.example.uikit.AppTheme.size.dp16),
//            verticalArrangement = Arrangement.spacedBy(com.example.uikit.AppTheme.size.dp8),
//            horizontalArrangement = Arrangement.spacedBy(com.example.uikit.AppTheme.size.dp16),
//            content = {
//                items(items = state.listShow, key = { show -> show.id }) { show ->
//                    ShowItemCard(
//                        show = show,
//                        onClickShow = { onClickShow(show.id) },
//                    )
//                }
//            }
//        )
//    }
}
