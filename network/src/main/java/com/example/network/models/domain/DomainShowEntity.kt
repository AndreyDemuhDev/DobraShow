package com.example.network.models.domain

// данные domain
data class DomainShowEntity(
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
