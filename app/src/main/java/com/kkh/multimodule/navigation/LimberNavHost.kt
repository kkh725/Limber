package com.kkh.multimodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.home.HomeRoute
import com.kkh.multimodule.home.homeScreen
import com.kkh.multimodule.home.navigateToMain
import com.kkh.multimodule.timer.TimerRoute
import com.kkh.multimodule.timer.timerScreen

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TimerRoute.ROUTE,
        modifier = modifier,
    ) {
//        testScreen (onClickButtonToNavigate = navController::navigateToMain)
        homeScreen (onClickButtonToNavigate = navController::navigateToMain)
        timerScreen (onClickButtonToNavigate = navController::navigateToMain)

    }
}