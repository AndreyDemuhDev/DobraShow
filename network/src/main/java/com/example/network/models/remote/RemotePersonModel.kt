package com.example.network.models.remote

import com.example.network.models.domain.DomainPersonEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePersonModel(
    @SerialName("_embedded")
    val embedded: Embedded?,
    val birthday: String?,
    val country: CountryPerson?,
    val deathday: String?,
    val gender: String?,
    val id: Int?,
    val image: ImagePerson?,
    val name: String?,
    val url: String?
) {
    @Serializable
    data class Embedded(
        val castcredits: List<Castcredit?>,
        val crewcredits: List<Crewcredit?>
    )

    @Serializable
    data class Castcredit(
        @SerialName("_links")
        val linksCast: LinksCast?,
        val self: Boolean?,
        val voice: Boolean?
    ) {
        @Serializable
        data class LinksCast(
            val character: Character?,
            val show: ShowPerson?
        ) {
            @Serializable
            data class Character(
                val href: String?,       //ссылка на шоу
                val name: String?        //наименование шоу
            )

            @Serializable
            data class ShowPerson(
                val href: String?,       //ссылка на шоу
                val name: String?        //наименование шоу
            )
        }
    }

    @Serializable
    data class Crewcredit(
        @SerialName("_links")
        val links: LinksCrew?,          //ссылка на шоу
        val type: String?
    ) {
        @Serializable
        data class LinksCrew(
            val show: ShowPerson?         //шоу
        ) {
            @Serializable
            data class ShowPerson(
                val href: String?,       //ссылка на шоу
                val name: String?       //наименование шоу
            )
        }
    }

    @Serializable
    data class CountryPerson(
        val name: String?,
    )

    @Serializable
    data class ImagePerson(
        val medium: String?,
        val original: String?
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