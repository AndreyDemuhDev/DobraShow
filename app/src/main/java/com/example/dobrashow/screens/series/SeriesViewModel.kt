package com.example.dobrashow.screens.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dobrashow.repositories.ShowRepository
import com.example.network.models.domain.DomainShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _listSeriesState = MutableStateFlow<SeriesUiState>(SeriesUiState.Loading)
    val listSeriesState: StateFlow<SeriesUiState> = _listSeriesState.asStateFlow()

    private val fetchedSeriesPage = mutableListOf<List<DomainShowEntity>>()

    fun initialPage() =
        viewModelScope.launch {
            if (fetchedSeriesPage.isNotEmpty()) return@launch
            val initialPage = showRepository.getListShow(numberPage = 0)
            initialPage.onSuccess { showPage ->
                fetchedSeriesPage.clear()
                fetchedSeriesPage.add(showPage)
                _listSeriesState.update { return@update SeriesUiState.Success(listShow = showPage) }
            }.onException {
                //TODO
            }
        }


    fun fetchNextPage() =
        viewModelScope.launch {
            val nextPageIndex = fetchedSeriesPage.size + 1
            showRepository.getListShow(numberPage = nextPageIndex).onSuccess { seriesPage ->
                fetchedSeriesPage.add(seriesPage)
                _listSeriesState.update { currentState ->
                    val currentShows =
                        (currentState as? SeriesUiState.Success)?.listShow ?: emptyList()
                    return@update SeriesUiState.Success(listShow = currentShows + seriesPage)
                }
            }.onException { }
        }

}


sealed interface SeriesUiState {
    data class Success(val listShow: List<DomainShowEntity> = emptyList()) : SeriesUiState
    data class Error(val message: String) : SeriesUiState
    object Loading : SeriesUiState
}