package com.example.dobrashow.navigation

import com.example.dobrashow.R

sealed class NavScreenDestination(
    val title: String,
    val route: String,
    val icon: Int,
) {
    data object Series : NavScreenDestination(
        title = "Series",
        route = "series",
        icon = R.drawable.ic_clapperboard,
    )

    data object Persons : NavScreenDestination(
        title = "Persons",
        route = "persons",
        icon = R.drawable.ic_persons,
    )

    data object Search : NavScreenDestination(
        title = "Search",
        route = "search",
        icon = R.drawable.ic_search,
    )
}

val tabs = listOf(
    NavScreenDestination.Series,
    NavScreenDestination.Persons,
    NavScreenDestination.Search
)