package com.kkh.multimodule.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.timer.ActiveTimerScreen

object HomeRoutes {
    const val HOME = "home"
    const val ACTIVE_TIMER = "active_timer"
}

fun NavController.navigateToHomeScreen() =
    navigate(HomeRoutes.HOME)

fun NavController.navigateToActiveTimerScreen() =
    navigate(HomeRoutes.ACTIVE_TIMER)

fun NavGraphBuilder.homeNavGraph(
    onClickButtonToNavigate: () -> Unit
) {
    composable(HomeRoutes.HOME) {
        HomeScreen(onClickButtonToNavigate)
    }
    composable(HomeRoutes.ACTIVE_TIMER) {
        ActiveTimerScreen()
    }
}
