package com.example.network.models.domain

data class DomainPersonEntity(
    val embedded: Embedded,
    val birthday: String,
    val country: CountryPerson,
    val deathday: String,
    val gender: String,
    val id: Int,
    val image: ImagePerson,
    val name: String,
    val url: String
) {

    data class Embedded(
        val castcredits: List<Castcredit>,
        val crewcredits: List<Crewcredit>
    )

    data class Castcredit(
        val linksCast: LinksCast,
        val self: Boolean,
        val voice: Boolean
    ) {
        data class LinksCast(
            val character: Character,
            val show: ShowPerson
        ) {
            data class Character(
                val href: String,       //ссылка на шоу
                val name: String        //наименование шоу
            )

            data class ShowPerson(
                val href: String,       //ссылка на шоу
                val name: String        //наименование шоу
            )
        }
    }

    data class Crewcredit(
        val links: LinksCrew,          //ссылка на шоу
        val type: String
    ) {
        data class LinksCrew(
            val show: ShowPerson         //шоу
        ) {
            data class ShowPerson(
                val href: String,       //ссылка на шоу
                val name: String        //наименование шоу
            )
        }
    }

    data class CountryPerson(
        val name: String,
    )

    data class ImagePerson(
        val medium: String,
        val original: String
    )
}