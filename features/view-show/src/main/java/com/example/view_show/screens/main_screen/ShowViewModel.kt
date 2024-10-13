package com.example.view_show.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetAllShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider


@HiltViewModel
class ShowViewModel @Inject constructor(
    getAllShowsUseCases: Provider<GetAllShowsUseCase>
) : ViewModel() {

    val listShowsState: StateFlow<ListShowsState> = getAllShowsUseCases.get().invoke(1)
        .map { it.toStateListShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ListShowsState.None
        )
}


private fun RequestStatus<List<ShowsUi>>.toStateListShows(): ListShowsState {
    return when (this) {
        is RequestStatus.Error -> ListShowsState.Error(data)
        is RequestStatus.InProgress -> ListShowsState.Loading(data)
        is RequestStatus.Success -> ListShowsState.Success(data)
    }
}

sealed class ListShowsState(val listShows: List<ShowsUi>?) {
    data object None : ListShowsState(listShows = null)
    class Loading(listShows: List<ShowsUi>? = null) : ListShowsState(listShows = listShows)
    class Error(listShows: List<ShowsUi>? = null) : ListShowsState(listShows = listShows)
    class Success(listShows: List<ShowsUi>) : ListShowsState(listShows = listShows)
}
