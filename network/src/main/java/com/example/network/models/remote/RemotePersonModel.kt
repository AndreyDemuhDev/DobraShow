package com.example.network.models.remote

import com.example.network.models.domain.DomainPersonEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePersonModel(
    @SerialName("_embedded") val embedded: Embedded?,
    @SerialName("birthday") val birthday: String?,
    @SerialName("country") val country: CountryPerson?,
    @SerialName("deathday") val deathday: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("id") val id: Int?,
    @SerialName("image") val image: ImagePerson?,
    @SerialName("name") val name: String?,
    @SerialName("url") val url: String?
) {
    @Serializable
    data class Embedded(
        @SerialName("castcredits") val castcredits: List<Castcredit?> = emptyList(),
        @SerialName("crewcredits") val crewcredits: List<Crewcredit?> = emptyList()
    )

    @Serializable
    data class Castcredit(
        @SerialName("_links") val linksCast: LinksCast?,
        @SerialName("self") val self: Boolean?,
        @SerialName("voice") val voice: Boolean?
    ) {
        @Serializable
        data class LinksCast(
            @SerialName("character") val character: Character?,
            @SerialName("show") val show: ShowPerson?
        ) {
            @Serializable
            data class Character(
                @SerialName("href") val href: String?,
                @SerialName("name") val name: String?
            )

            @Serializable
            data class ShowPerson(
                @SerialName("href") val href: String?,
                @SerialName("name") val name: String?
            )
        }
    }

    @Serializable
    data class Crewcredit(
        @SerialName("_links") val links: LinksCrew?,
        @SerialName("type") val type: String?
    ) {
        @Serializable
        data class LinksCrew(
            @SerialName("show") val show: ShowPerson?
        ) {
            @Serializable
            data class ShowPerson(
                @SerialName("href") val href: String?,
                @SerialName("name") val name: String?
            )
        }
    }

    @Serializable
    data class CountryPerson(
        @SerialName("name") val name: String?,
    )

    @Serializable
    data class ImagePerson(
        @SerialName("medium") val medium: String?,
        @SerialName("original") val original: String?
    )
}


fun RemotePersonModel.toDomainPerson(): DomainPersonEntity {
    return DomainPersonEntity(
        embedded = DomainPersonEntity.Embedded(
            castcredits = embedded?.castcredits?.mapIndexed { index, castcredit ->
                DomainPersonEntity.Castcredit(
                    linksCast = DomainPersonEntity.Castcredit.LinksCast(
                        character = DomainPersonEntity.Castcredit.LinksCast.Character(
                            href = castcredit?.linksCast?.character?.href ?: "",
                            name = castcredit?.linksCast?.character?.name ?: ""
                        ),
                        show = DomainPersonEntity.Castcredit.LinksCast.ShowPerson(
                            href = castcredit?.linksCast?.show?.href ?: "",
                            name = castcredit?.linksCast?.show?.name ?: ""
                        )
                    ),
                    self = embedded.castcredits[index]?.self ?: false,
                    voice = embedded.castcredits[index]?.voice ?: false
                )
            } ?: emptyList(),
            crewcredits = embedded?.crewcredits?.mapIndexed { index, crewcredit ->
                DomainPersonEntity.Crewcredit(
                    links = DomainPersonEntity.Crewcredit.LinksCrew(
                        show = DomainPersonEntity.Crewcredit.LinksCrew.ShowPerson(
                            href = crewcredit?.links?.show?.href ?: "",
                            name = crewcredit?.links?.show?.name ?: ""
                        )
                    ),
                    type = crewcredit?.type ?: ""
                )
            } ?: emptyList()
        ),
        birthday = birthday ?: "",
        country = DomainPersonEntity.CountryPerson(name = country?.name ?: ""),
        deathday = deathday ?: "",
        gender = gender ?: "",
        id = id ?: -1,
        image = DomainPersonEntity.ImagePerson(
            medium = image?.medium ?: "",
            original = image?.original ?: ""
        ),
        name = name ?: "",
        url = url ?: "",
    )
}
