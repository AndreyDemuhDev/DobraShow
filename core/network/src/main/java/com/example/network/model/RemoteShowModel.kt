package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// данные remote мы получаем из сети (dto)

@Serializable
data class RemoteShowModel(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String? = "unknown name",
    @SerialName("ended") val ended: String? = "unknown ended",
    @SerialName("genres") val genres: List<String>? = emptyList(),
    @SerialName("image") val image: RemoteImageShow? = null,
    @SerialName("language") val language: String? = "unknown language",
    @SerialName("network") val network: RemoteNetworkShow? = null,
    @SerialName("officialSite") val officialSite: String? = "unknown official site",
    @SerialName("premiered") val premiered: String? = "unknown premiered",
    @SerialName("rating") val rating: RemoteRatingShow? = null,
    @SerialName("status") val status: String? = "unknown status",
    @SerialName("summary") val summary: String? = "unknown summary",
    @SerialName("url") val url: String? = "unknown url",
)

@Serializable
data class RemoteImageShow(
    @SerialName("medium") val medium: String?,
    @SerialName("original") val original: String?,
)

@Serializable
data class RemoteNetworkShow(
    @SerialName("country") val country: RemoteCountryShow? = null,
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("officialSite") val officialSite: String?
)

@Serializable
data class RemoteCountryShow(
    @SerialName("code") val code: String?,
    @SerialName("name") val name: String?,
    @SerialName("timezone") val timezone: String?
)

@Serializable
data class RemoteRatingShow(
    @SerialName("average") val average: Double? = 0.0,
)

