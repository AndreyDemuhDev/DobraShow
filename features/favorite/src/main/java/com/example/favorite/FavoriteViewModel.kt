package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetFavoriteShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getAllShowsUseCases: Provider<GetFavoriteShowUseCase>
): ViewModel() {


}

private fun RequestStatus<List<ShowsUi>>.toStateFavoriteListShows(): ListFavoriteShowsState {
    return when (this) {
        is RequestStatus.Error -> ListFavoriteShowsState.Error(data)
        is RequestStatus.InProgress -> ListFavoriteShowsState.Loading(data)
        is RequestStatus.Success -> ListFavoriteShowsState.Success(data)
    }
}

sealed class ListFavoriteShowsState(val listShows: List<ShowsUi>?) {
    data object None : ListFavoriteShowsState(listShows = null)
    class Loading(listShows: List<ShowsUi>? = null) : ListFavoriteShowsState(listShows = listShows)
    class Error(listShows: List<ShowsUi>? = null) : ListFavoriteShowsState(listShows = listShows)
    class Success(listShows: List<ShowsUi>) : ListFavoriteShowsState(listShows = listShows)
}