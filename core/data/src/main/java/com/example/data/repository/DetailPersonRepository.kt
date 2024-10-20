package com.example.data.repository

import com.example.data.RequestStatus
import com.example.data.mapper.toPerson
import com.example.data.mapperStatus
import com.example.data.model.PersonShowEntity
import com.example.data.toRequestStatus
import com.example.network.KtorClient
import com.example.network.model.RemotePersonShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailPersonRepository @Inject constructor(
    private val ktorClient: KtorClient
) {

    fun getPersonDetails(idPerson: Int): Flow<RequestStatus<PersonShowEntity>> {
        val apiRequest: Flow<RequestStatus<RemotePersonShow>> =
            flow { emit(ktorClient.getPersonInfo(personId = idPerson)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { remotePerson ->
                remotePerson.toPerson()
            }
        }
    }
}