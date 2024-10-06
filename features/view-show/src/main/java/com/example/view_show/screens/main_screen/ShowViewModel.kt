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

    val state: StateFlow<State> = getAllShowsUseCases.get().invoke(1)
        .map { it.toState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            State.None
        )
}


private fun RequestStatus<List<ShowsUi>>.toState(): State {
    return when (this) {
        is RequestStatus.Error -> State.Error(data)
        is RequestStatus.InProgress -> State.Loading(data)
        is RequestStatus.Success -> State.Success(data)
    }
}

sealed class State(val shows: List<ShowsUi>?) {
    data object None : State(shows = null)
    class Loading(shows: List<ShowsUi>? = null) : State(shows)
    class Error(shows: List<ShowsUi>? = null) : State(shows)
    class Success(shows: List<ShowsUi>) : State(shows)
}
