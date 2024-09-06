package com.example.shows_data.repositories

import com.example.database.ShowsDatabase
import com.example.network.ApiStatus
import com.example.network.KtorClient
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainPersonEntity
import com.example.network.models.domain.DomainSearchShowEntity
import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.domain.DomainSimplePersonEntity
import javax.inject.Inject


class ShowRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val database: ShowsDatabase,
) {

    suspend fun getListShow(numberPage: Int): ApiStatus<List<DomainShowEntity>> {
        return ktorClient.getListShow(pageNumber = numberPage)
    }

    suspend fun getShowInformation(showId: Int): ApiStatus<DomainShowEntity> {
        return ktorClient.getShow(id = showId)
    }

    suspend fun getListCastShow(showId: Int): ApiStatus<List<DomainCastEntity>> {
        return ktorClient.getCastShow(id = showId)
    }

    suspend fun getListCrewShow(showId: Int): ApiStatus<List<DomainCrewEntity>> {
        return ktorClient.getCrewShow(id = showId)
    }

    suspend fun getListSeasonsShow(showId: Int): ApiStatus<List<DomainSeasonEntity>> {
        return ktorClient.getListSeasonsShow(id = showId)
    }

    suspend fun getSeasonInfo(seasonId: Int): ApiStatus<DomainSeasonEntity> {
        return ktorClient.getSeasonInfo(seasonId = seasonId)
    }

    suspend fun getInfoPerson(personId: Int): ApiStatus<DomainPersonEntity> {
        return ktorClient.getPersonInfo(personId = personId)
    }

    suspend fun getListPersons(pageNumber: Int): ApiStatus<List<DomainSimplePersonEntity>> {
        return ktorClient.getListPersons(pageNumber = pageNumber)
    }

    suspend fun searchShow(query: String): ApiStatus<List<DomainSearchShowEntity>> {
        return ktorClient.searchShow(query = query)
    }

}


sealed class RequestStatus<E>(internal val data: E?) {
    class InProgress<E>(data: E?) : RequestStatus<E>(data = data)
    class Success<E>(data: E?) : RequestStatus<E>(data = data)
    class Error<E>(data: E?) : RequestStatus<E>(data = data)
}