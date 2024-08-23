package com.example.dobrashow.screens.show_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows_data.repositories.ShowRepository
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsShowViewModel @Inject constructor(
    private val showRepository: com.example.shows_data.repositories.ShowRepository
) : ViewModel() {

    private val showInformation =
        MutableStateFlow<ShowInformationUiState>(ShowInformationUiState.Loading)
    private val listPeoplesShow =
        MutableStateFlow<ShowPeoplesListUiState>(ShowPeoplesListUiState.Loading)
    private val listCast = MutableStateFlow<List<DomainCastEntity>>(emptyList())
    private val listCrew = MutableStateFlow<List<DomainCrewEntity>>(emptyList())
    private val listSeasons =
        MutableStateFlow<ShowSeasonsListUiState>(ShowSeasonsListUiState.Loading)
    private val isError = MutableStateFlow(false)
    private val isRefreshing = MutableStateFlow(false)

    val showUiState: StateFlow<ShowUiStateView> = combine(
        showInformation,
        listPeoplesShow,
        listSeasons,
        isError,
        isRefreshing,
    ) { showInfoResult, listPeoplesShowResult, listSeasonsResult, isRefreshingResult, isErrorResult ->

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

        val listSeasonsShow = when (listSeasonsResult) {
            is ShowSeasonsListUiState.Error -> ShowSeasonsListUiState.Error(message = listSeasonsResult.message)
            ShowSeasonsListUiState.Loading -> ShowSeasonsListUiState.Loading
            is ShowSeasonsListUiState.Success -> ShowSeasonsListUiState.Success(listSeasons = listSeasonsResult.listSeasons)
        }

        ShowUiStateView(
            showInformation = showInfo,
            showPeoplesList = listPeopleShow,
            showSeasonsList = listSeasonsShow,
            isError = isErrorResult,
            isRefreshing = isRefreshingResult
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = ShowUiStateView(
            showInformation = ShowInformationUiState.Loading,
            showPeoplesList = ShowPeoplesListUiState.Loading,
            showSeasonsList = ShowSeasonsListUiState.Loading,
            isError = false,
            isRefreshing = false
        )
    )

    fun getShowInformation(showId: Int) =
        viewModelScope.launch {
            showInformation.update { return@update ShowInformationUiState.Loading }
            showRepository.getShowInformation(showId = showId)
                .onSuccess { show ->
                    showInformation.update {
                        return@update ShowInformationUiState.Success(show = show)
                    }
                }
                .onException { exception ->
                    showInformation.update {
                        return@update ShowInformationUiState.Error(
                            message = exception.message ?: "unknown error"
                        )
                    }
                }
        }

    fun getPeoplesShowInformation(showId: Int) {
        viewModelScope.launch {
            showRepository.getListCastShow(showId = showId)
                .onSuccess { cast ->
                    listCast.value = cast
                }.onException { exception ->
                    listPeoplesShow.update {
                        return@update ShowPeoplesListUiState.Error(
                            message = exception.message ?: "unknown error cast information"
                        )
                    }
                }
            showRepository.getListCrewShow(showId = showId)
                .onSuccess { crew ->
                    listCrew.value = crew
                }.onException { exception ->
                    listPeoplesShow.update {
                        return@update ShowPeoplesListUiState.Error(
                            message = exception.message ?: "unknown error crew information"
                        )
                    }
                }
            listPeoplesShow.update {
                return@update ShowPeoplesListUiState.Success(
                    castList = listCast.value,
                    crewList = listCrew.value
                )
            }
        }
    }

    fun getListSeasonsShow(showId: Int) =
        viewModelScope.launch {
            listSeasons.update { return@update ShowSeasonsListUiState.Loading }
            showRepository.getListSeasonsShow(showId = showId)
                .onSuccess { seasons ->
                    listSeasons.update {
                        return@update ShowSeasonsListUiState.Success(listSeasons = seasons)
                    }
                }
                .onException { exception ->
                    listSeasons.update {
                        return@update ShowSeasonsListUiState.Error(
                            message = exception.message ?: "error load seasons"
                        )
                    }
                }
        }
}

//общее состояние экрана деталей шоу
data class ShowUiStateView(
    val showInformation: ShowInformationUiState,
    val showPeoplesList: ShowPeoplesListUiState,
    val showSeasonsList: ShowSeasonsListUiState,
    val isError: Boolean,
    val isRefreshing: Boolean,
)


//состояние отображающее информацию о шоу
sealed interface ShowInformationUiState {
    data class Success(val show: DomainShowEntity) : ShowInformationUiState
    data class Error(val message: String) : ShowInformationUiState
    data object Loading : ShowInformationUiState
}

//состояние отображающее информацию об актерах шоу
sealed interface ShowPeoplesListUiState {
    data class Success(
        val castList: List<DomainCastEntity>,
        val crewList: List<DomainCrewEntity>
    ) : ShowPeoplesListUiState

    data class Error(val message: String) : ShowPeoplesListUiState
    data object Loading : ShowPeoplesListUiState
}

//сщстояние отображения сезонов шоу
sealed interface ShowSeasonsListUiState {
    data class Success(val listSeasons: List<DomainSeasonEntity>) : ShowSeasonsListUiState
    data class Error(val message: String) : ShowSeasonsListUiState
    data object Loading : ShowSeasonsListUiState
}

private const val StopTimeoutMillis: Long = 5000

val WhileUiSubscribed: SharingStarted = SharingStarted.WhileSubscribed(StopTimeoutMillis)


