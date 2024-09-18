package com.example.dobrashow.screens.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.DomainShowEntity
import com.example.shows_data.RequestStatus
import com.example.shows_data.model.Shows
import com.example.view_show.GetAllShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllShowsUseCases: Provider<GetAllShowsUseCase>
) : ViewModel() {

    private val _listShowState = MutableStateFlow<ShowUiState>(ShowUiState.Loading)
    val listShowState: StateFlow<ShowUiState> = _listShowState.asStateFlow()

    private val fetchedShowPage = mutableListOf<List<DomainShowEntity>>()

//    private val state: StateFlow<State> = getAllShowsUseCases.get().invoke()
//        .map { it.toState() }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Lazily, State.None
//        )


    fun initialPage() =
        viewModelScope.launch {
//            if (fetchedShowPage.isNotEmpty()) return@launch
//            val initialPage = showRepository.getListShow(numberPage = 0)
//            initialPage.onSuccessStatus { showPage ->
//                fetchedShowPage.clear()
//                fetchedShowPage.add(showPage)
//                _listShowState.update { return@update ShowUiState.Success(listShow = showPage) }
//            }.onFailure{
//
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
//
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
    class Loading(val shows: List<Shows>? = null) : State()
    class Error(val shows: List<Shows>? = null) : State()
    class Success(val shows: List<Shows>) : State()
}