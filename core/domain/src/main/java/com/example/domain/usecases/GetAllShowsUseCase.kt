package com.example.domain.usecases

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.repository.ShowRepository
import com.example.domain.mapper.toUiShows
import com.example.domain.model.ShowsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    operator fun invoke(numberPage: Int): Flow<RequestStatus<List<ShowsUi>>> {
        return showRepository.getListShow(numberPage = numberPage)
            .map { requestResult ->
                requestResult.mapperStatus { shows ->
                    shows.map { it.toUiShows() }
                }
            }
    }
}
