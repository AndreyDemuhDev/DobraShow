package com.example.view_show.screens.details_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetShowInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class DetailsShowViewModel @Inject constructor(
    getDetailShowsUseCases: Provider<GetShowInformationUseCase>
) : ViewModel() {

    val showDetailState: StateFlow<StateShow> = getDetailShowsUseCases.get().showInformation(1)
        .map { it.toStateShow() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StateShow.None
        )
}


private fun RequestStatus<List<CastShowUi>>.toStateCast(): StateCast {
    return when (this) {
        is RequestStatus.Error -> StateCast.Error(data)
        is RequestStatus.InProgress -> StateCast.Loading(data)
        is RequestStatus.Success -> StateCast.Success(data)
    }
}

sealed interface StateCast {
    data object None : StateCast
    data class Loading(val castList: List<CastShowUi>? = null) : StateCast
    data class Error(val castList: List<CastShowUi>? = null) : StateCast
    data class Success(val castList: List<CastShowUi>) : StateCast
}

private fun RequestStatus<List<CrewShowUi>>.toStateCrew(): StateCrew {
    return when (this) {
        is RequestStatus.Error -> StateCrew.Error(data)
        is RequestStatus.InProgress -> StateCrew.Loading(data)
        is RequestStatus.Success -> StateCrew.Success(data)
    }
}

sealed interface StateCrew {
    data object None : StateCrew
    data class Loading(val crewsList: List<CrewShowUi>? = null) : StateCrew
    data class Error(val crewsList: List<CrewShowUi>? = null) : StateCrew
    data class Success(val crewsList: List<CrewShowUi>) : StateCrew
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
