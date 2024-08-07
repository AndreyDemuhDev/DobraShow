package com.example.network.models.remote

import com.example.network.models.domain.DomainSeasonEntity
import com.example.network.models.domain.DomainShowEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSeasonsModel(
    val id: Int,
    val endDate: String? = "unknown end date season",
    val episodeOrder: Int? = -1,
    val image: ImageSeasons? = null,
    val name: String? = "unknown name season",
    val number: Int? = -1,
    val premiereDate: String? = "unknown premiere date season",
    val summary: String? = "empty summary season",
    val url: String? = "unknown url season",
    @SerialName("_embedded")
    val listEpisodes: RemoteEmbedded? = null
) {
    @Serializable
    data class ImageSeasons(
        val medium: String?,
        val original: String?
    )

    @Serializable
    data class RemoteEmbedded(
        val episodes: List<RemoteEpisode>
    )

    @Serializable
    data class RemoteEpisode(
        val id: Long?,
        val url: String?,
        val name: String?,
        val airdate: String?,
        val season: Long?,
        val number: Long? = null,
        val runtime: Long?,
        val rating: RemoteShowModel.RatingShow? = null,
        val image: RemoteShowModel.ImageShow? = null,
        val summary: String?,
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
        number = number ?: -1,
        premiereDate = premiereDate ?: "unknown premiere date season",
        summary = summary ?: "",
        url = url ?: "unknown url season",
        listEpisodes = DomainSeasonEntity.DomainEmbedded(
            episodes = listEpisodes?.episodes?.map { it.toDomainEpisode() } ?: emptyList()
        )
    )
}


fun RemoteSeasonsModel.RemoteEpisode.toDomainEpisode(): DomainSeasonEntity.DomainEpisode {
    return DomainSeasonEntity.DomainEpisode(
        id = id ?: -1,
        url = url ?: "unknown url episode",
        name = name ?: "unknown name episode",
        airdate = airdate ?: "",
        season = season ?: -1,
        number = number,
        runtime = runtime ?: -1,
        rating = RemoteShowModel.RatingShow(),
        image = RemoteShowModel.ImageShow(
            medium = image?.medium ?: "unknown image medium episode",
            original = image?.original ?: "unknown image originas episode"
        ),
        summary = summary ?: "unknown summary episode"
    )
}

