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

//
//@Composable
//@Suppress("LongMethod")
//fun AppNavigation(
//    navController: NavHostController,
//    innerPadding: PaddingValues
//) {
//    NavHost(
//        navController = navController,
//        startDestination = NavScreenDestination.Shows.route,
//        modifier = Modifier.padding(innerPadding)
//    ) {
//        // экраны для bottom navigation bar
//        composable(route = NavScreenDestination.Shows.route) {
//            ShowsScreen(
//                onClickShow = { showId ->
//                    navController.navigate("show_details/$showId")
//                },
//                modifier = Modifier
//            )
//        }
//        composable(route = NavScreenDestination.Persons.route) {
//            PersonsScreen(onClickPerson = { navController.navigate(NavScreenDestination.Persons.route) })
//        }
//        composable(route = NavScreenDestination.Search.route) {
//            SearchScreen(onClickShow = { navController.navigate(NavScreenDestination.Search.route) })
//        }
//        composable(route = NavScreenDestination.Favorite.route) {
//            FavoriteScreen()
//        }
//        // экраны не включенные в навигационную панель bottom navigation bar
//        composable(
//            route = "show_details/{showId}",
//            arguments = listOf(navArgument("showId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val showId = backStackEntry.arguments?.getInt("showId")
//            showId?.let {
//                DetailShowScreen(
//                    showId = it,
//                    onClickSeason = { navController.navigate("seasons_details/$it") },
//                    onClickPerson = { navController.navigate("people_details/$it") },
//                    onClickBack = { navController.navigateUp() },
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
//        composable(
//            route = "seasons_details/{seasonId}",
//            arguments = listOf(navArgument("seasonId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val currentShow = backStackEntry.arguments?.getInt("seasonId")
//            currentShow?.let {
//                SeasonDetailsScreen(
//                    seasonId = it,
//                    onClickBack = { navController.navigateUp() },
//                )
//            }
//        }
//        composable(
//            route = "people_details/{personId}",
//            arguments = listOf(navArgument("personId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val currentPeople = backStackEntry.arguments?.getInt("personId")
//            currentPeople?.let { id ->
//                PersonDetailsScreen(
//                    personId = id,
//                    onClickShow = { navController.navigate("show_details/$id") },
//                    onClickCrew = { navController.navigate("show_details/$id") },
//                    onClickBack = { navController.navigateUp() },
//                )
//            }
//        }
//    }
//}
//

