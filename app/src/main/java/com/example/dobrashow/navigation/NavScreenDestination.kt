package com.example.dobrashow.navigation

import com.example.dobrashow.R

sealed class NavScreenDestination(
    val title: String,
    val route: String,
    val icon: Int,
) {
    data object Shows : NavScreenDestination(
        title = "Shows",
        route = "shows",
        icon = R.drawable.ic_clapperboard,
    )

    data object Search : NavScreenDestination(
        title = "Search",
        route = "search",
        icon = R.drawable.ic_search,
    )

    data object Favorite : NavScreenDestination(
        title = "Favorite",
        route = "favorite",
        icon = R.drawable.ic_bookmark,
    )
}
