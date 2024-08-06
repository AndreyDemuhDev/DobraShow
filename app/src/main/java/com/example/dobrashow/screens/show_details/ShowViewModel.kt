package com.example.dobrashow.screens.show_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dobrashow.repositories.ShowRepository
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {


    private val _showInformation =
        MutableStateFlow<ShowInformationUiState>(ShowInformationUiState.Loading)
    private val _listPeoplesShow =
        MutableStateFlow<ShowPeoplesListUiState>(ShowPeoplesListUiState.Loading)
    private val _listsssssCast = MutableStateFlow<List<DomainCastEntity>>(emptyList())
    private val _listsssssCrew = MutableStateFlow<List<DomainCrewEntity>>(emptyList())
    private val isError = MutableStateFlow(false)
    private val isRefreshing = MutableStateFlow(false)

    val showUiState: StateFlow<ShowUiStateView> = combine(
        _showInformation,
        _listPeoplesShow,
        isError,
        isRefreshing,
    ) { showInfoResult, listPeoplesShowResult, isRefreshingResult, isErrorResult ->

        val showInfo = when (showInfoResult) {
            is ShowInformationUiState.Error -> ShowInformationUiState.Error(message = showInfoResult.message)
            ShowInformationUiState.Loading -> ShowInformationUiState.Loading
            is ShowInformationUiState.Success -> ShowInformationUiState.Success(show = showInfoResult.show)
        }
        val listPeopleShow = when (listPeoplesShowResult) {
            is ShowPeoplesListUiState.Error -> ShowPeoplesListUiState.Error(message = listPeoplesShowResult.message)
            ShowPeoplesListUiState.Loading -> ShowPeoplesListUiState.Loading
            is ShowPeoplesListUiState.Success -> ShowPeoplesListUiState.Success(
                castList = listPeoplesShowResult.castList,
                crewList = listPeoplesShowResult.crewList
            )
        }

        ShowUiStateView(
            showInformation = showInfo,
            showPeoplesList = listPeopleShow,
            isError = isErrorResult,
            isRefreshing = isRefreshingResult
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = ShowUiStateView(
            showInformation = ShowInformationUiState.Loading,
            showPeoplesList = ShowPeoplesListUiState.Loading,
            isError = false,
            isRefreshing = false
        )
    )

    fun getShowInformation(showId: Int) =
        viewModelScope.launch {
            _showInformation.update { return@update ShowInformationUiState.Loading }
            showRepository.getShowInformation(showId = showId)
                .onSuccess { show ->
                    _showInformation.update {
                        return@update ShowInformationUiState.Success(show = show)
                    }
                }
                .onException { exception ->
                    _showInformation.update {
                        return@update ShowInformationUiState.Error(
                            message = exception.message ?: "unknown error"
                        )
                    }
                }
        }


    fun getPeoplesShowInformation(showId: Int) {
        viewModelScope.launch {
            showRepository.getCast(showId = showId)
                .onSuccess { cast ->
                    _listsssssCast.value = cast
                }.onException { exception ->
                    _listPeoplesShow.update {
                        return@update ShowPeoplesListUiState.Error(
                            message = exception.message ?: "unknown error cast information"
                        )
                    }
                }
            showRepository.getCrew(showId = showId)
                .onSuccess { crew ->
                    _listsssssCrew.value = crew
                }.onException { exception ->
                    _listPeoplesShow.update {
                        return@update ShowPeoplesListUiState.Error(
                            message = exception.message ?: "unknown error crew information"
                        )
                    }
                }
            _listPeoplesShow.update {
                return@update ShowPeoplesListUiState.Success(
                    castList = _listsssssCast.value,
                    crewList = _listsssssCrew.value
                )
            }
        }
    }
}

//общее состояние экрана деталей шоу
data class ShowUiStateView(
    val showInformation: ShowInformationUiState,
    val showPeoplesList: ShowPeoplesListUiState,
    val isError: Boolean,
    val isRefreshing: Boolean,
)


//состояние отображающее информацию о шоу
sealed interface ShowInformationUiState {
    data class Success(val show: DomainShowEntity) : ShowInformationUiState
    data class Error(val message: String) : ShowInformationUiState
    object Loading : ShowInformationUiState
}

//состояние отображающее информацию об актерах шоу
sealed interface ShowPeoplesListUiState {
    data class Success(
        val castList: List<DomainCastEntity>,
        val crewList: List<DomainCrewEntity>
    ) : ShowPeoplesListUiState

    data class Error(val message: String) : ShowPeoplesListUiState
    object Loading : ShowPeoplesListUiState
}

private const val StopTimeoutMillis: Long = 5000

val WhileUiSubscribed: SharingStarted = SharingStarted.WhileSubscribed(StopTimeoutMillis)


