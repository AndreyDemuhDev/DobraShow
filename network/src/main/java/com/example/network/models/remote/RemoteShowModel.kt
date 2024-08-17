package com.example.network.models.remote

import com.example.network.models.domain.DomainShowEntity
import com.example.network.ShowStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteShowModel(
    @SerialName("id")
    val id: Int?,
    val name: String? = "unknown name",                     //название
    val ended: String? = "unknown ended",                   //дата завершения
    val genres: List<String>? = emptyList(),                //жанры
    val image: ImageShow? = null,                           //постер
    val language: String? = "unknown language",             //язык
    val network: NetworkShow? = null,                       //инфа о производителе
    val officialSite: String? = "unknown official site",    //ссылка на офф сайт
    val premiered: String? = "unknown premiered",           //дата премьеры
    val rating: RatingShow? = null,                         //рейтинг
    val status: String? = "unknown status",                 //статус (завершен, выпускается)
    val summary: String? = "unknown summary",               //описание
    val url: String? = "unknown url",                       //ссылка на сайт
) {
    @Serializable
    data class ImageShow(
        val medium: String?,
        val original: String?,
    )

    @Serializable
    data class NetworkShow(
        val country: CountryShow? = null,
        val id: Int?,
        val name: String?,
        val officialSite: String?
    )

    @Serializable
    data class CountryShow(
        val code: String?,
        val name: String?,
        val timezone: String?
    )

    @Serializable
    data class RatingShow(
        val average: Double? = 0.0,
    )
}

//маппер
fun RemoteShowModel.toDomainShow(): DomainShowEntity {
    val showStatus = when (status?.lowercase()) {
        "running" -> ShowStatus.Running
        "ended" -> ShowStatus.Ended
        "to be determined" -> ShowStatus.Determined
        else -> ShowStatus.Unknown
    }
    return DomainShowEntity(
        id = id ?: 4,
        name = name ?: "unknown name",
        ended = ended ?: "unknown",
        genres = genres ?: emptyList(),
        image = DomainShowEntity.ImageShow(
            medium = image?.medium ?: "unknown image",
            original = image?.original ?: "unknown image"
        ),
        language = language ?: "unknown language",
        network = DomainShowEntity.NetworkShow(
            country = DomainShowEntity.CountryShow(
                code = network?.country?.code ?: "unknown code country",
                name = network?.country?.name ?: "unknown name country",
                timezone = network?.country?.timezone ?: "unknown timezone country"
            ),
            id = network?.id ?: -1,
            name = network?.name ?: "unknown name country",
            officialSite = network?.officialSite ?: "unknown official site"
        ),
        officialSite = officialSite ?: "unknown language",
        premiered = premiered ?: "unknown premiered",
        rating = DomainShowEntity.RatingShow(average = rating?.average ?: -1.0),
        status = showStatus,
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
    )
}
