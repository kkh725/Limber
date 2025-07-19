package com.kkh.multimodule.feature.timer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object TimerRoute{
    const val ROUTE = "TIMER"
}

fun NavController.navigateToTimer() =
    navigate(route = TimerRoute.ROUTE)

fun NavGraphBuilder.timerNavGraph(
    onNavigateToActiveTimer: () -> Unit
) {
    composable(TimerRoute.ROUTE) {
        TimerScreen(onNavigateToActiveTimer)
    }
}