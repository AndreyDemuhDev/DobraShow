package com.example.view_show.screens.details_person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RequestStatus
import com.example.domain.model.PersonShowUi
import com.example.domain.usecases.GetDetailsPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val personUseCase: Provider<GetDetailsPersonUseCase>
) : ViewModel() {

    var personDetailState: StateFlow<StatePerson> = personUseCase.get().personInformation(0)
        .map { it.toStatePerson() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            StatePerson.None
        )


    fun getPersonDetails(personId: Int) {
        viewModelScope.launch {
            personDetailState = personUseCase.get().personInformation(personId = personId)
                .map { it.toStatePerson() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    StatePerson.None
                )
        }
    }
}


private fun RequestStatus<PersonShowUi>.toStatePerson(): StatePerson {
    return when (this) {
        is RequestStatus.Error -> StatePerson.Error(data)
        is RequestStatus.InProgress -> StatePerson.Loading(data)
        is RequestStatus.Success -> StatePerson.Success(data)
    }
}

sealed class StatePerson(val person: PersonShowUi?) {
    data object None : StatePerson(person = null)
    class Loading(person: PersonShowUi? = null) : StatePerson(person = person)
    class Error(person: PersonShowUi? = null) : StatePerson(person = person)
    class Success(person: PersonShowUi) : StatePerson(person = person)
}