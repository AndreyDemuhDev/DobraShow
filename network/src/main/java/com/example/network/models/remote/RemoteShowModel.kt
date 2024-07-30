package com.example.network.models.remote

import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.domain.ShowStatus
import kotlinx.serialization.Serializable

@Serializable
data class RemoteShowModel(
    val id: Int,
    val name: String,           //название
    val ended: String,          //дата завершения
    val genres: List<String>,   //жанры
    val image: ImageShow,       //постер
    val language: String,       //язык
    val network: NetworkShow,   //инфа о производителе
    val officialSite: String,   //ссылка на офф сайт
    val premiered: String,      //дата премьеры
    val rating: RatingShow,     //рейтинг
    val status: String,         //статус (завершен, выпускается)
    val summary: String,        //описание
    val url: String,            //ссылка на сайт
) {
    @Serializable
    data class ImageShow(
        val medium: String,
        val original: String,
    )

    @Serializable
    data class NetworkShow(
        val country: CountryShow,
        val id: Int,
        val name: String,
        val officialSite: String
    )

    @Serializable
    data class CountryShow(
        val code: String,
        val name: String,
        val timezone: String
    )

    @Serializable
    data class RatingShow(
        val average: Double,
    )
}

//маппер
fun RemoteShowModel.toDomainShow(): DomainShowEntity {
    val showStatus = when (status.lowercase()) {
        "running" -> ShowStatus.Running
        "ended" -> ShowStatus.Ended
        "to be determined" -> ShowStatus.Determined
        else -> ShowStatus.Unknown
    }
    return DomainShowEntity(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = DomainShowEntity.ImageShow(medium = image.medium, original = image.original),
        language = language,
        network = DomainShowEntity.NetworkShow(
            country = DomainShowEntity.CountryShow(
                code = network.country.code,
                name = network.country.name,
                timezone = network.country.timezone
            ),
            id = network.id,
            name = network.name,
            officialSite = network.officialSite
        ),
        officialSite = officialSite,
        premiered = premiered,
        rating = DomainShowEntity.RatingShow(average = rating.average),
        status = showStatus,
        summary = summary,
        url = url,
    )
}
