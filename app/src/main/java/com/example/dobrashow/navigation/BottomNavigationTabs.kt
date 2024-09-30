package com.example.dobrashow.navigation

//
//@Composable
//fun BottomNavigationTabs(
//    navController: NavHostController
//) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    if (currentRoute in bottomBarRoutes) {
//        Column {
//            HorizontalDivider(thickness = AppTheme.size.dp2, color = AppTheme.colorScheme.primary)
//            NavigationBar(
//                containerColor = Color.Transparent,
//            ) {
//                tabs.forEach { screen ->
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                painter = painterResource(id = screen.icon),
//                                contentDescription = null,
//                                modifier = Modifier.size(
//                                    if (currentRoute != screen.route) {
//                                        AppTheme.size.dp24
//                                    } else AppTheme.size.dp24 * 1.5f
//                                ),
//                                tint = if (currentRoute != screen.route) {
//                                    AppTheme.colorScheme.text
//                                } else AppTheme.colorScheme.primary
//                            )
//                        },
//                        label = {
//                            Text(
//                                text = screen.title,
//                                color = if (currentRoute != screen.route) {
//                                    AppTheme.colorScheme.text
//                                } else AppTheme.colorScheme.primary,
//                                style = if (currentRoute != screen.route) {
//                                    AppTheme.typography.labelNormal
//                                } else AppTheme.typography.labelLarge,
//                            )
//                        },
//                        selected = currentRoute == screen.route,
//                        onClick = {
//                            if (currentRoute != screen.route) {
//                                navController.navigate(screen.route) {
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        },
//                        colors = NavigationBarItemDefaults.colors(
//                            indicatorColor = Color.Transparent
//                        )
//                    )
//                }
//            }
//        }
//    }
//}
