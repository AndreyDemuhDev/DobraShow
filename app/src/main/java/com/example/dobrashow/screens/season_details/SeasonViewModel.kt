package com.example.dobrashow.screens.season_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows_data.repositories.ShowRepository
import com.example.network.models.domain.DomainSeasonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(
    private val showRepository: com.example.shows_data.repositories.ShowRepository
) : ViewModel() {

    private val _seasonState = MutableStateFlow<SeasonUiState>(SeasonUiState.Loading)
    val seasonState: StateFlow<SeasonUiState> = _seasonState.asStateFlow()


    fun getSeasonInfo(seasonId: Int) =
        viewModelScope.launch {
            showRepository.getSeasonInfo(seasonId = seasonId).onSuccess { season ->
                _seasonState.update {
                    return@update SeasonUiState.Success(season = season)
                }
            }.onException { exception ->
                _seasonState.update {
                    return@update SeasonUiState.Error(
                        message = exception.message ?: "error load season info"
                    )
                }
            }
        }
}


sealed interface SeasonUiState {
    data class Success(val season: DomainSeasonEntity) : SeasonUiState
    data class Error(val message: String) : SeasonUiState
    object Loading : SeasonUiState
}