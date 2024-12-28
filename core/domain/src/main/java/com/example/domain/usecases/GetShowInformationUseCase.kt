package com.example.domain.usecases

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.model.ShowEntity
import com.example.data.repository.DetailShowRepository
import com.example.data.repository.ShowRepository
import com.example.domain.mapper.toUiCast
import com.example.domain.mapper.toUiCrew
import com.example.domain.mapper.toUiSeason
import com.example.domain.mapper.toUiShows
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.SeasonsShowUi
import com.example.domain.model.ShowsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShowInformationUseCase @Inject constructor(
    private val detailShowRepository: DetailShowRepository
) {

    fun showInformation(showId: Int): Flow<RequestStatus<ShowsUi>> {
        return detailShowRepository.getShow(showId = showId)
            .map { requestResult ->
                requestResult.mapperStatus { it.toUiShows() }
            }

    }

    fun showCrewList(showId: Int): Flow<RequestStatus<List<CrewShowUi>>> {
        return detailShowRepository.getListCrewShow(showId = showId)
            .map { result ->
                result.mapperStatus { crewRemote ->
                    crewRemote.map {
                        it.toUiCrew()
                    }
                }
            }
    }

    fun showCastList(showId: Int): Flow<RequestStatus<List<CastShowUi>>> {
        return detailShowRepository.getListCastShow(showId = showId)
            .map { result ->
                result.mapperStatus { crewRemote ->
                    crewRemote.map {
                        it.toUiCast()
                    }
                }
            }
    }

    fun showSeasonsList(showId: Int): Flow<RequestStatus<List<SeasonsShowUi>>> {
        return detailShowRepository.getListSeasonsShow(showId = showId)
            .map { result ->
                result.mapperStatus { seasonsRemote ->
                    seasonsRemote.map {
                        it.toUiSeason()
                    }
                }
            }
    }

    suspend fun addShowToFavorite(show: ShowEntity){
        detailShowRepository.insertShowToFavorite(show = show)
    }

}
