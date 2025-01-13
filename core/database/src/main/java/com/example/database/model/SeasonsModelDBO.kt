package com.example.database.model


data class SeasonsModelDBO(
   val id: Int,
    val endDate: String? = "unknown end date season",
    val episodeOrder: Int? = -1,
    val image: ImageSeasonsDBO? = null,
    val name: String? = "unknown name season",
    val number: Int? = -1,
    val premiereDate: String? = "unknown premiere date season",
    val summary: String? = "empty summary season",
    val url: String? = "unknown url season",
    val listEpisodes: ListEpisodesDBO? = null
)


data class ImageSeasonsDBO(
    val medium: String?,
    val original: String?
)


data class ListEpisodesDBO(
   val episodes: List<EpisodeDBO>
)

data class EpisodeDBO(
    val id: Long?,
    val url: String?,
    val name: String?,
    val airdate: String?,
    val season: Long?,
    val number: Long? = null,
    val runtime: Long?,
    val rating: RatingShowDBO? = null,
   val image: ImageShowDBO? = null,
    val summary: String?,
)

