package com.example.dobrashow.screens.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.DomainShowEntity
import com.example.shows_data.RequestStatus
import com.example.shows_data.mapperStatus
import com.example.shows_data.model.Shows
import com.example.shows_data.repositories.ShowRepository
import com.example.view_show.GetAllShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllShowsUseCases: GetAllShowsUseCase
) : ViewModel() {

    private val _listShowState = MutableStateFlow<ShowUiState>(ShowUiState.Loading)
    val listShowState: StateFlow<ShowUiState> = _listShowState.asStateFlow()

    private val fetchedShowPage = mutableListOf<List<DomainShowEntity>>()

    private val state: StateFlow<State> = getAllShowsUseCases()
        .map { it.toState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily, State.None
        )

    fun initialPage() =
        viewModelScope.launch {
//            if (fetchedShowPage.isNotEmpty()) return@launch
//            val initialPage = showRepository.getListShow(numberPage = 0)
//            initialPage.onSuccessStatus { showPage ->
//                fetchedShowPage.clear()
//                fetchedShowPage.add(showPage)
//                _listShowState.update { return@update ShowUiState.Success(listShow = showPage) }
//            }.onFailure{
//                TODO("Need implementation")
//            }
        }


    fun fetchNextPage() =
        viewModelScope.launch {
//            val nextPageIndex = fetchedShowPage.size + 1
//            showRepository.getListShow(numberPage = nextPageIndex).onSuccess { showPage ->
//                fetchedShowPage.add(showPage)
//                _listShowState.update { currentState ->
//                    val currentShows =
//                        (currentState as? ShowUiState.Success)?.listShow ?: emptyList()
//                    return@update ShowUiState.Success(listShow = currentShows + showPage)
//                }
//            }.onFailure {
//                TODO("Need implementation")
//            }
        }
}


private fun RequestStatus<List<Shows>>.toState(): State {
    return when (this) {
        is RequestStatus.Error -> State.Error()
        is RequestStatus.InProgress -> State.Loading(shows = data)
        is RequestStatus.Success -> State.Success(shows = checkNotNull(data))
    }
}


sealed interface ShowUiState {
    data class Success(val listShow: List<DomainShowEntity> = emptyList()) : ShowUiState
    data class Error(val message: String) : ShowUiState
    object Loading : ShowUiState
}

sealed class State {
    object None : State()
    class Loading(val shows: List<Shows>?) : State()
    class Error : State()
    class Success(val shows: List<Shows>) : State()
}