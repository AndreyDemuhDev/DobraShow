package com.example.data.model

class PersonShowEntity(
    val id: Int,
    val birthday: String,
    val country: CountryPersonEntity,
    val deathday: String,
    val gender: String,
    val image: ImagePersonEntity,
    val name: String,
    val url: String,
)

data class CharacterShowEntity(
    val id: Int,
    val image: ImagePersonEntity,
    val name: String,
    val url: String
)

data class CountryPersonEntity(
    val name: String,
)

data class ImagePersonEntity(
    val medium: String,
    val original: String
)

