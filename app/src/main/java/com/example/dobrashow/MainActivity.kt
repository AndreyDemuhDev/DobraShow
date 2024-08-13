package com.example.dobrashow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dobrashow.navigation.BottomNavigationTabs
import com.example.dobrashow.navigation.NavScreenDestination
import com.example.dobrashow.navigation.tabs
import com.example.dobrashow.screens.show.ShowsScreen
import com.example.dobrashow.screens.person_details.PersonDetailsScreen
import com.example.dobrashow.screens.persons.PersonsScreen
import com.example.dobrashow.screens.search.SearchScreen
import com.example.dobrashow.screens.season_details.SeasonDetailsScreen
import com.example.dobrashow.screens.show_details.DetailShowScreen2
import com.example.dobrashow.ui.theme.AppTheme
import com.example.network.KtorClient
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
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
            val hazeState = remember { HazeState() }
            AppTheme {
                Scaffold(
                    bottomBar = { BottomNavigationTabs(hazeState, navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavScreenDestination.Series.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = NavScreenDestination.Series.route) {
                            ShowsScreen(
                                onClickShow = { showId ->
                                    navController.navigate("show_details/$showId")
                                }
                            )
                        }
                        composable(
                            route = "show_details/{showId}",
                            arguments = listOf(navArgument("showId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val showId = backStackEntry.arguments?.getInt("showId") ?: -1
                            DetailShowScreen2(
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
                            SearchScreen(onClickShow = { navController.navigate("show_details/$it") })
                        }
                    }
                }
            }
        }
    }
}
