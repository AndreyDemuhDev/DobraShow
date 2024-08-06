package com.example.network.models.domain


data class DomainSeasonEntity(
    val id: Int,
    val endDate: String,
    val episodeOrder: Int,
    val image: ImageSeasons,
    val name: String,
    val number: Int,
    val premiereDate: String,
    val summary: String,
    val url: String,
){
    data class ImageSeasons(
        val medium: String,
        val original: String
    )
}