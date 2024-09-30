package com.example.view_show
//
//import com.example.shows_data.RequestStatus
//import com.example.shows_data.mapperStatus
//import com.example.shows_data.model.Shows
//import com.example.shows_data.repositories.ShowRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//
//class GetAllShowsUseCase @Inject constructor(
//    private val showRepository: ShowRepository,
//) {
//
//    operator fun invoke(numberPage: Int): Flow<RequestStatus<List<ShowsUI>>> {
//        return showRepository.getListShow(numberPage = numberPage)
//            .map { requestResult ->
//                requestResult.mapperStatus { shows ->
//                    shows.map { it.toUiShows() }
//                }
//            }
//    }
//}
//
//private fun Shows.toUiShows(): ShowsUI {
//    return ShowsUI(
//        id = id,
//        name = name,
//        ended = ended,
//        genres = genres,
//        image = ShowsUI.ImageShow(
//            medium = image.medium,
//            original = image.original
//        ),
//        language = language,
//        network = ShowsUI.NetworkShow(
//            country = ShowsUI.CountryShow(
//                code = network.country.code,
//                name = network.country.name,
//                timezone = network.country.timezone
//            ),
//            id = network.id,
//            name = network.name,
//            officialSite = network.officialSite
//        ),
//        officialSite = officialSite,
//        premiered = premiered,
//        rating = ShowsUI.RatingShow(average = rating.average),
//        status = status,
//        summary = summary,
//        url = url,
//    )
//}
