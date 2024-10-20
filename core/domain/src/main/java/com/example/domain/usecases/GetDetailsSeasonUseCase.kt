package com.example.domain.usecases

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.repository.DetailSeasonRepository
import com.example.domain.mapper.toUiSeason
import com.example.domain.model.SeasonsShowUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsSeasonUseCase @Inject constructor(
    private val seasonRepository: DetailSeasonRepository
) {

    fun getSeasonInfo(seasonId: Int): Flow<RequestStatus<SeasonsShowUi>> {
        return seasonRepository.getSeasonInfo(seasonId = seasonId)
            .map { result ->
                result.mapperStatus { it.toUiSeason() }
            }
    }
}