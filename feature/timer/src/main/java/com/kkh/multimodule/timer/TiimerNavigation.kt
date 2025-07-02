package com.kkh.multimodule.timer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.util.Timer

object TimerRoute{
    const val ROUTE = "TIMER"
}

fun NavController.navigateToTimer() =
    navigate(route = TimerRoute.ROUTE)

fun NavGraphBuilder.timerScreen(
    onClickStartButton: () -> Unit
) {
    composable(TimerRoute.ROUTE) {
        TimerScreen(onClickStartButton = onClickStartButton)
    }
}