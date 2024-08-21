package com.example.dobrashow.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dobrashow.screens.favorite.FavoriteScreen
import com.example.dobrashow.screens.person_details.PersonDetailsScreen
import com.example.dobrashow.screens.persons.PersonsScreen
import com.example.dobrashow.screens.search.SearchScreen
import com.example.dobrashow.screens.season_details.SeasonDetailsScreen
import com.example.dobrashow.screens.show.ShowsScreen
import com.example.dobrashow.screens.show_details.DetailShowScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavScreenDestination.Shows.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        //экраны для bottom navigation bar
        composable(route = NavScreenDestination.Shows.route) {
            ShowsScreen(
                onClickShow = { showId ->
                    navController.navigate("show_details/$showId")
                },
                modifier = Modifier
            )
        }
        composable(route = NavScreenDestination.Persons.route) {
            PersonsScreen(onClickPerson = { navController.navigate(NavScreenDestination.Persons.route) })
        }
        composable(route = NavScreenDestination.Search.route) {
            SearchScreen(onClickShow = { navController.navigate(NavScreenDestination.Search.route) })
        }
        composable(route = NavScreenDestination.Favorite.route) {
            FavoriteScreen()
        }
        //экраны не включенные в навигационную панель bottom navigation bar
        composable(
            route = "show_details/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId")
            showId?.let {
                DetailShowScreen(
                    showId = it,
                    onClickSeason = { navController.navigate("seasons_details/$it") },
                    onClickPerson = { navController.navigate("people_details/$it") },
                    onClickBack = { navController.navigateUp() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        composable(
            route = "seasons_details/{seasonId}",
            arguments = listOf(navArgument("seasonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val currentShow = backStackEntry.arguments?.getInt("seasonId")
            currentShow?.let {
                SeasonDetailsScreen(
                    seasonId = it,
                    onClickBack = { navController.navigateUp() },
                )
            }
        }
        composable(
            route = "people_details/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
            val currentPeople = backStackEntry.arguments?.getInt("personId")
            currentPeople?.let {
                PersonDetailsScreen(
                    personId = it,
                    onClickShow = { navController.navigate("show_details/$it") },
                    onClickCrew = { navController.navigate("show_details/$it") },
                    onClickBack = { navController.navigateUp() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


//табы навигационной панели
val tabs = listOf(
    NavScreenDestination.Shows,
    NavScreenDestination.Persons,
    NavScreenDestination.Search,
    NavScreenDestination.Favorite,
)

//навигационные графы
val bottomBarRoutes = setOf(
    NavScreenDestination.Shows.route,
    NavScreenDestination.Persons.route,
    NavScreenDestination.Search.route,
    NavScreenDestination.Favorite.route,
)