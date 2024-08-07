package com.example.dobrashow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dobrashow.screens.home.HomeScreen
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
            DobraShowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "home_screen") {
                        composable(route = "home_screen") {
                            HomeScreen(onClickShow = { showId ->
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
                                onClick = { navController.navigate("seasons_show/$it") },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable(
                            route = "seasons_show/{showId}",
                            arguments = listOf(navArgument("showId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val currentShow = backStackEntry.arguments?.getInt("showId") ?: -1
                            SeasonDetailsScreen(showId = currentShow)
                        }
                    }
                }
            }
        }
    }
}
