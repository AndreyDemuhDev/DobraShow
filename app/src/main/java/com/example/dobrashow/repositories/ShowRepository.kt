package com.example.dobrashow.repositories

import com.example.network.ApiStatus
import com.example.network.KtorClient
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import com.example.network.models.domain.DomainShowEntity
import javax.inject.Inject


class ShowRepository @Inject constructor(
    private val ktorClient: KtorClient
) {
    suspend fun getShowInformation(showId: Int): ApiStatus<DomainShowEntity> {
        return ktorClient.getShow(id = showId)
    }

    suspend fun getCast(showId: Int): ApiStatus<List<DomainCastEntity>> {
        return ktorClient.getCastShow(id = showId)
    }

    suspend fun getCrew(showId: Int): ApiStatus<List<DomainCrewEntity>> {
        return ktorClient.getCrewShow(id = showId)
    }
}