package com.kkh.multimodule.limber

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kkh.more.MoreRoute
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.feature.laboratory.LaboratoryRoutes
import com.kkh.multimodule.feature.timer.TimerRoute
import com.kkh.multimodule.feature.home.HomeRoutes
import com.kkh.multimodule.limber.navigation.BottomNavRoutes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomNavItem(HomeRoutes.HOME, "홈", R.drawable.ic_bottom_home)
    object TIMER : BottomNavItem(TimerRoute.ROUTE, "타이머", R.drawable.ic_bottom_timer)
    object LABORATORY :
        BottomNavItem(LaboratoryRoutes.LABORATORY, "실험실", R.drawable.ic_bottom_experiment)

    object MORE : BottomNavItem(MoreRoute.MORE, "더보기", R.drawable.ic_bottom_more)
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

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        HorizontalDivider(thickness = 1.dp, color = Gray200)
        NavigationBar(modifier = modifier.height(61.dp), containerColor = Gray50) {
            screens.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = screen.title,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            style = LimberTextStyle.Body3,
                            modifier = Modifier.offset(y = (-2).dp)
                        )
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {

                            navController.navigate(screen.route)
                            onScreenSelected(screen.route) // 🔥 상태 업데이트
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LimberColorStyle.Primary_Dark,
                        selectedTextColor = LimberColorStyle.Primary_Dark,
                        unselectedIconColor = Gray400,
                        unselectedTextColor = Gray400,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}