package com.kkh.multimodule

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kkh.multimodule.home.HomeRoute
import com.kkh.multimodule.navigation.BottomNavRoutes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(HomeRoute.ROUTE, "홈", Icons.Default.Home)
    // todo route 방식 교체.
    object TIMER : BottomNavItem(BottomNavRoutes.TIMER, "타이머", Icons.Default.Person)
    object LABORATORY : BottomNavItem(BottomNavRoutes.LABORATORY, "실험실", Icons.Default.Add)
    object MORE : BottomNavItem(BottomNavRoutes.MORE, "더보기", Icons.Default.Person)
}

@Composable
fun LimberBottomBar(
    navController: NavHostController,
    onScreenSelected: (String) -> Unit
) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.TIMER,
        BottomNavItem.LABORATORY,
        BottomNavItem.MORE
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
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}