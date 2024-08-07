package com.example.network

import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.remote.RemoteCastModel
import com.example.network.models.remote.RemoteCrewModel
import com.example.network.models.remote.RemoteSeasonsModel
import com.example.network.models.remote.RemoteShowModel
import com.example.network.models.remote.toDomainCast
import com.example.network.models.remote.toDomainCrew
import com.example.network.models.remote.toDomainSeason
import com.example.network.models.remote.toDomainShow
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
import kotlinx.serialization.json.Json

class KtorClient() {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://api.tvmaze.com/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
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

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiStatus<T> {
        return try {
            ApiStatus.SuccessStatus(data = apiCall())
        } catch (exception: Exception) {
            ApiStatus.ExceptionStatus(exception = exception)
        }
    }
}



