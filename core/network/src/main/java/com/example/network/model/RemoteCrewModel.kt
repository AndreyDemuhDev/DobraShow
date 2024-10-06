package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCrewModel(
    @SerialName("person") val person: RemotePersonShow,
    @SerialName("type") val type: String
)
