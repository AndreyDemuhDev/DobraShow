package com.example.data.repository

import com.example.data.RequestStatus
import com.example.data.mapper.toSearchShow
import com.example.data.mapper.toShow
import com.example.data.mapperStatus
import com.example.data.model.SearchShowEntity
import com.example.data.model.ShowEntity
import com.example.data.toRequestStatus
import com.example.network.KtorClient
import com.example.network.model.RemoteSearchShowModel
import com.example.network.model.RemoteShowModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchShowRepository @Inject constructor(
    private val ktorClient: KtorClient
) {

    fun searchShow(query: String): Flow<RequestStatus<List<SearchShowEntity>>> {
        val apiRequest: Flow<RequestStatus<List<RemoteSearchShowModel>>> =
            flow { emit(ktorClient.searchShow(query = query)) }
                .map { it.toRequestStatus() }

        return apiRequest.map { result ->
            result.mapperStatus { showsRemote ->
                showsRemote.map { it.toSearchShow() }
            }
        }
    }
}
