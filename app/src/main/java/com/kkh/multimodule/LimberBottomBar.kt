package com.kkh.multimodule

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kkh.multimodule.home.HomeRoute
import com.kkh.multimodule.navigation.BottomNavRoutes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomNavItem(HomeRoute.ROUTE, "홈", R.drawable.ic_home)
    object TIMER : BottomNavItem(BottomNavRoutes.TIMER, "타이머", R.drawable.ic_timer)
    object LABORATORY : BottomNavItem(BottomNavRoutes.LABORATORY, "실험실", R.drawable.ic_laboratory)
    object MORE : BottomNavItem(BottomNavRoutes.MORE, "더보기", R.drawable.ic_more)
}

@Composable
fun LimberBottomBar(
    modifier: Modifier = Modifier,
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

    NavigationBar(modifier = modifier.height(61.dp)) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.title,
                        modifier = Modifier.size(28.dp)
                    )
                },
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