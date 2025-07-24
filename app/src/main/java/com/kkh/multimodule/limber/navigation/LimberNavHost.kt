package com.kkh.multimodule.limber.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.limber.RootViewModel
import com.kkh.multimodule.core.domain.model.ScreenState
import com.kkh.multimodule.feature.laboratory.laboratoryGraph
import com.kkh.multimodule.feature.home.homeNavGraph
import com.kkh.multimodule.feature.home.navigateToActiveTimerScreen
import com.kkh.multimodule.feature.home.navigateToHomeScreen
import com.kkh.multimodule.feature.home.navigateToRecallScreen
import com.kkh.multimodule.feature.onboarding.OnBoardingRoute
import com.kkh.multimodule.limber.intent.RootEvent
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

//    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))

    NavHost(
        navController = navController,
        startDestination = OnBoardingRoute.Onboarding,// HomeRoutes.HOME, //OnBoardingRoute.Onboarding,
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
            onClickBack = navController::popBackStack
        )

        homeNavGraph(
            onNavigateToActiveTimer = navController::navigateToActiveTimerScreen,
            onNavigateToHome = navController::navigateToHomeScreen,
            onPopBackStack = navController::popBackStack,
            onNavigateToRecall = navController::navigateToRecallScreen
        )

        timerNavGraph(onNavigateToActiveTimer = {
            navController.navigateToHomeScreen()
            rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
        })

        laboratoryGraph()

    }
}