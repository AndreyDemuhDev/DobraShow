package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCastModel(
    @SerialName("person") val person: RemotePersonShow,
    @SerialName("character") val character: RemoteCharacterShow
)
