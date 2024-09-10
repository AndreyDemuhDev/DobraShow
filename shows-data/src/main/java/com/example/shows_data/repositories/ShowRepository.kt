package com.example.shows_data.repositories

import com.example.database.ShowsDatabase
import com.example.database.models.ShowsDBO
import com.example.network.KtorClient
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainPersonEntity
import com.example.network.models.domain.DomainSearchShowEntity
import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.domain.DomainSimplePersonEntity
import com.example.network.models.remote.RemoteShowModel
import com.example.shows_data.RequestResponseMergeStrategy
import com.example.shows_data.RequestStatus
import com.example.shows_data.map
import com.example.shows_data.mappers.toShow
import com.example.shows_data.mappers.toShowDatabase
import com.example.shows_data.model.Shows
import com.example.shows_data.toRequestStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class ShowRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val database: ShowsDatabase,
    private val requestResponseMergeStrategy: RequestResponseMergeStrategy<List<Shows>>
) {

//    suspend fun getListShow(numberPage: Int): Result<List<DomainShowEntity>> {
//        return ktorClient.getListShow(pageNumber = numberPage)
//    }

    fun getListShow(numberPage: Int): Flow<RequestStatus<List<Shows>>> {
        val cachedAllShow: Flow<RequestStatus<List<Shows>>> = getAllShowsFromDatabase()
            .map { result ->
                result.map { showsDatabase ->
                    showsDatabase.map {
                        it.toShow()
                    }
                }
            }

        val remoteShows: Flow<RequestStatus<List<Shows>>> = getAllShowsFromServer(numberPage)
            .map { result ->
                result.map { showsRemote ->
                    showsRemote.map {
                        it.toShow()
                    }
                }
            }
        return cachedAllShow.combine(remoteShows) { databaseShows: RequestStatus<List<Shows>>, networkShows: RequestStatus<List<Shows>> ->
            requestResponseMergeStrategy.merge(databaseShows, networkShows)
        }
    }

    private fun getAllShowsFromServer(numberPage: Int): Flow<RequestStatus<List<RemoteShowModel>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteShowModel>>> =
            flow { emit(ktorClient.getListShow(pageNumber = numberPage)) }
                .onEach { result: Result<List<RemoteShowModel>> ->
                    if (result.isSuccess) {
                        saveNetworkShowsToDatabase(result.getOrThrow())
                    }
                }
                .map { it.toRequestStatus() }

        val startRequest = flowOf<RequestStatus<List<RemoteShowModel>>>(RequestStatus.InProgress())

        return merge(apiRequest, startRequest)

    }

    private suspend fun saveNetworkShowsToDatabase(data: List<RemoteShowModel>) {
        val networkShows: List<ShowsDBO> = data.map { showsDto: RemoteShowModel -> showsDto.toShowDatabase() }
        database.showsDao.insertShowToDatabase(networkShows)
    }

    private fun getAllShowsFromDatabase(): Flow<RequestStatus<List<ShowsDBO>>> {
        val databaseRequest = database.showsDao
            .getAllListShow()
            .map { RequestStatus.Success(it) }
        val startRequest = flowOf<RequestStatus<List<ShowsDBO>>>(RequestStatus.InProgress())

        return merge(startRequest, databaseRequest)
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




