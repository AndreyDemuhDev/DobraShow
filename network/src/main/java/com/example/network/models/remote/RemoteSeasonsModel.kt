package com.example.network.models.remote

import com.example.network.models.domain.DomainSeasonEntity
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSeasonsModel(
    val id: Int,
    val endDate: String? = "unknown end date season",
    val episodeOrder: Int? = -1,
    val image: ImageSeasons? = null,
    val name: String? = "unknown name season",
    val number: Int,
    val premiereDate: String? = "unknown premiere date season",
    val summary: String? = "empty summary season",
    val url: String? = "unknown url season",
) {
    @Serializable
    data class ImageSeasons(
        val medium: String,
        val original: String
    )
}

fun RemoteSeasonsModel.toDomainSeason(): DomainSeasonEntity {
    return DomainSeasonEntity(
        id = id,
        endDate = endDate ?: "unknown end date season",
        episodeOrder = episodeOrder ?: -1,
        image = DomainSeasonEntity.ImageSeasons(
            medium = image?.medium ?: "empty medium image season",
            original = image?.original ?: "empty original image season"
        ),
        name = name ?: "unknown name season",
        number = number,
        premiereDate = premiereDate ?: "unknown premiere date season",
        summary = summary ?: "unknown summary season",
        url = url ?: "unknown url season"
    )
}

