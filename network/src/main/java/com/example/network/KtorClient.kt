package com.example.network

import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.remote.RemoteShowModel
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
import kotlinx.serialization.Serializable
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

    suspend fun getShow(id: Int): DomainShowEntity {
        return client.get("shows/$id")
            .body<RemoteShowModel>()
            .toDomainShow()
    }


}

@Serializable
data class Show(
    val id: Int,
    val name: String,
    val genres: List<String>
)