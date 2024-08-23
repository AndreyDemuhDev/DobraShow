package com.example.dobrashow.screens.person_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows_data.repositories.ShowRepository
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
    private val showRepository: com.example.shows_data.repositories.ShowRepository
) : ViewModel() {

    private val _personDetailUiState = MutableStateFlow<PersonDetailUiState>(PersonDetailUiState.Loading)
    val personDetailUiState: StateFlow<PersonDetailUiState> = _personDetailUiState.asStateFlow()


    fun getPeopleInfo(personId: Int) =
        viewModelScope.launch {
            _personDetailUiState.update { return@update PersonDetailUiState.Loading }
            showRepository.getInfoPerson(personId = personId).onSuccess { person ->
                _personDetailUiState.update {
                    return@update PersonDetailUiState.Success(person = person)
                }
            }.onException { exception ->
                _personDetailUiState.update {
                    return@update PersonDetailUiState.Error(
                        message = exception.message ?: "error load info person"
                    )
                }
            }
        }
}


sealed interface PersonDetailUiState {
    data class Success(val person: DomainPersonEntity) : PersonDetailUiState
    data class Error(val message: String) : PersonDetailUiState
    object Loading : PersonDetailUiState
}