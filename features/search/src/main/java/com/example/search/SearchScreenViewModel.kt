package com.example.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetSearchShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowUseCase: GetSearchShowUseCase
) : ViewModel() {

    val searchTextFieldState =  TextFieldState()


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchTextState = snapshotFlow { searchTextFieldState.text }
        .debounce(500)
        .mapLatest { if (it.isBlank())"Enter search show ..." else it.toString() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
            initialValue = ""
        )

    var searchState: StateFlow<SearchShowState> = searchShowUseCase.searchShow(query = "")
        .map { it.toStateSearchShowsState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchShowState.None
        )

    fun searchShow(query: String) {
        viewModelScope.launch {
            Log.d("MyLog", "Search viewModel query = $query")
            searchState = searchShowUseCase.searchShow(query = query)
                .map { it.toStateSearchShowsState() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    SearchShowState.None
                )
        }
    }
}


private fun RequestStatus<List<ShowsUi>>.toStateSearchShowsState(): SearchShowState {
    return when (this) {
        is RequestStatus.Error -> SearchShowState.Error(data)
        is RequestStatus.InProgress -> SearchShowState.Loading(data)
        is RequestStatus.Success -> SearchShowState.Success(data)
    }
}

sealed class SearchShowState(val listShow: List<ShowsUi>?) {
    data object None : SearchShowState(listShow = null)
    class Loading(listShows: List<ShowsUi>? = null) : SearchShowState(listShow = listShows)
    class Error(listShows: List<ShowsUi>? = null) : SearchShowState(listShow = listShows)
    class Success(listShows: List<ShowsUi>) : SearchShowState(listShow = listShows)
}
