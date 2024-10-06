package com.example.domain.mapper

import com.example.data.model.CastShowEntity
import com.example.data.model.CrewShowEntity
import com.example.data.model.ShowsEntity
import com.example.domain.model.CastShowUi
import com.example.domain.model.CharacterShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.ImagePersonUi
import com.example.domain.model.PersonShowUi
import com.example.domain.model.ShowsUi

internal fun ShowsEntity.toUiShows(): ShowsUi {
    return ShowsUi(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = ShowsUi.ImageShowUi(
            medium = image.medium,
            original = image.original
        ),
        language = language,
        network = ShowsUi.NetworkShowUi(
            country = ShowsUi.CountryShowUi(
                code = network.country.code,
                name = network.country.name,
                timezone = network.country.timezone
            ),
            id = network.id,
            name = network.name,
            officialSite = network.officialSite
        ),
        officialSite = officialSite,
        premiered = premiered,
        rating = ShowsUi.RatingShowUi(average = rating.average),
        status = status,
        summary = summary,
        url = url,
    )
}

internal fun CrewShowEntity.toUiCrew(): CrewShowUi {
    return CrewShowUi(
        person = CrewShowUi.PersonShowUi(
            id = person.id,
            birthday = person.birthday,
            country = CrewShowUi.CountryPersonUI(name = person.country.name),
            deathday = person.deathday,
            gender = person.gender,
            image = CrewShowUi.ImagePersonUI(
                medium = person.image.medium,
                original = person.image.original
            ),
            name = person.name,
            url = person.url
        ),
        type = type
    )
}

internal fun CastShowEntity.toUiCast(): CastShowUi {
    return CastShowUi(
        person = CrewShowUi.PersonShowUi(
            id = person.id,
            birthday = person.birthday,
            country = CrewShowUi.CountryPersonUI(name = person.country.name),
            deathday = person.deathday,
            gender = person.gender,
            image = CrewShowUi.ImagePersonUI(
                medium = person.image.medium,
                original = person.image.original
            ),
            name = person.name,
            url = person.url
        ),
        character = CharacterShowUi(
            id = person.id,
            image = ImagePersonUi(
                medium = person.image.medium,
                original = person.image.original
            ),
            name = person.name,
            url = person.url
        ),
    )
}
