package com.example.network.models.domain

import com.example.network.models.remote.RemoteShowModel


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
    val listEpisodes: DomainEmbedded
) {
    data class ImageSeasons(
        val medium: String,
        val original: String
    )

    data class DomainEmbedded(
        val episodes: List<DomainEpisode>
    )

    data class DomainEpisode(
        val id: Long,
        val url: String,
        val name: String,
        val airdate: String,
        val season: Long,
        val number: Long? = null,
        val runtime: Long,
        val rating: RemoteShowModel.RatingShow,
        val image: RemoteShowModel.ImageShow? = null,
        val summary: String,
    )
}
