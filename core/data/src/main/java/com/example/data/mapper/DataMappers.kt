package com.example.data.mapper

import com.example.data.model.CastShowEntity
import com.example.data.model.CharacterShowEntity
import com.example.data.model.CountryPersonEntity
import com.example.data.model.CountryShowEntity
import com.example.data.model.CrewShowEntity
import com.example.data.model.EpisodeEntity
import com.example.data.model.ImagePersonEntity
import com.example.data.model.ImageSeasonsEntity
import com.example.data.model.ImageShowEntity
import com.example.data.model.ListEpisodesEntity
import com.example.data.model.NetworkShowEntity
import com.example.data.model.PersonShowEntity
import com.example.data.model.RatingShowEntity
import com.example.data.model.SearchShowEntity
import com.example.data.model.SeasonsShowEntity
import com.example.data.model.ShowEntity
import com.example.database.model.CastModelDBO
import com.example.database.model.CountryShowDBO
import com.example.database.model.CrewModelDBO
import com.example.database.model.EpisodeDBO
import com.example.database.model.ImageShowDBO
import com.example.database.model.NetworkShowDBO
import com.example.database.model.PersonShowDBO
import com.example.database.model.RatingShowDBO
import com.example.database.model.SeasonsModelDBO
import com.example.database.model.ShowsDBO
import com.example.network.model.RemoteCastModel
import com.example.network.model.RemoteCrewModel
import com.example.network.model.RemoteEpisode
import com.example.network.model.RemotePersonShow
import com.example.network.model.RemoteSearchShowModel
import com.example.network.model.RemoteSeasonsModel
import com.example.network.model.RemoteShowModel


