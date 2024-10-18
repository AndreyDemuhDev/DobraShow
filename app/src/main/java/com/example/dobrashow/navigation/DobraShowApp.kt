package com.example.dobrashow.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.design.theme.AppTheme

@Composable
fun DobraShowApp() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        bottomBar = { BottomNavigationTabs(navController = navController) }
    ) { innerPadding ->
        DobraShowHost(
            navController = navController,
            innerPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        )
    }
}