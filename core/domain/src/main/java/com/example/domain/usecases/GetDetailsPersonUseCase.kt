package com.example.domain.usecases

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.repository.DetailPersonRepository
import com.example.domain.mapper.toPersonUi
import com.example.domain.model.PersonShowUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsPersonUseCase @Inject constructor(
    private val personRepository: DetailPersonRepository
) {

    fun personInformation(personId: Int): Flow<RequestStatus<PersonShowUi>> {
        return personRepository.getPersonDetails(idPerson = personId)
            .map { result ->
                result.mapperStatus {
                    it.toPersonUi()
                }
            }
    }
}