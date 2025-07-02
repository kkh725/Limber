package com.kkh.multimodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.RootViewModel
import com.kkh.multimodule.home.HomeRoute
import com.kkh.multimodule.home.homeScreen
import com.kkh.multimodule.home.navigateToMain
import com.kkh.multimodule.intent.RootEvent
import com.kkh.multimodule.timer.TimerRoute
import com.kkh.multimodule.timer.timerScreen

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    rootViewModel: RootViewModel
) {
    NavHost(
        navController = navController,
        startDestination = TimerRoute.ROUTE,
        modifier = modifier,
    ) {
        homeScreen(onClickButtonToNavigate = navController::navigateToMain)
        timerScreen()
    }
}