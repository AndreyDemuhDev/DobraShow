package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSeasonsModel(
    @SerialName("id") val id: Int,
    @SerialName("endDate") val endDate: String? = "unknown end date season",
    @SerialName("episodeOrder") val episodeOrder: Int? = -1,
    @SerialName("image") val image: RemoteImageSeasons? = null,
    @SerialName("name") val name: String? = "unknown name season",
    @SerialName("number") val number: Int? = -1,
    @SerialName("premiereDate") val premiereDate: String? = "unknown premiere date season",
    @SerialName("summary") val summary: String? = "empty summary season",
    @SerialName("url") val url: String? = "unknown url season",
    @SerialName("_embedded") val listEpisodes: RemoteEmbedded? = null
)

@Serializable
data class RemoteImageSeasons(
    @SerialName("medium") val medium: String?,
    @SerialName("original") val original: String?
)

@Serializable
data class RemoteEmbedded(
    @SerialName("episodes") val episodes: List<RemoteEpisode>
)


@Serializable
data class RemoteEpisode(
    @SerialName("id") val id: Long?,
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
    @SerialName("airdate") val airdate: String?,
    @SerialName("season") val season: Long?,
    @SerialName("number") val number: Long? = null,
    @SerialName("runtime") val runtime: Long?,
    @SerialName("rating") val rating: RemoteRatingShow? = null,
    @SerialName("image") val image: RemoteImageShow? = null,
    @SerialName("summary") val summary: String?,
)

