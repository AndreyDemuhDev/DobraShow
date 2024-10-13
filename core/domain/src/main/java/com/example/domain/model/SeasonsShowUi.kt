package com.example.domain.model


data class SeasonsShowUi(
    val id: Int,
    val endDate: String,
    val episodeOrder: Int,
    val image: ImageSeasonUI,
    val name: String,
    val number: Int,
    val premiereDate: String,
    val summary: String,
    val url: String,
    val listEpisodes: SeasonEpisodesUI
)

data class ImageSeasonUI(
    val medium: String,
    val original: String
)

data class SeasonEpisodesUI(
    val episodes: List<EpisodeUI>
)

data class EpisodeUI(
    val id: Long,
    val url: String,
    val name: String,
    val airdate: String,
    val season: Long,
    val number: Long,
    val runtime: Long,
    val rating: RatingUI,
    val image: ImageSeasonUI,
    val summary: String,
)

data class RatingUI(
    val average: Double,
)