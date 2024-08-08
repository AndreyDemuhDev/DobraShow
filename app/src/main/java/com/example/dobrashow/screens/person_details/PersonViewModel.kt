package com.example.dobrashow.screens.person_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dobrashow.repositories.ShowRepository
import com.example.network.models.domain.DomainPersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _personUiState = MutableStateFlow<PersonUiState>(PersonUiState.Loading)
    val personUiState: StateFlow<PersonUiState> = _personUiState.asStateFlow()


    fun getPeopleInfo(personId: Int) =
        viewModelScope.launch {
            _personUiState.update { return@update PersonUiState.Loading }
            showRepository.getInfoPerson(personId = personId).onSuccess { person ->
                _personUiState.update {
                    return@update PersonUiState.Success(person = person)
                }
            }.onException { exception ->
                _personUiState.update {
                    return@update PersonUiState.Error(
                        message = exception.message ?: "error load info person"
                    )
                }
            }
        }
}


sealed interface PersonUiState {
    data class Success(val person: DomainPersonEntity) : PersonUiState
    data class Error(val message: String) : PersonUiState
    object Loading : PersonUiState
}