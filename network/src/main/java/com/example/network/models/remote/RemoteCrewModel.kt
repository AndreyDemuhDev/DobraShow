package com.example.network.models.remote

import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainCrewEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCrewModel(
    @SerialName("person") val person: RemoteCastModel.PersonShow,
    @SerialName("type") val type: String
)

fun RemoteCrewModel.toDomainCrew(): DomainCrewEntity {
    return DomainCrewEntity(
        person = DomainCastEntity.PersonShow(
            id = person.id,
            birthday = person.birthday ?: "unknown birthday",
            country = DomainCastEntity.CountryPerson(
                name = person.country?.name ?: "unknown country",
            ),
            deathday = person.deathday ?: "unknown",
            gender = person.gender ?: "unknown gender",
            image = DomainCastEntity.ImagePerson(
                medium = person.image?.medium ?: "unknown image",
                original = person.image?.original ?: "unknown image"
            ),
            name = person.name,
            url = person.url,
        ),
        type = type
    )
}
