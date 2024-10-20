package com.example.view_show.screens.details_season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.SeasonsShowUi
import com.example.domain.usecases.GetDetailsSeasonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class DetailSeasonViewModel @Inject constructor(
    private val seasonUseCase: Provider<GetDetailsSeasonUseCase>
) : ViewModel() {

    var seasonState: StateFlow<StateSeason> = seasonUseCase.get().getSeasonInfo(0)
        .map { it.toStatePerson() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = StateSeason.None
        )


    fun getSeasonInfo(seasonId: Int) {
        viewModelScope.launch {
            seasonState = seasonUseCase.get().getSeasonInfo(seasonId = seasonId)
                .map { it.toStatePerson() }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = StateSeason.None
                )
        }
    }
}

private fun RequestStatus<SeasonsShowUi>.toStatePerson(): StateSeason {
    return when (this) {
        is RequestStatus.Error -> StateSeason.Error(data)
        is RequestStatus.InProgress -> StateSeason.Loading(data)
        is RequestStatus.Success -> StateSeason.Success(data)
    }
}

sealed class StateSeason(val season: SeasonsShowUi?) {
    data object None : StateSeason(season = null)
    class Loading(season: SeasonsShowUi? = null) : StateSeason(season = season)
    class Error(season: SeasonsShowUi? = null) : StateSeason(season = season)
    class Success(season: SeasonsShowUi) : StateSeason(season = season)
}