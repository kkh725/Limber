package com.kkh.multimodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.RootViewModel
import com.kkh.multimodule.core.domain.model.ScreenState
import com.kkh.multimodule.home.HomeRoutes
import com.kkh.multimodule.home.homeNavGraph
import com.kkh.multimodule.home.navigateToHomeScreen
import com.kkh.multimodule.intent.RootEvent
import com.kkh.multimodule.feature.timer.navigateToTimer
import com.kkh.multimodule.feature.timer.timerNavGraph
import com.kkh.multimodule.feature.onboarding.navigateToAccessPermissionScreen
import com.kkh.multimodule.feature.onboarding.navigateToStartScreenScreen
import com.kkh.multimodule.feature.onboarding.navigateToManageAppScreen
import com.kkh.multimodule.feature.onboarding.navigateToScreenTimePermissionScreen
import com.kkh.multimodule.feature.onboarding.navigateToSelectTypeScreen
import com.kkh.multimodule.feature.onboarding.onBoardingNavGraph

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    rootViewModel: RootViewModel
) {

    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))

    NavHost(
        navController = navController,
        startDestination = HomeRoutes.HOME,
        modifier = modifier,
    ) {
        onBoardingNavGraph(
            navigateToScreenTimePermissionScreen = navController::navigateToScreenTimePermissionScreen,
            navigateToAccessPermissionScreen = navController::navigateToAccessPermissionScreen,
            navigateToManageAppScreen = navController::navigateToManageAppScreen,
            navigateToSelectTypeScreen = navController::navigateToSelectTypeScreen,
            navigateToStartScreenScreen = navController::navigateToStartScreenScreen,
            navigateToHome = {
                navController.navigateToHomeScreen()
                rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
            },
            onClickBack = {navController.popBackStack()}
        )

        homeNavGraph(onClickButtonToNavigate = navController::navigateToTimer)

        timerNavGraph(onNavigateToActiveTimer = navController::navigateToHomeScreen)

    }
}