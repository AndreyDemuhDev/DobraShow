package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePersonShow(
    @SerialName("id") val id: Int,
    @SerialName("birthday") val birthday: String?,
    @SerialName("country") val country: RemoteCountryPerson? = null,
    @SerialName("deathday") val deathday: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("image") val image: RemoteImagePerson? = null,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)

@Serializable
data class RemoteCharacterShow(
    @SerialName("id") val id: Int,
    @SerialName("image") val image: RemoteImagePerson? = null,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class RemoteCountryPerson(
    @SerialName("name") val name: String,
)

@Serializable
data class RemoteImagePerson(
    @SerialName("medium") val medium: String,
    @SerialName("original") val original: String
)

