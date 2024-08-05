package com.example.network.models.remote

import com.example.network.models.domain.DomainCastEntity
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCastModel(
    val person: PersonShow,
    val character: CharacterShow
) {
    @Serializable
    data class PersonShow(
        val id: Int,
        val birthday: String?,
        val country: CountryPerson? = null,
        val deathday: String?,
        val gender: String?,
        val image: ImagePerson? = null,
        val name: String,
        val url: String,
    )

    @Serializable
    data class CharacterShow(
        val id: Int,
        val image: ImagePerson? = null,
        val name: String,
        val url: String
    )

    @Serializable
    data class CountryPerson(
        val name: String,
    )

    @Serializable
    data class ImagePerson(
        val medium: String,
        val original: String
    )
}


fun RemoteCastModel.toDomainCast(): DomainCastEntity {
    return DomainCastEntity(
        person = DomainCastEntity.PersonShow(
            id = person.id,
            birthday = person.birthday?: "unknown date birthday",
            country = DomainCastEntity.CountryPerson(
                name = person.country?.name ?: "unknown name country"
            ),
            deathday = person.deathday ?: "empty",
            gender = person.gender ?: "unknown gender",
            image = DomainCastEntity.ImagePerson(
                medium = person.image?.medium ?: "",
                original = person.image?.original ?: ""
            ),
            name = person.name,
            url = person.url
        ),
        character = DomainCastEntity.CharacterShow(
            id = character.id,
            image = DomainCastEntity.ImagePerson(
                medium = character.image?.medium ?: "",
                original = character.image?.original ?: ""
            ),
            name = character.name,
            url = character.url
        )
    )
}


