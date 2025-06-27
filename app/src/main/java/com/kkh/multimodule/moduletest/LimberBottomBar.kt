package com.kkh.multimodule.moduletest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kkh.multimodule.moduletest.navigation.BottomNavRoutes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(BottomNavRoutes.HOME, "홈", Icons.Default.Home)
    object Search : BottomNavItem(BottomNavRoutes.SEARCH, "검색", Icons.Default.Search)
    object Notifications : BottomNavItem(BottomNavRoutes.NOTIFICATIONS, "알림", Icons.Default.Notifications)
    object SETTINGS : BottomNavItem(BottomNavRoutes.SETTINGS, "프로필", Icons.Default.Person)
}

@Composable
fun LimberBottomBar(
    navController: NavHostController,
    onScreenSelected: (String) -> Unit
) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Notifications,
        BottomNavItem.SETTINGS
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        onScreenSelected(screen.route) // 🔥 상태 업데이트
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}