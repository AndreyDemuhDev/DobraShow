package com.example.shows_data.mappers

import com.example.database.models.ShowsDBO
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.remote.RemoteShowModel
import com.example.shows_data.model.Shows

internal fun ShowsDBO.toShow(): Shows {
    TODO("Need implementation")
}

internal fun RemoteShowModel.toShow(): Shows {
    TODO("Need implementation")
}

internal fun RemoteShowModel.toShowDatabase(): ShowsDBO {
    TODO("Need implementation")
}