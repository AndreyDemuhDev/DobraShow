package com.example.view_show.screens.details_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.SeasonsShowUi
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetShowInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class DetailsShowViewModel @Inject constructor(
    private val getDetailShowsUseCases: Provider<GetShowInformationUseCase>
) : ViewModel() {

    var showDetailState: StateFlow<StateShow> = getDetailShowsUseCases.get().showInformation(0)
        .map { it.toStateShow() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StateShow.None
        )

    var showCastState: StateFlow<StateCast> = getDetailShowsUseCases.get().showCastList(0)
        .map { it.toStateCast() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StateCast.None
        )

    var showCrewState: StateFlow<StateCrew> = getDetailShowsUseCases.get().showCrewList(0)
        .map { it.toStateCrew() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StateCrew.None
        )

    var showSeasonsState: StateFlow<StateSeason> = getDetailShowsUseCases.get().showSeasonsList(0)
        .map { it.toStateSeason() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StateSeason.None
        )

    fun getShowDetails(showId: Int) {
        viewModelScope.launch {
            showDetailState = getDetailShowsUseCases.get().showInformation(showId)
                .map { it.toStateShow() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    StateShow.None
                )
        }
    }

    fun getShowCast(showId: Int) {
        viewModelScope.launch {
            showCastState = getDetailShowsUseCases.get().showCastList(showId)
                .map { it.toStateCast() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    StateCast.None
                )
        }
    }

    fun getShowCrew(showId: Int) {
        viewModelScope.launch {
            showCrewState = getDetailShowsUseCases.get().showCrewList(showId)
                .map { it.toStateCrew() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    StateCrew.None
                )
        }
    }

    fun getSeasonsShow(showId: Int) {
        viewModelScope.launch {
            showSeasonsState = getDetailShowsUseCases.get().showSeasonsList(showId)
                .map { it.toStateSeason() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    StateSeason.None
                )
        }
    }
}


private fun RequestStatus<List<CastShowUi>>.toStateCast(): StateCast {
    return when (this) {
        is RequestStatus.Error -> StateCast.Error(data)
        is RequestStatus.InProgress -> StateCast.Loading(data)
        is RequestStatus.Success -> StateCast.Success(data)
    }
}

sealed class StateCast(val listCast: List<CastShowUi>?) {
    data object None : StateCast(listCast = null)
    class Loading(listCast: List<CastShowUi>? = null) : StateCast(listCast = listCast)
    class Error(listCast: List<CastShowUi>? = null) : StateCast(listCast = listCast)
    class Success(listCast: List<CastShowUi>) : StateCast(listCast = listCast)
}

private fun RequestStatus<List<CrewShowUi>>.toStateCrew(): StateCrew {
    return when (this) {
        is RequestStatus.Error -> StateCrew.Error(data)
        is RequestStatus.InProgress -> StateCrew.Loading(data)
        is RequestStatus.Success -> StateCrew.Success(data)
    }
}

sealed class StateCrew(val listCrew: List<CrewShowUi>?) {
    data object None : StateCrew(listCrew = null)
    class Loading(listCrew: List<CrewShowUi>? = null) : StateCrew(listCrew = listCrew)
    class Error(listCrew: List<CrewShowUi>? = null) : StateCrew(listCrew = listCrew)
    class Success(listCrew: List<CrewShowUi>) : StateCrew(listCrew = listCrew)
}

private fun RequestStatus<ShowsUi>.toStateShow(): StateShow {
    return when (this) {
        is RequestStatus.Error -> StateShow.Error(data)
        is RequestStatus.InProgress -> StateShow.Loading(data)
        is RequestStatus.Success -> StateShow.Success(data)
    }
}

sealed class StateShow(val show: ShowsUi?) {
    data object None : StateShow(show = null)
    class Loading(show: ShowsUi? = null) : StateShow(show = show)
    class Error(show: ShowsUi? = null) : StateShow(show = show)
    class Success(show: ShowsUi) : StateShow(show = show)
}

private fun RequestStatus<List<SeasonsShowUi>>.toStateSeason(): StateSeason {
    return when (this) {
        is RequestStatus.Error -> StateSeason.Error(data)
        is RequestStatus.InProgress -> StateSeason.Loading(data)
        is RequestStatus.Success -> StateSeason.Success(data)
    }
}

sealed class StateSeason(val listSeasons: List<SeasonsShowUi>?) {
    data object None : StateSeason(listSeasons = null)
    class Loading(listSeasons: List<SeasonsShowUi>? = null) : StateSeason(listSeasons = listSeasons)
    class Error(listSeasons: List<SeasonsShowUi>? = null) : StateSeason(listSeasons = listSeasons)
    class Success(listSeasons: List<SeasonsShowUi>) : StateSeason(listSeasons = listSeasons)
}
