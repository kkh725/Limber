package com.kkh.multimodule.timer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.home.HomeScreen

object ActiveTimerRoute{
    const val ROUTE = "ACTIVE_TIMER"
}

fun NavController.navigateToActiveTimerScreen() =
    navigate(route = ActiveTimerRoute.ROUTE)

fun NavGraphBuilder.activeTimerScreen(
) {
    composable(ActiveTimerRoute.ROUTE) {
        ActiveTimerScreen()
    }
}