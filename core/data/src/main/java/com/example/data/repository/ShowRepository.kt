package com.example.data.repository

import com.example.data.DefaultRequestResponseMergeStrategy
import com.example.data.MergeStrategy
import com.example.data.RequestStatus
import com.example.data.mapper.toShow
import com.example.data.mapper.toShowDatabase
import com.example.data.mapperStatus
import com.example.data.model.ShowsEntity
import com.example.data.toRequestStatus
import com.example.database.ShowsDatabase
import com.example.database.model.ShowsDBO
import com.example.network.KtorClient
import com.example.network.model.RemoteShowModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@Suppress("TooManyFunctions")
class ShowRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val database: ShowsDatabase,
) {
//
//    suspend fun getListShow(numberPage: Int): Result<List<DomainShowEntity>> {
//        return ktorClient.getListShow(pageNumber = numberPage)
//    }

    // функция которая отображает нам списко сериалов
    fun getListShow(
        numberPage: Int,
        mergeStrategy: MergeStrategy<RequestStatus<List<ShowsEntity>>> = DefaultRequestResponseMergeStrategy()
    ): Flow<RequestStatus<List<ShowsEntity>>> {
        // переменная которая предоставляет сериалы из базы данных
        val cachedAllShow: Flow<RequestStatus<List<ShowsEntity>>> = getAllShowsFromDatabase()

        // переменная которая предоставляет сериалы из сети
        val remoteShowsEntity: Flow<RequestStatus<List<ShowsEntity>>> = getAllShowsFromServer(numberPage)

        // возвращаем результаты которые мы получили в результате статуса получения данных из сети и базы данных
        // результат зависит от работы функции merge интерфейса MergeStrategy
        return cachedAllShow.combine(remoteShowsEntity, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestStatus.Success) {
                    database.showsDao.getObservableListShow()
                        .map { databaseShow -> databaseShow.map { it.toShow() } }
                        .map { RequestStatus.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    // функция которая предоставляет сериалы из сети
    private fun getAllShowsFromServer(numberPage: Int): Flow<RequestStatus<List<ShowsEntity>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteShowModel>>> =
            flow { emit(ktorClient.getListShow(pageNumber = numberPage)) }
                .onEach { result: Result<List<RemoteShowModel>> ->
                    if (result.isSuccess) {
                        saveNetworkShowsToDatabase(result.getOrThrow())
                    }
                }
                .map { it.toRequestStatus() }

        val startRequest = flowOf<RequestStatus<List<RemoteShowModel>>>(RequestStatus.InProgress())

        return merge(apiRequest, startRequest).map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.map {
                    it.toShow()
                }
            }
        }
    }

    // функция которая сохраняет сериалы из сети в базу данных
    private suspend fun saveNetworkShowsToDatabase(data: List<RemoteShowModel>) {
        val networkShows: List<ShowsDBO> =
            data.map { showsDto: RemoteShowModel -> showsDto.toShowDatabase() }
        database.showsDao.insertShowToDatabase(networkShows)
    }

    // функция которая предоставляет сериалы из базы данных
    private fun getAllShowsFromDatabase(): Flow<RequestStatus<List<ShowsEntity>>> {
        val databaseRequest = database.showsDao::getAllListShow.asFlow()
            .map<List<ShowsDBO>, RequestStatus<List<ShowsDBO>>> { RequestStatus.Success(it) }
        val startRequest = flowOf<RequestStatus<List<ShowsDBO>>>(RequestStatus.InProgress())

        return merge(startRequest, databaseRequest)
            .map { result ->
                result.mapperStatus { showsDatabase ->
                    showsDatabase.map { it.toShow() }
                }
            }
    }

//    suspend fun getSeasonInfo(seasonId: Int): Result<DomainSeasonEntity> {
//        return ktorClient.getSeasonInfo(seasonId = seasonId)
//    }
//
//    suspend fun getInfoPerson(personId: Int): Result<DomainPersonEntity> {
//        return ktorClient.getPersonInfo(personId = personId)
//    }
//
//    suspend fun getListPersons(pageNumber: Int): Result<List<DomainSimplePersonEntity>> {
//        return ktorClient.getListPersons(pageNumber = pageNumber)
//    }
//
//    suspend fun searchShow(query: String): Result<List<DomainSearchShowEntity>> {
//        return ktorClient.searchShow(query = query)
//    }
}
