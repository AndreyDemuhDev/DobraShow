package com.example.dobrashow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dobrashow.navigation.NavScreenDestination
import com.example.dobrashow.screens.series.SeriesScreen
import com.example.dobrashow.screens.person_details.PersonDetailsScreen
import com.example.dobrashow.screens.persons.PersonsScreen
import com.example.dobrashow.screens.show_details.DetailShowScreen
import com.example.dobrashow.screens.season_details.SeasonDetailsScreen
import com.example.dobrashow.ui.theme.DobraShowTheme
import com.example.network.KtorClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val items = listOf(
                NavScreenDestination.Series,
                NavScreenDestination.Persons,
                NavScreenDestination.Search
            )
            var selectedIndex by remember { mutableIntStateOf(0) }
            DobraShowTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    label = { Text(text = screen.title) },
                                    selected = index == selectedIndex,
                                    onClick = {
                                        selectedIndex = index
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavScreenDestination.Series.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = NavScreenDestination.Series.route) {
                            SeriesScreen(onClickShow = { showId ->
                                navController.navigate("show_details/$showId")
                            }
                            )
                        }
                        composable(
                            route = "show_details/{showId}",
                            arguments = listOf(navArgument("showId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val showId = backStackEntry.arguments?.getInt("showId") ?: -1
                            DetailShowScreen(
                                showId = showId,
                                onClickSeason = { navController.navigate("seasons_details/$it") },
                                onClickPerson = { navController.navigate("people_details/$it") },
                                onClickBack = { navController.navigateUp() },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable(
                            route = "seasons_details/{seasonId}",
                            arguments = listOf(navArgument("seasonId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val currentShow = backStackEntry.arguments?.getInt("seasonId") ?: -1
                            SeasonDetailsScreen(
                                seasonId = currentShow,
                                onClickBack = { navController.navigateUp() },
                            )
                        }
                        composable(
                            route = "people_details/{personId}",
                            arguments = listOf(navArgument("personId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val currentPeople = backStackEntry.arguments?.getInt("personId") ?: -1
                            PersonDetailsScreen(
                                personId = currentPeople,
                                onClickShow = { navController.navigate("show_details/$it") },
                                onClickCrew = { navController.navigate("show_details/$it") },
                                onClickBack = { navController.navigateUp() },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable(route = NavScreenDestination.Persons.route) {
                            PersonsScreen(onClickPerson = { navController.navigate("people_details/$it") })
                        }
                        composable(route = NavScreenDestination.Search.route) {
                            Text(text = "Search screen")
                        }
                    }
                }
            }
        }
    }
}
