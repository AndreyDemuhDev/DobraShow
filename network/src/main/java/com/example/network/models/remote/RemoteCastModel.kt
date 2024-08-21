package com.example.network.models.remote

import com.example.network.models.domain.DomainCastEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCastModel(
    @SerialName("person") val person: PersonShow,
    @SerialName("character") val character: CharacterShow
) {
    @Serializable
    data class PersonShow(
        @SerialName("id") val id: Int,
        @SerialName("birthday") val birthday: String?,
        @SerialName("country") val country: CountryPerson? = null,
        @SerialName("deathday") val deathday: String?,
        @SerialName("gender") val gender: String?,
        @SerialName("image") val image: ImagePerson? = null,
        @SerialName("name") val name: String,
        @SerialName("url") val url: String,
    )

    @Serializable
    data class CharacterShow(
        @SerialName("id") val id: Int,
        @SerialName("image") val image: ImagePerson? = null,
        @SerialName("name") val name: String,
        @SerialName("url") val url: String
    )

    @Serializable
    data class CountryPerson(
        @SerialName("name") val name: String,
    )

    @Serializable
    data class ImagePerson(
        @SerialName("medium") val medium: String,
        @SerialName("original") val original: String
    )
}


fun RemoteCastModel.toDomainCast(): DomainCastEntity {
    return DomainCastEntity(
        person = DomainCastEntity.PersonShow(
            id = person.id,
            birthday = person.birthday ?: "unknown date birthday",
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


