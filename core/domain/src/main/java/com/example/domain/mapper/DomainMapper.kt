package com.example.domain.mapper

import com.example.data.model.CastShowEntity
import com.example.data.model.CrewShowEntity
import com.example.data.model.EpisodeEntity
import com.example.data.model.PersonShowEntity
import com.example.data.model.SearchShowEntity
import com.example.data.model.SeasonsShowEntity
import com.example.data.model.ShowEntity
import com.example.domain.model.CastShowUi
import com.example.domain.model.CharacterShowUi
import com.example.domain.model.CountryPersonUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.EpisodeUI
import com.example.domain.model.ImagePersonUi
import com.example.domain.model.ImageSeasonUI
import com.example.domain.model.PersonShowUi
import com.example.domain.model.RatingUI
import com.example.domain.model.SeasonEpisodesUI
import com.example.domain.model.SeasonsShowUi
import com.example.domain.model.ShowsUi

/**
 * класс в котором мапим из моделей модуля data в модели модуля domain
 */
internal fun ShowEntity.toUiShows(): ShowsUi {
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


internal fun SeasonsShowEntity.toUiSeason(): SeasonsShowUi {
    return SeasonsShowUi(
        id = id,
        endDate = endDate,
        episodeOrder = episodeOrder,
        image = ImageSeasonUI(
            medium = image.medium,
            original = image.original
        ),
        name = name,
        number = number,
        premiereDate = premiereDate,
        summary = summary,
        url = url,
        listEpisodes = SeasonEpisodesUI(episodes = listEpisodes.episodes.map { it.toUiEpisode() })
    )
}


internal fun EpisodeEntity.toUiEpisode(): EpisodeUI {
    return EpisodeUI(
        id = id,
        url = url,
        name = name,
        airdate = airdate,
        season = season,
        number = number,
        runtime = runtime,
        rating = RatingUI(average = rating.average),
        image = ImageSeasonUI(
            medium = image.medium,
            original = image.original
        ),
        summary = summary
    )
}

internal fun PersonShowEntity.toPersonUi(): PersonShowUi {
    return PersonShowUi(
        id = id,
        birthday = birthday,
        country = CountryPersonUi(name = country.name),
        deathday = deathday,
        gender = gender,
        image = ImagePersonUi(medium = image.medium, original = image.original),
        name = name,
        url = url
    )
}


internal fun SearchShowEntity.toSearchShowUi(): ShowsUi{
    return ShowsUi(
        id = searchShow.id,
        name = searchShow.name,
        ended = searchShow.ended,
        genres = searchShow.genres,
        image = ShowsUi.ImageShowUi(
            medium = searchShow.image.medium,
            original = searchShow.image.original
        ),
        language = searchShow.language,
        network = ShowsUi.NetworkShowUi(
            country = ShowsUi.CountryShowUi(
                code = searchShow.network.country.code,
                name = searchShow.network.country.name,
                timezone = searchShow.network.country.timezone
            ),
            id = searchShow.network.id,
            name = searchShow.network.name,
            officialSite = searchShow.network.officialSite
        ),
        officialSite = searchShow.officialSite,
        premiered = searchShow.premiered,
        rating = ShowsUi.RatingShowUi(average = searchShow.rating.average),
        status = searchShow.status,
        summary = searchShow.summary,
        url = searchShow.url,
    )
}
