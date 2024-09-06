package com.example.network.models.remote

import com.example.network.models.domain.DomainShowEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//данные remote мы получаем из сети

@Serializable
data class RemoteShowModel(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String? = "unknown name",                             //название
    @SerialName("ended") val ended: String? = "unknown ended",                          //дата завершения
    @SerialName("genres") val genres: List<String>? = emptyList(),                      //жанры
    @SerialName("image") val image: ImageShow? = null,                                  //постер
    @SerialName("language") val language: String? = "unknown language",                 //язык
    @SerialName("network") val network: NetworkShow? = null,                            //инфа о производителе
    @SerialName("officialSite") val officialSite: String? = "unknown official site",    //ссылка на офф сайт
    @SerialName("premiered") val premiered: String? = "unknown premiered",              //дата премьеры
    @SerialName("rating") val rating: RatingShow? = null,                               //рейтинг
    @SerialName("status") val status: String? = "unknown status",                       //статус (завершен, выпускается)
    @SerialName("summary") val summary: String? = "unknown summary",                    //описание
    @SerialName("url") val url: String? = "unknown url",                                //ссылка на сайт
) {
    @Serializable
    data class ImageShow(
        @SerialName("medium") val medium: String?,
        @SerialName("original") val original: String?,
    )

    @Serializable
    data class NetworkShow(
        @SerialName("country") val country: CountryShow? = null,
        @SerialName("id") val id: Int?,
        @SerialName("name") val name: String?,
        @SerialName("officialSite") val officialSite: String?
    )

    @Serializable
    data class CountryShow(
        @SerialName("code") val code: String?,
        @SerialName("name") val name: String?,
        @SerialName("timezone") val timezone: String?
    )

    @Serializable
    data class RatingShow(
        @SerialName("average") val average: Double? = 0.0,
    )
}

//маппер
fun RemoteShowModel.toDomainShow(): DomainShowEntity {
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
        status = status ?: "unknown status",
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
    )
}
