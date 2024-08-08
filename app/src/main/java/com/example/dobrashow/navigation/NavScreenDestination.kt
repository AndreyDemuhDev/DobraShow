package com.example.dobrashow.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.dobrashow.R

sealed class NavScreenDestination(
    val title: String,
    val route: String,
    val icon: Int,
) {
    object Series : NavScreenDestination(
        title = "Series",
        route = "series",
        icon = R.drawable.ic_clapperboard
    )

    object Persons : NavScreenDestination(
        title = "Persons",
        route = "persons",
        icon = R.drawable.ic_persons
    )

    object Search : NavScreenDestination(
        title = "Search",
        route = "search",
        icon = R.drawable.ic_search
    )

}