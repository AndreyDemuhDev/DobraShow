package com.example.data.repository

import com.example.data.RequestStatus
import com.example.data.mapper.toSeasonsShow
import com.example.data.mapperStatus
import com.example.data.model.SeasonsShowEntity
import com.example.data.toRequestStatus
import com.example.network.KtorClient
import com.example.network.model.RemoteSeasonsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailSeasonRepository @Inject constructor(
    private val ktorClient: KtorClient
) {

    fun getSeasonInfo(seasonId: Int): Flow<RequestStatus<SeasonsShowEntity>> {
        val apiRequest: Flow<RequestStatus<RemoteSeasonsModel>> =
            flow { emit(ktorClient.getSeasonInfo(seasonId = seasonId)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { remoteSeason ->
                remoteSeason.toSeasonsShow()
            }
        }
    }
}