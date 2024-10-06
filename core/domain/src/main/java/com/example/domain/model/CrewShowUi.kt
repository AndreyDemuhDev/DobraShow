package com.example.domain.model

data class CrewShowUi(
    val person: PersonShowUi,
    val type: String
) {
    data class PersonShowUi(
        val id: Int,
        val birthday: String,
        val country: CountryPersonUI,
        val deathday: String,
        val gender: String,
        val image: ImagePersonUI,
        val name: String,
        val url: String,
    )

    data class CountryPersonUI(
        val name: String,
    )

    data class ImagePersonUI(
        val medium: String,
        val original: String
    )
}
