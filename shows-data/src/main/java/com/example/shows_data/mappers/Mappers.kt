package com.example.shows_data.mappers

import com.example.database.models.ShowsDBO
import com.example.network.models.domain.DomainShowEntity
import com.example.network.models.remote.RemoteShowModel
import com.example.shows_data.model.ShowsUi

internal fun ShowsDBO.toShow(): ShowsUi {
    return ShowsUi(
        id = id,
        name = name,
        ended = ended,
        genres = genres,
        image = ShowsUi.ImageShow(
            medium = image.medium,
            original = image.original
        ),
        language = language,
        network = ShowsUi.NetworkShow(
            country = ShowsUi.CountryShow(
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
        rating = ShowsUi.RatingShow(average = rating.average),
        status = status,
        summary = summary,
        url = url,
    )
}

internal fun RemoteShowModel.toShow(): ShowsUi {
    return ShowsUi(
        id = id ?: 4,
        name = name ?: "unknown name",
        ended = ended ?: "unknown",
        genres = genres ?: emptyList(),
        image = ShowsUi.ImageShow(
            medium = image?.medium ?: "unknown image",
            original = image?.original ?: "unknown image"
        ),
        language = language ?: "unknown language",
        network = ShowsUi.NetworkShow(
            country = ShowsUi.CountryShow(
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
        rating = ShowsUi.RatingShow(average = rating?.average ?: -1.0),
        status = status ?: "unknown status",
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
    )
}

internal fun RemoteShowModel.toShowDatabase(): ShowsDBO {
    return ShowsDBO(
        id = id ?: 4,
        name = name ?: "unknown name",
        ended = ended ?: "unknown",
        genres = genres ?: emptyList(),
        image = ShowsDBO.ImageShow(
            medium = image?.medium ?: "unknown image",
            original = image?.original ?: "unknown image"
        ),
        language = language ?: "unknown language",
        network = ShowsDBO.NetworkShow(
            country = ShowsDBO.CountryShow(
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
        rating = ShowsDBO.RatingShow(average = rating?.average ?: -1.0),
        status = status ?: "unknown status",
        summary = summary ?: "unknown summary",
        url = url ?: "unknown url",
    )
}