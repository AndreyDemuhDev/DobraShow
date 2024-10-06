package com.example.network.models.remote

import com.example.network.models.domain.DomainSeasonEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//
//@Serializable
//data class RemoteSeasonsModel(
//    @SerialName("id") val id: Int,
//    @SerialName("endDate") val endDate: String? = "unknown end date season",
//    @SerialName("episodeOrder") val episodeOrder: Int? = -1,
//    @SerialName("image") val image: ImageSeasons? = null,
//    @SerialName("name") val name: String? = "unknown name season",
//    @SerialName("number") val number: Int? = -1,
//    @SerialName("premiereDate") val premiereDate: String? = "unknown premiere date season",
//    @SerialName("summary") val summary: String? = "empty summary season",
//    @SerialName("url") val url: String? = "unknown url season",
//    @SerialName("_embedded") val listEpisodes: RemoteEmbedded? = null
//) {
//    @Serializable
//    data class ImageSeasons(
//        @SerialName("medium") val medium: String?,
//        @SerialName("original") val original: String?
//    )
//
//    @Serializable
//    data class RemoteEmbedded(
//        @SerialName("episodes") val episodes: List<RemoteEpisode>
//    )
//
//    @Serializable
//    data class RemoteEpisode(
//        @SerialName("id") val id: Long?,
//        @SerialName("url") val url: String?,
//        @SerialName("name") val name: String?,
//        @SerialName("airdate") val airdate: String?,
//        @SerialName("season") val season: Long?,
//        @SerialName("number") val number: Long? = null,
//        @SerialName("runtime") val runtime: Long?,
//        @SerialName("rating") val rating: RemoteShowModel.RatingShow? = null,
//        @SerialName("image") val image: RemoteShowModel.ImageShow? = null,
//        @SerialName("summary") val summary: String?,
//    )
//}

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
