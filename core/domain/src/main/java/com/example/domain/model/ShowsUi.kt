package com.example.domain.model


// класс для отображения на UI слое
public data class ShowsUi(
    val id: Int,
    val name: String,
    val ended: String,
    val genres: List<String>,
    val image: ImageShowUi,
    val language: String,
    val network: NetworkShowUi,
    val officialSite: String,
    val premiered: String,
    val rating: RatingShowUi,
    val status: String,
    val summary: String,
    val url: String,
    var isFavorite: Int,
) {
    data class ImageShowUi(
        val medium: String,
        val original: String,
    )

    data class NetworkShowUi(
        val country: CountryShowUi,
        val id: Int,
        val name: String,
        val officialSite: String
    )

    data class CountryShowUi(
        val code: String,
        val name: String,
        val timezone: String
    )

    data class RatingShowUi(
        val average: Double,
    )
}