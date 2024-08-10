package com.example.dobrashow.screens.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dobrashow.repositories.ShowRepository
import com.example.network.models.domain.DomainSimplePersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _listPersonsState = MutableStateFlow<PersonsUiState>(PersonsUiState.Loading)
    val listPersonsState: StateFlow<PersonsUiState> = _listPersonsState

    private val fetchedPersonsPage = mutableListOf<List<DomainSimplePersonEntity>>()

    fun initialPersonsPage() =
        viewModelScope.launch {
            if (fetchedPersonsPage.isNotEmpty()) return@launch
            val initialPage = showRepository.getListPersons(pageNumber = 0)
            initialPage.onSuccess { listPersons ->
                fetchedPersonsPage.clear()
                fetchedPersonsPage.add(listPersons)
                _listPersonsState.update {
                    return@update PersonsUiState.Success(listPersons = listPersons)
                }
            }.onException {
                _listPersonsState.update {
                    return@update PersonsUiState.Error(message = "Error load list persons")
                }
            }
        }

    fun fetchNextPage() =
        viewModelScope.launch {
            val nextPageIndex = fetchedPersonsPage.size + 1
            showRepository.getListPersons(pageNumber = nextPageIndex).onSuccess { personPage ->
                fetchedPersonsPage.add(personPage)
                _listPersonsState.update { currentState ->
                    val currentPersons =
                        (currentState as? PersonsUiState.Success)?.listPersons ?: emptyList()
                    return@update PersonsUiState.Success(listPersons = currentPersons + personPage)
                }
            }.onException {
                //TODO
            }
        }


}


sealed interface PersonsUiState {
    data class Success(val listPersons: List<DomainSimplePersonEntity> = emptyList()) :
        PersonsUiState
    data class Error(val message: String) : PersonsUiState
    object Loading : PersonsUiState
}