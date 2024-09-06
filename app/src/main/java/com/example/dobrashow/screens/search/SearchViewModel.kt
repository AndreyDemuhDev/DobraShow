package com.example.dobrashow.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows_data.repositories.ShowRepository
import com.example.dobrashow.screens.show.ShowUiState
import com.example.network.models.domain.DomainSearchShowEntity
import com.example.network.models.domain.DomainShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _listSearchShowState = MutableStateFlow<SearchUiState>(SearchUiState.Success())
    val listSearchShowState: StateFlow<SearchUiState> = _listSearchShowState.asStateFlow()


    fun searchShow(query: String) =
        viewModelScope.launch {
            showRepository.searchShow(query = query).onSuccess { listShow ->
                _listSearchShowState.update {
                    return@update SearchUiState.Success(listShow = listShow)
                }
            }.onFailure { exception ->
                _listSearchShowState.update {
                    return@update SearchUiState.Error(
                        message = exception.message ?: "error search show"
                    )
                }
            }
        }

}



sealed interface SearchUiState {
    data class Success(val listShow: List<DomainSearchShowEntity> = emptyList()) : SearchUiState
    data class Error(val message: String) : SearchUiState
    object Empty : SearchUiState
}