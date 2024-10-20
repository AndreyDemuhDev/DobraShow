package com.example.data.model


data class SeasonsShowEntity(
    val id: Int,
    val endDate: String,
    val episodeOrder: Int,
    val image: ImageSeasonsEntity,
    val name: String,
    val number: Int,
    val premiereDate: String,
    val summary: String,
    val url: String,
    val listEpisodes: ListEpisodesEntity
)

data class ImageSeasonsEntity(
    val medium: String,
    val original: String
)


data class ListEpisodesEntity(
    val episodes: List<EpisodeEntity>
)


data class EpisodeEntity(
    val id: Long,
    val url: String,
    val name: String,
    val airdate: String,
    val season: Long,
    val number: Long,
    val runtime: Long,
    val rating: RatingShowEntity,
    val image: ImageShowEntity,
    val summary: String,
)


