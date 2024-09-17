package com.example.view_show

import com.example.shows_data.RequestStatus
import com.example.shows_data.mapperStatus
import com.example.shows_data.model.ShowsUi
import com.example.shows_data.repositories.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository,
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

private fun ShowsUi.toUiShows(): ShowsUi {
    return ShowsUi(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = ShowsUi.ImageShow(
            medium = image.medium,
            original = image.original
        ),
        language = language,
        network = ShowsUi.NetworkShow(
            country = ShowsUi.CountryShow(
                code = network.country.code,
                name = network.country.name,
                timezone = network.country.timezone
            ),
            id = network.id,
            name = network.name,
            officialSite = network.officialSite
        ),
        officialSite = officialSite,
        premiered = premiered,
        rating = ShowsUi.RatingShow(average = rating.average),
        status = status,
        summary = summary,
        url = url,
    )
}