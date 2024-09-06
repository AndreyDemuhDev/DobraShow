package com.example.shows_data.repositories

import com.example.database.ShowsDatabase
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

    suspend fun getListShow(numberPage: Int): Result<List<DomainShowEntity>> {
        return ktorClient.getListShow(pageNumber = numberPage)
    }

    suspend fun getShowInformation(showId: Int): Result<DomainShowEntity> {
        return ktorClient.getShow(id = showId)
    }

    suspend fun getListCastShow(showId: Int): Result<List<DomainCastEntity>> {
        return ktorClient.getCastShow(id = showId)
    }

    suspend fun getListCrewShow(showId: Int): Result<List<DomainCrewEntity>> {
        return ktorClient.getCrewShow(id = showId)
    }

    suspend fun getListSeasonsShow(showId: Int): Result<List<DomainSeasonEntity>> {
        return ktorClient.getListSeasonsShow(id = showId)
    }

    suspend fun getSeasonInfo(seasonId: Int): Result<DomainSeasonEntity> {
        return ktorClient.getSeasonInfo(seasonId = seasonId)
    }

    suspend fun getInfoPerson(personId: Int): Result<DomainPersonEntity> {
        return ktorClient.getPersonInfo(personId = personId)
    }

    suspend fun getListPersons(pageNumber: Int): Result<List<DomainSimplePersonEntity>> {
        return ktorClient.getListPersons(pageNumber = pageNumber)
    }

    suspend fun searchShow(query: String): Result<List<DomainSearchShowEntity>> {
        return ktorClient.searchShow(query = query)
    }

}


sealed class RequestStatus<E>(internal val data: E?) {
    class InProgress<E>(data: E?) : RequestStatus<E>(data = data)
    class Success<E>(data: E?) : RequestStatus<E>(data = data)
    class Error<E>(data: E?) : RequestStatus<E>(data = data)
}