package com.example.dobrashow.screens.show

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

    private val _listShowState = MutableStateFlow<ShowUiState>(ShowUiState.Loading)
    val listShowState: StateFlow<ShowUiState> = _listShowState.asStateFlow()

    private val fetchedShowPage = mutableListOf<List<DomainShowEntity>>()

    fun initialPage() =
        viewModelScope.launch {
            if (fetchedShowPage.isNotEmpty()) return@launch
            val initialPage = showRepository.getListShow(numberPage = 0)
            initialPage.onSuccess { showPage ->
                fetchedShowPage.clear()
                fetchedShowPage.add(showPage)
                _listShowState.update { return@update ShowUiState.Success(listShow = showPage) }
            }.onException {
                //TODO
            }
        }


    fun fetchNextPage() =
        viewModelScope.launch {
            val nextPageIndex = fetchedShowPage.size + 1
            showRepository.getListShow(numberPage = nextPageIndex).onSuccess { showPage ->
                fetchedShowPage.add(showPage)
                _listShowState.update { currentState ->
                    val currentShows =
                        (currentState as? ShowUiState.Success)?.listShow ?: emptyList()
                    return@update ShowUiState.Success(listShow = currentShows + showPage)
                }
            }.onException { }
        }
}


sealed interface ShowUiState {
    data class Success(val listShow: List<DomainShowEntity> = emptyList()) : ShowUiState
    data class Error(val message: String) : ShowUiState
    object Loading : ShowUiState
}