package com.example.view_show

import com.example.shows_data.RequestStatus
import com.example.shows_data.mapperStatus
import com.example.shows_data.model.Shows
import com.example.shows_data.repositories.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllShowsUseCase(private val showRepository: ShowRepository) {

    operator fun invoke(): Flow<RequestStatus<List<Shows>>> {
        return showRepository.getListShow(0)
            .map { requestResult ->
                requestResult.mapperStatus { shows ->
                    shows.map { it.toUiShows() }
                }
            }
    }
}

private fun Shows.toUiShows(): Shows {
    TODO("need implementation")
}