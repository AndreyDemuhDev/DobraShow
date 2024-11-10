package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteSearchShowModel(
    @SerialName("show")
    val searchShow: RemoteShowModel
)