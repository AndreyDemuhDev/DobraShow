package com.example.database.model

data class PersonShowDBO(
    val id: Int,
    val birthday: String?,
    val country: CountryPersonDBO? = null,
    val deathday: String?,
    val gender: String?,
    val image: ImagePersonDBO? = null,
    val name: String,
    val url: String,
)


data class CharacterShowDBO(
   val id: Int,
    val image: ImagePersonDBO? = null,
    val name: String,
    val url: String
)

data class CountryPersonDBO(
    val name: String,
)


data class ImagePersonDBO(
    val medium: String,
    val original: String
)

