package com.example.dobrashow.screens.search
//
//import android.util.Log
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.dobrashow.ui.components.CustomTopBarComponent
//import com.example.dobrashow.ui.components.SearchFieldComponent
//import com.example.dobrashow.ui.components.SearchShowItemCard
//import com.example.uikit.theme.AppTheme
//
//@Composable
//fun SearchScreen(
//    onClickShow: (Int) -> Unit,
//    showViewModel: SearchViewModel = hiltViewModel()
//) {
//    val viewState by showViewModel.listSearchShowState.collectAsState()
//
//    when (val state = viewState) {
//        is SearchUiState.Error -> {
//            Text(text = "Error load show list")
//        }
//
//        SearchUiState.Empty -> {
//            Text(text = "Empty")
//        }
//
//        is SearchUiState.Success -> {
//            SuccessStateShowContent(
//                state = state,
//                onClickShow = onClickShow,
//                onSearch = { showViewModel.searchShow(it) }
//            )
//        }
//    }
//}
//
//@Composable
//private fun SuccessStateShowContent(
//    state: SearchUiState.Success,
//    onClickShow: (Int) -> Unit,
//    onSearch: (String) -> Unit,
//) {
//    Column {
//        CustomTopBarComponent(
//            title = "Search shows",
//            modifier = Modifier.padding(horizontal = AppTheme.size.dp16)
//        )
//        SearchFieldComponent(
//            onSearch = onSearch,
//            modifier = Modifier.padding(vertical = AppTheme.size.dp8)
//        )
//
//        LazyColumn(
//            contentPadding = PaddingValues(horizontal = AppTheme.size.dp16),
//            verticalArrangement = Arrangement.spacedBy(AppTheme.size.dp8),
//            modifier = Modifier.padding(vertical = AppTheme.size.dp8),
//            content = {
//                Log.d("MyLog", "listShows ${state.listShow}")
//                items(items = state.listShow) { show ->
//                    SearchShowItemCard(
//                        show = show,
//                        onClickShow = { show.searchShows?.id?.let { onClickShow(it) } },
//                    )
//                }
//            }
//        )
//    }
//}
