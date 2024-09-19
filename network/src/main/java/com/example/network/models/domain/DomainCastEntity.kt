package com.example.network.models.domain


data class DomainCastEntity(
    val person: PersonShow,
    val character: CharacterShow
) {

    data class PersonShow(
        val id: Int,
        val birthday: String,
        val country: CountryPerson,
        val deathday: String?,
        val gender: String,
        val image: ImagePerson,
        val name: String,
        val url: String,
    )

    data class CharacterShow(
        val id: Int,
        val image: ImagePerson,
        val name: String,
        val url: String
    )

    data class CountryPerson(
        val name: String,
    )

    data class ImagePerson(
        val medium: String,
        val original: String
    )
}
