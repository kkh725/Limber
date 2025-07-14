package com.kkh.multimodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.RootViewModel
import com.kkh.multimodule.domain.model.ScreenState
import com.kkh.multimodule.home.HomeRoutes
import com.kkh.multimodule.home.homeNavGraph
import com.kkh.multimodule.home.navigateToHomeScreen
import com.kkh.multimodule.intent.RootEvent
import com.kkh.multimodule.timer.navigateToTimer
import com.kkh.multimodule.timer.timerNavGraph
import com.kkh.onboarding.OnBoardingRoute
import com.kkh.onboarding.navigateToAccessPermissionScreen
import com.kkh.onboarding.navigateToStartScreenScreen
import com.kkh.onboarding.navigateToManageAppScreen
import com.kkh.onboarding.navigateToScreenTimePermissionScreen
import com.kkh.onboarding.navigateToSelectTypeScreen
import com.kkh.onboarding.onBoardingNavGraph

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    rootViewModel: RootViewModel
) {
    NavHost(
        navController = navController,
        startDestination = OnBoardingRoute.Onboarding,
        modifier = modifier,
    ) {
        homeNavGraph(onClickButtonToNavigate = navController::navigateToTimer)
        timerNavGraph(onNavigateToActiveTimer = navController::navigateToHomeScreen)
        onBoardingNavGraph(
            navigateToScreenTimePermissionScreen = navController::navigateToScreenTimePermissionScreen,
            navigateToAccessPermissionScreen = navController::navigateToAccessPermissionScreen,
            navigateToManageAppScreen = navController::navigateToManageAppScreen,
            navigateToSelectTypeScreen = navController::navigateToSelectTypeScreen,
            navigateToStartScreenScreen = navController::navigateToStartScreenScreen,
            navigateToHome = {
                navController.navigateToHomeScreen()
                rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
            }
        )
    }
}