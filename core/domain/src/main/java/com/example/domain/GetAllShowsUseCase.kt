package com.example.domain

import com.example.data.RequestStatus
import com.example.data.mapperStatus
import com.example.data.model.Shows
import com.example.data.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    operator fun invoke(numberPage: Int): Flow<RequestStatus<List<ShowsUI>>> {
        return showRepository.getListShow(numberPage = numberPage)
            .map { requestResult ->
                requestResult.mapperStatus { shows ->
                    shows.map { it.toUiShows() }
                }
            }
    }
}


// класс для отображения на UI слое
public data class ShowsUI(
    val id: Int,
    val name: String,
    val ended: String,
    val genres: List<String>,
    val image: ImageShow,
    val language: String,
    val network: NetworkShow,
    val officialSite: String,
    val premiered: String,
    val rating: RatingShow,
    val status: String,
    val summary: String,
    val url: String,
) {
    data class ImageShow(
        val medium: String,
        val original: String,
    )

    data class NetworkShow(
        val country: CountryShow,
        val id: Int,
        val name: String,
        val officialSite: String
    )

    data class CountryShow(
        val code: String,
        val name: String,
        val timezone: String
    )

    data class RatingShow(
        val average: Double,
    )
}

private fun Shows.toUiShows(): ShowsUI {
    return ShowsUI(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = ShowsUI.ImageShow(
            medium = image.medium,
            original = image.original
        ),
        language = language,
        network = ShowsUI.NetworkShow(
            country = ShowsUI.CountryShow(
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
        rating = ShowsUI.RatingShow(average = rating.average),
        status = status,
        summary = summary,
        url = url,
    )
}