package com.example.network

import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainPersonEntity
import com.example.network.models.domain.DomainSearchShowEntity
import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.domain.DomainSimplePersonEntity
import com.example.network.models.remote.RemoteCastModel
import com.example.network.models.remote.RemoteCrewModel
import com.example.network.models.remote.RemotePersonModel
import com.example.network.models.remote.RemoteSearchShowModel
import com.example.network.models.remote.RemoteSeasonsModel
import com.example.network.models.remote.RemoteShowModel
import com.example.network.models.remote.RemoteSimplePersonElement
import com.example.network.models.remote.toDomainCast
import com.example.network.models.remote.toDomainCrew
import com.example.network.models.remote.toDomainPerson
import com.example.network.models.remote.toDomainSearchShowEntity
import com.example.network.models.remote.toDomainSeason
import com.example.network.models.remote.toDomainShow
import com.example.network.models.remote.toDomainSimplePerson
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
import kotlinx.serialization.serializer

class KtorClient() {
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
    private var showCache = mutableMapOf<Int, DomainShowEntity>()

    suspend fun getShow(id: Int): ApiStatus<DomainShowEntity> {
        showCache[id]?.let { return ApiStatus.SuccessStatus(it) }
        return safeApiCall {
            client.get("shows/$id")
                .body<RemoteShowModel>()
                .toDomainShow()
                .also { showCache[id] = it }
        }
    }

    suspend fun getCastShow(id: Int): ApiStatus<List<DomainCastEntity>> {
        return safeApiCall {
            client.get("shows/$id/cast")
                .body<List<RemoteCastModel>>()
                .map { it.toDomainCast() }
        }
    }

    suspend fun getCrewShow(id: Int): ApiStatus<List<DomainCrewEntity>> {
        return safeApiCall {
            client.get("shows/$id/crew")
                .body<List<RemoteCrewModel>>()
                .map { it.toDomainCrew() }
        }
    }

    suspend fun getListSeasonsShow(id: Int): ApiStatus<List<DomainSeasonEntity>> {
        return safeApiCall {
            client.get("shows/$id/seasons")
                .body<List<RemoteSeasonsModel>>()
                .map { it.toDomainSeason() }
        }
    }

    suspend fun getListShow(pageNumber: Int): ApiStatus<List<DomainShowEntity>> {
        return safeApiCall {
            client.get("shows?page=$pageNumber")
                .body<List<RemoteShowModel>>()
                .map { it.toDomainShow() }
        }
    }

    suspend fun getSeasonInfo(seasonId: Int): ApiStatus<DomainSeasonEntity> {
        return safeApiCall {
            client.get("seasons/$seasonId?embed=episodes")
                .body<RemoteSeasonsModel>()
                .toDomainSeason()
        }
    }

    suspend fun getPersonInfo(personId: Int): ApiStatus<DomainPersonEntity> {
        return safeApiCall {
            client.get("people/$personId?embed[]=crewcredits&embed[]=castcredits")
                .body<RemotePersonModel>()
                .toDomainPerson()
        }
    }

    suspend fun getListPersons(pageNumber: Int): ApiStatus<List<DomainSimplePersonEntity>> {
        return safeApiCall {
            client.get("people?page=$pageNumber")
                .body<List<RemoteSimplePersonElement>>()
                .map { it.toDomainSimplePerson() }
        }
    }

    suspend fun searchShow(query: String): ApiStatus<List<DomainSearchShowEntity>> {
        return safeApiCall {
            client.get("search/shows?q=$query")
                .body<List<RemoteSearchShowModel>>()
                .map { it.toDomainSearchShowEntity() }
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiStatus<T> {
        return try {
            ApiStatus.SuccessStatus(data = apiCall())
        } catch (exception: Exception) {
            ApiStatus.ExceptionStatus(exception = exception)
        }
    }
}



