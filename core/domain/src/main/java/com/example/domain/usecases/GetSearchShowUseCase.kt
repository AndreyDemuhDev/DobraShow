package com.example.domain.usecases

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.repository.SearchShowRepository
import com.example.domain.mapper.toSearchShowUi
import com.example.domain.mapper.toUiShows
import com.example.domain.model.ShowsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchShowUseCase @Inject constructor(
    private val searchShowRepository: SearchShowRepository
) {

    fun searchShow(query: String): Flow<RequestStatus<List<ShowsUi>>> {
        return searchShowRepository.searchShow(query = query)
            .map { result ->
                result.mapperStatus { listShows ->
                    listShows.map { show ->
                        show.toSearchShowUi()
                    }
                }
            }
    }
}
