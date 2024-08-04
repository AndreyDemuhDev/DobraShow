package com.example.dobrashow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dobrashow.screens.DetailShowScreen
import com.example.dobrashow.screens.SeasonDetailsScreen
import com.example.dobrashow.ui.theme.DobraShowTheme
import com.example.network.KtorClient
import com.example.network.models.domain.DomainShowEntity
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DobraShowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "show_details") {
                        composable(route = "show_details") {
                            DetailShowScreen(
                                ktorClient = ktorClient,
                                showId = 1,
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
