package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetSearchShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowUseCase: GetSearchShowUseCase
) : ViewModel() {


    var searchState: StateFlow<SearchShowState> = searchShowUseCase.searchShow(query = "")
        .map { it.toStateSearchShowsState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchShowState.None
        )

    fun searchShow(query: String) {
        viewModelScope.launch {
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
