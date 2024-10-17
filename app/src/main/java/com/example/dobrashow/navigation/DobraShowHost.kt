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
//            SearchScreen(onClickShow = { navController.navigate(NavScreenDestination.Search.route) })
        }
        composable(route = NavScreenDestination.Favorite.route) {
//            FavoriteScreen()
        }
        // экраны не включенные в навигационную панель bottom navigation bar
        composable(
            route = "show_details/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId")
            showId?.let {
                ShowDetailsMainScreen(idShow = showId, modifier = modifier)
            }
        }
        composable(
            route = "seasons_details/{seasonId}",
            arguments = listOf(navArgument("seasonId") { type = NavType.IntType })
        ) { backStackEntry ->
//            val currentShow = backStackEntry.arguments?.getInt("seasonId")
//            currentShow?.let {
//                SeasonDetailsScreen(
//                    seasonId = it,
//                    onClickBack = { navController.navigateUp() },
//                )
//            }
        }
        composable(
            route = "people_details/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
//            val currentPeople = backStackEntry.arguments?.getInt("personId")
//            currentPeople?.let { id ->
//                PersonDetailsScreen(
//                    personId = id,
//                    onClickShow = { navController.navigate("show_details/$id") },
//                    onClickCrew = { navController.navigate("show_details/$id") },
//                    onClickBack = { navController.navigateUp() },
//                )
//            }
        }
    }
}
