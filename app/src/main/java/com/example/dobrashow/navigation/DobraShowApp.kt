package com.example.dobrashow.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun DobraShowApp(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
) {

    val navController = rememberNavController()

    DobraShowHost(
        navController = navController,
        innerPadding = innerPadding,
        modifier = modifier
    )
}