package com.example.data.model

// "промежуточный" класс данных моделей

data class ShowsEntity(
    val id: Int,
    val name: String,
    val ended: String,
    val genres: List<String>,
    val image: ImageShowEntity,
    val language: String,
    val network: NetworkShowEntity,
    val officialSite: String,
    val premiered: String,
    val rating: RatingShowEntity,
    val status: String,
    val summary: String,
    val url: String,
)

data class ImageShowEntity(
    val medium: String,
    val original: String,
)

data class NetworkShowEntity(
    val country: CountryShowEntity,
    val id: Int,
    val name: String,
    val officialSite: String
)

data class CountryShowEntity(
    val code: String,
    val name: String,
    val timezone: String
)

data class RatingShowEntity(
    val average: Double,
)
