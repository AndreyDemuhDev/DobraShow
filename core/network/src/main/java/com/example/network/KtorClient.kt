package com.example.network

import com.example.network.model.RemoteCastModel
import com.example.network.model.RemoteCrewModel
import com.example.network.model.RemoteSeasonsModel
import com.example.network.model.RemoteShowModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class KtorClient {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(OkHttp) {

        defaultRequest { url("https://api.tvmaze.com/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }
//    private var showCache = mutableMapOf<Int, DomainShowEntity>()
//
    suspend fun getShow(id: Int): Result<RemoteShowModel> {
        return safeApiCall {
            client.get("shows/$id")
                .body<RemoteShowModel>()
        }
    }

    suspend fun getCastShow(id: Int): Result<List<RemoteCastModel>> {
        return safeApiCall {
            client.get("shows/$id/cast")
                .body<List<RemoteCastModel>>()
        }
    }

    suspend fun getCrewShow(id: Int): Result<List<RemoteCrewModel>> {
        return safeApiCall {
            client.get("shows/$id/crew")
                .body<List<RemoteCrewModel>>()
        }
    }

    suspend fun getListSeasonsShow(id: Int): Result<List<RemoteSeasonsModel>> {
        return safeApiCall {
            client.get("shows/$id/seasons")
                .body<List<RemoteSeasonsModel>>()
        }
    }

    suspend fun getListShow(pageNumber: Int): Result<List<RemoteShowModel>> {
        return safeApiCall {
            client.get("shows?page=$pageNumber")
                .body<List<RemoteShowModel>>()
        }
    }
//
//    suspend fun getSeasonInfo(seasonId: Int): Result<DomainSeasonEntity> {
//        return safeApiCall {
//            client.get("seasons/$seasonId?embed=episodes")
//                .body<RemoteSeasonsModel>()
//                .toDomainSeason()
//        }
//    }
//
//    suspend fun getPersonInfo(personId: Int): Result<DomainPersonEntity> {
//        return safeApiCall {
//            client.get("people/$personId?embed[]=crewcredits&embed[]=castcredits")
//                .body<RemotePersonModel>()
//                .toDomainPerson()
//        }
//    }
//
//    suspend fun getListPersons(pageNumber: Int): Result<List<DomainSimplePersonEntity>> {
//        return safeApiCall {
//            client.get("people?page=$pageNumber")
//                .body<List<RemoteSimplePersonModel>>()
//                .map { it.toDomainSimplePerson() }
//        }
//    }
//
//    suspend fun searchShow(query: String): Result<List<DomainSearchShowEntity>> {
//        return safeApiCall {
//            client.get("search/shows?q=$query")
//                .body<List<RemoteSearchShowModel>>()
//                .map { it.toDomainSearchShowEntity() }
//        }
//    }
}

// функция для обработки данных получаемых из сети
private inline fun <T> safeApiCall(apiCall: () -> T): Result<T> {
    return try {
        Result.success(value = apiCall())
    } catch (exception: Exception) {
        Result.failure(exception = exception)
    }
}