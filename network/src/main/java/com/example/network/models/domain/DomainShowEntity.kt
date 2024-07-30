package com.example.network.models.domain

data class DomainShowEntity(
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
    val status: ShowStatus,     //статус (завершен, выпускается)
    val summary: String,        //описание
    val url: String,            //ссылка на сайт
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