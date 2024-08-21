package com.example.network.models.remote

import com.example.network.models.domain.DomainSearchShowEntity
import kotlinx.serialization.*

@Serializable
data class RemoteSearchShowModel(
    @SerialName("show") val searchShows: RemoteShowModel? = null
)

fun RemoteSearchShowModel.toDomainSearchShowEntity(): DomainSearchShowEntity {
    return DomainSearchShowEntity(searchShows = searchShows?.toDomainShow())
}