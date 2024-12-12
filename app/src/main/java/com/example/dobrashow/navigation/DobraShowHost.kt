package com.example.dobrashow.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.favorite.FavoriteMainScreen
import com.example.search.SearchMainScreen
import com.example.view_show.screens.details_person.PersonDetailsMainScreen
import com.example.view_show.screens.details_season.DetailSeasonMainScreen
import com.example.view_show.screens.details_show.ShowDetailsMainScreen
import com.example.view_show.screens.main_screen.ShowsMainScreen

@Composable
fun DobraShowHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {

    NavHost(
        navController = navController,
        startDestination = NavScreenDestination.Shows.route,
        modifier = modifier.padding(innerPadding)
    ) {
        // экраны для bottom navigation bar
        composable(route = NavScreenDestination.Shows.route) {
            ShowsMainScreen(
                onClickShow = { showId ->
                    navController.navigate("show_details/$showId")
                },
                modifier = modifier
            )
        }
        composable(route = NavScreenDestination.Search.route) {
            SearchMainScreen(onClickShow = { navController.navigate("show_details/$it") })
        }
        composable(route = NavScreenDestination.Favorite.route) {
            FavoriteMainScreen(
                onClickShow = { navController.navigate("show_details/$it") },
                onClickDelete = {}
            )
        }
        // экраны не включенные в навигационную панель bottom navigation bar
        composable(
            route = "show_details/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId")
            showId?.let {
                ShowDetailsMainScreen(
                    idShow = showId,
                    onClickPerson = { navController.navigate("people_details/$it") },
                    onClickSeason = { navController.navigate("seasons_details/$it") },
                    onClickBack = { navController.navigateUp() },
                    modifier = modifier
                )
            }
        }
        composable(
            route = "seasons_details/{seasonId}",
            arguments = listOf(navArgument("seasonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val currentShow = backStackEntry.arguments?.getInt("seasonId")
            currentShow?.let {
                DetailSeasonMainScreen(
                    idSeason = it,
                    onClickBack = { navController.navigateUp() },
                )
            }
        }
        composable(
            route = "people_details/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
            val currentPeople = backStackEntry.arguments?.getInt("personId")
            currentPeople?.let { id ->
                PersonDetailsMainScreen(
                    personId = id,
//                    onClickShow = { navController.navigate("show_details/$id") },
//                    onClickCrew = { navController.navigate("show_details/$id") },
                    onClickBack = { navController.navigateUp() },
                )
            }
        }
    }
}
