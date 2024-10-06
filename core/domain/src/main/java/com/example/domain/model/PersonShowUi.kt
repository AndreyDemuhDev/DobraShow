package com.example.domain.model

class PersonShowUi(
    val id: Int,
    val birthday: String?,
    val country: CountryPersonUi? = null,
    val deathday: String?,
    val gender: String?,
    val image: ImagePersonUi? = null,
    val name: String,
    val url: String,
)

data class CharacterShowUi(
    val id: Int,
    val image: ImagePersonUi? = null,
    val name: String,
    val url: String
)

data class CountryPersonUi(
    val name: String,
)

data class ImagePersonUi(
    val medium: String,
    val original: String
)
