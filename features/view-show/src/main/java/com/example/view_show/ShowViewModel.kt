package com.example.view_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows_data.RequestStatus
import com.example.shows_data.model.ShowsUi
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

     val state: StateFlow<State> = getAllShowsUseCases.get().invoke()
        .map { it.toState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, State.None
        )
}


private fun RequestStatus<List<ShowsUi>>.toState(): State {
    return when (this) {
        is RequestStatus.Error -> State.Error()
        is RequestStatus.InProgress -> State.Loading(shows = data)
        is RequestStatus.Success -> State.Success(shows = checkNotNull(data))
    }
}

sealed class State {
    object None : State()
    class Loading(val shows: List<ShowsUi>? = null) : State()
    class Error(val shows: List<ShowsUi>? = null) : State()
    class Success(val shows: List<ShowsUi>) : State()
}