package com.example.dobrashow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.dobrashow.navigation.BottomNavigationTabs
import com.example.uikit.theme.AppTheme
import com.example.view_show.ShowsMainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Scaffold(
                    containerColor = AppTheme.colorScheme.background,
                    bottomBar = { BottomNavigationTabs(navController) }
                ) { innerPadding ->
//                    AppNavigation(navController, innerPadding)
                    ShowsMainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
