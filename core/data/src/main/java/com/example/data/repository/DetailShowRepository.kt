package com.example.data.repository

import com.example.data.RequestStatus
import com.example.data.mapper.toCastShow
import com.example.data.mapper.toCrewShow
import com.example.data.mapper.toSeasonsShow
import com.example.data.mapper.toShow
import com.example.data.mapperStatus
import com.example.data.model.CastShowEntity
import com.example.data.model.CrewShowEntity
import com.example.data.model.SeasonsShowEntity
import com.example.data.model.ShowsEntity
import com.example.data.toRequestStatus
import com.example.network.KtorClient
import com.example.network.model.RemoteCastModel
import com.example.network.model.RemoteCrewModel
import com.example.network.model.RemoteSeasonsModel
import com.example.network.model.RemoteShowModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailShowRepository @Inject constructor(
    private val ktorClient: KtorClient,
) {


    fun getShow(showId: Int): Flow<RequestStatus<ShowsEntity>> {
        val apiRequest: Flow<RequestStatus<RemoteShowModel>> =
            flow { emit(ktorClient.getShow(id = showId)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.toShow()
            }
        }
    }


    fun getListCastShow(showId: Int): Flow<RequestStatus<List<CastShowEntity>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteCastModel>>> =
            flow { emit(ktorClient.getCastShow(id = showId)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.map {
                    it.toCastShow()
                }
            }
        }
    }

    fun getListCrewShow(showId: Int): Flow<RequestStatus<List<CrewShowEntity>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteCrewModel>>> =
            flow { emit(ktorClient.getCrewShow(id = showId)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.map {
                    it.toCrewShow()
                }
            }
        }
    }

    fun getListSeasonsShow(showId: Int): Flow<RequestStatus<List<SeasonsShowEntity>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteSeasonsModel>>> =
            flow { emit(ktorClient.getListSeasonsShow(id = showId)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.map {
                    it.toSeasonsShow()
                }
            }
        }
    }
}
