package com.example.dobrashow.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.dobrashow.ui.theme.AppTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild


@Composable
fun BottomNavigationTabs(
    hazeState: HazeState,
    navController: NavHostController
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .hazeChild(state = hazeState)
            .fillMaxWidth()
            .border(
                width = AppTheme.size.dp2,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = .8f),
                        Color.White.copy(alpha = .2f),
                    ),
                ),
                shape = RoundedCornerShape(24.dp)
            ),
    ) {
        tabs.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        modifier = Modifier.size(AppTheme.size.dp24)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        style = AppTheme.typography.labelNormal
                    )
                },
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

