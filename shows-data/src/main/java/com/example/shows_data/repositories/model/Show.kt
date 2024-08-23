package com.example.shows_data.repositories.model

data class Shows(
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
    val status: ShowStatus,
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

sealed class ShowStatus(val statusName: String) {
    object Running : ShowStatus("Running")
    object Ended : ShowStatus("Ended")
    object Determined : ShowStatus("To Be Determined")
    object Unknown : ShowStatus("Unknown")
}