// маппер из модели шоу базы данных в ui модель шоу
internal fun ShowsDBO.toShow(): ShowEntity {
    return ShowEntity(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = ImageShowEntity(
            medium = image.medium,
            original = image.original
        ),
        language = language,
        network = NetworkShowEntity(
            country = CountryShowEntity(
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
        rating = RatingShowEntity(average = rating.average),
        status = status,
        summary = summary,
        url = url,
        isFavorite = favorite,
    )
}

// маппер из сетевой модели шоу в модель шоу data слоя
internal fun RemoteShowModel.toShow(): ShowEntity {
    return ShowEntity(
        id = id ?: 4,
        name = name ?: "unknown name",
        ended = ended ?: "unknown",
        genres = genres ?: emptyList(),
        image = ImageShowEntity(
            medium = image?.medium ?: "unknown image",
            original = image?.original ?: "unknown image"
        ),
        language = language ?: "unknown language",
        network = NetworkShowEntity(
            country = CountryShowEntity(
                code = network?.country?.code ?: "unknown code country",
                name = network?.country?.name ?: "unknown name country",
                timezone = network?.country?.timezone ?: "unknown timezone country"
            ),
            id = network?.id ?: -1,
            name = network?.name ?: "unknown name country",
            officialSite = network?.officialSite ?: "unknown official site"
        ),
        officialSite = officialSite ?: "unknown language",
        premiered = premiered ?: "unknown premiered",
        rating = RatingShowEntity(average = rating?.average ?: -1.0),
        status = status ?: "unknown status",
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
        isFavorite = 0,
    )
}

internal fun ShowEntity.toShowDatabase():ShowsDBO{
    return ShowsDBO(
        id = id ,
        name = name,
        ended = ended,
        genres = genres,
        image = ImageShowDBO(
            medium = image.medium ,
            original = image.original
        ),
        language = language ,
        network = NetworkShowDBO(
            country = CountryShowDBO(
                code = network.country.code,
                name = network.country.name,
                timezone = network.country.timezone
            ),
            id = network.id,
            name = network.name,
            officialSite = network.officialSite
        ),
        officialSite = officialSite,
        premiered = premiered ,
        rating = RatingShowDBO(average = rating.average),
        status = status,
        summary = summary,
        url = url,
        favorite = 0,
    )
}

// маппер из сетевой модели шоу в модель шоу базы данных модель
internal fun RemoteShowModel.toShowDatabase(): ShowsDBO {
    return ShowsDBO(
        id = id ?: 4,
        name = name ?: "unknown name",
        ended = ended ?: "unknown",
        genres = genres ?: emptyList(),
        image = ImageShowDBO(
            medium = image?.medium ?: "unknown image",
            original = image?.original ?: "unknown image"
        ),
        language = language ?: "unknown language",
        network = NetworkShowDBO(
            country = CountryShowDBO(
                code = network?.country?.code ?: "unknown code country",
                name = network?.country?.name ?: "unknown name country",
                timezone = network?.country?.timezone ?: "unknown timezone country"
            ),
            id = network?.id ?: -1,
            name = network?.name ?: "unknown name country",
            officialSite = network?.officialSite ?: "unknown official site"
        ),
        officialSite = officialSite ?: "unknown language",
        premiered = premiered ?: "unknown premiered",
        rating = RatingShowDBO(average = rating?.average ?: -1.0),
        status = status ?: "unknown status",
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
        favorite = 0,
    )
}

//маппер из модели crew из сети в модель crew data слоя
internal fun RemoteCrewModel.toCrewShow(): CrewShowEntity {
    return CrewShowEntity(
        person = PersonShowEntity(
            id = person.id,
            birthday = person.birthday ?: "unknown birthday",
            country = CountryPersonEntity(
                name = person.country?.name ?: "unknown country",
            ),
            deathday = person.deathday ?: "unknown",
            gender = person.gender ?: "unknown gender",
            image = ImagePersonEntity(
                medium = person.image?.medium ?: "unknown image",
                original = person.image?.original ?: "unknown image"
            ),
            name = person.name,
            url = person.url
        ),
        type = type
    )
}

//маппер из модели cast из сети в модель cast data слоя
internal fun RemoteCastModel.toCastShow(): CastShowEntity {
    return CastShowEntity(
        person = PersonShowEntity(
            id = person.id,
            birthday = person.birthday ?: "unknown date birthday",
            country = CountryPersonEntity(
                name = person.country?.name ?: "unknown name country"
            ),
            deathday = person.deathday ?: "empty",
            gender = person.gender ?: "unknown gender",
            image = ImagePersonEntity(
                medium = person.image?.medium ?: "",
                original = person.image?.original ?: ""
            ),
            name = person.name,
            url = person.url
        ),
        character = CharacterShowEntity(
            id = character.id,
            image = ImagePersonEntity(
                medium = character.image?.medium ?: "",
                original = character.image?.original ?: ""
            ),
            name = character.name,
            url = character.url
        )
    )
}

//маппер из модели seasons из сети в модель season data слоя
internal fun RemoteSeasonsModel.toSeasonsShow(): SeasonsShowEntity {
    return SeasonsShowEntity(
        id = id,
        endDate = endDate ?: "unknown end date season",
        episodeOrder = episodeOrder ?: -1,
        image = ImageSeasonsEntity(
            medium = image?.medium ?: "empty medium image season",
            original = image?.original ?: "empty original image season"
        ),
        name = name ?: "unknown name season",
        number = number ?: -1,
        premiereDate = premiereDate ?: "unknown premiere date season",
        summary = summary ?: "",
        url = url ?: "unknown url season",
        listEpisodes = ListEpisodesEntity(
            episodes = listEpisodes?.episodes?.map { it.toEpisodeSeasonShow() } ?: emptyList()
        )
    )
}

//маппер из сетевой модели episode в модель episode data слоя
internal fun RemoteEpisode.toEpisodeSeasonShow(): EpisodeEntity {
    return EpisodeEntity(
        id = id ?: -1,
        url = url ?: "unknown url episode",
        name = name ?: "unknown name episode",
        airdate = airdate ?: "",
        season = season ?: -1,
        number = number ?: -1,
        runtime = runtime ?: -1,
        rating = RatingShowEntity(average = rating?.average ?: 0.0),
        image = ImageShowEntity(
            medium = image?.medium ?: "unknown image medium episode",
            original = image?.original ?: "unknown image original episode"
        ),
        summary = summary ?: "unknown summary episode"
    )
}

internal fun RemotePersonShow.toPerson(): PersonShowEntity {
    return PersonShowEntity(
        id = id,
        birthday = birthday ?: "-",
        country = CountryPersonEntity(name = country?.name ?: "unknown name person"),
        deathday = deathday ?: "-",
        gender = gender ?: "-",
        image = ImagePersonEntity(
            medium = image?.medium ?: "unknown image person",
            original = image?.original ?: "unknown image person"
        ),
        name = name,
        url = url,
    )
}

//маппер из модели crew из сети в модель crew data слоя
internal fun CrewModelDBO.toCrewShow(): CrewShowEntity {
    return CrewShowEntity(
        person = PersonShowEntity(
            id = person.id,
            birthday = person.birthday ?: "unknown birthday",
            country = CountryPersonEntity(
                name = person.country?.name ?: "unknown country",
            ),
            deathday = person.deathday ?: "unknown",
            gender = person.gender ?: "unknown gender",
            image = ImagePersonEntity(
                medium = person.image?.medium ?: "unknown image",
                original = person.image?.original ?: "unknown image"
            ),
            name = person.name,
            url = person.url
        ),
        type = type
    )
}

//маппер из модели cast из сети в модель cast data слоя
internal fun CastModelDBO.toCastShow(): CastShowEntity {
    return CastShowEntity(
        person = PersonShowEntity(
            id = person.id,
            birthday = person.birthday ?: "unknown date birthday",
            country = CountryPersonEntity(
                name = person.country?.name ?: "unknown name country"
            ),
            deathday = person.deathday ?: "empty",
            gender = person.gender ?: "unknown gender",
            image = ImagePersonEntity(
                medium = person.image?.medium ?: "",
                original = person.image?.original ?: ""
            ),
            name = person.name,
            url = person.url
        ),
        character = CharacterShowEntity(
            id = character.id,
            image = ImagePersonEntity(
                medium = character.image?.medium ?: "",
                original = character.image?.original ?: ""
            ),
            name = character.name,
            url = character.url
        )
    )
}

//маппер из модели seasons из сети в модель season data слоя
internal fun SeasonsModelDBO.toSeasonsShow(): SeasonsShowEntity {
    return SeasonsShowEntity(
        id = id,
        endDate = endDate ?: "unknown end date season",
        episodeOrder = episodeOrder ?: -1,
        image = ImageSeasonsEntity(
            medium = image?.medium ?: "empty medium image season",
            original = image?.original ?: "empty original image season"
        ),
        name = name ?: "unknown name season",
        number = number ?: -1,
        premiereDate = premiereDate ?: "unknown premiere date season",
        summary = summary ?: "",
        url = url ?: "unknown url season",
        listEpisodes = ListEpisodesEntity(
            episodes = listEpisodes?.episodes?.map { it.toEpisodeSeasonShow() } ?: emptyList()
        )
    )
}

//маппер из сетевой модели episode в модель episode data слоя
internal fun EpisodeDBO.toEpisodeSeasonShow(): EpisodeEntity {
    return EpisodeEntity(
        id = id ?: -1,
        url = url ?: "unknown url episode",
        name = name ?: "unknown name episode",
        airdate = airdate ?: "",
        season = season ?: -1,
        number = number ?: -1,
        runtime = runtime ?: -1,
        rating = RatingShowEntity(average = rating?.average ?: 0.0),
        image = ImageShowEntity(
            medium = image?.medium ?: "unknown image medium episode",
            original = image?.original ?: "unknown image original episode"
        ),
        summary = summary ?: "unknown summary episode"
    )
}

internal fun PersonShowDBO.toPerson(): PersonShowEntity {
    return PersonShowEntity(
        id = id,
        birthday = birthday ?: "-",
        country = CountryPersonEntity(name = country?.name ?: "unknown name person"),
        deathday = deathday ?: "-",
        gender = gender ?: "-",
        image = ImagePersonEntity(
            medium = image?.medium ?: "unknown image person",
            original = image?.original ?: "unknown image person"
        ),
        name = name,
        url = url,
    )
}

internal fun RemoteSearchShowModel.toSearchShow(): SearchShowEntity {
    return SearchShowEntity(searchShow = searchShow.toShow())
}


