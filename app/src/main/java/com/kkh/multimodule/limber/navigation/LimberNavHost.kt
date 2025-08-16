package com.kkh.multimodule.limber.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.limber.RootViewModel
import com.kkh.multimodule.core.domain.ScreenState
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.feature.home.HomeRoutes
import com.kkh.multimodule.feature.laboratory.laboratoryGraph
import com.kkh.multimodule.feature.home.homeNavGraph
import com.kkh.multimodule.feature.home.navigateToActiveTimerScreen
import com.kkh.multimodule.feature.home.navigateToHomeScreen
import com.kkh.multimodule.feature.home.navigateToRecallScreen
import com.kkh.multimodule.feature.laboratory.LaboratoryRoutes
import com.kkh.multimodule.feature.onboarding.OnBoardingRoute
import com.kkh.multimodule.limber.intent.RootEvent
import com.kkh.multimodule.feature.timer.timerNavGraph
import com.kkh.multimodule.feature.onboarding.navigateToAccessPermissionScreen
import com.kkh.multimodule.feature.onboarding.navigateToAlertPermissionScreen
import com.kkh.multimodule.feature.onboarding.navigateToStartScreenScreen
import com.kkh.multimodule.feature.onboarding.navigateToManageAppScreen
import com.kkh.multimodule.feature.onboarding.navigateToScreenTimePermissionScreen
import com.kkh.multimodule.feature.onboarding.navigateToSelectTypeScreen
import com.kkh.multimodule.feature.onboarding.onBoardingNavGraph
import com.kkh.multimodule.feature.timer.TimerRoute
import com.kkh.multimodule.feature.timer.navigateToTimer

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    rootViewModel: RootViewModel
) {
    val onPopBackStack: () -> Unit = {
        navController.popBackStack()
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        when (currentRoute) {
            HomeRoutes.HOME -> rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
            TimerRoute.ROUTE -> rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.TIMER_SCREEN))
            LaboratoryRoutes.LABORATORY -> rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.LABORATORY_SCREEN))
            else -> rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.NONE_SCREEN))
        }
    }

    val uiState by rootViewModel.uiState.collectAsState()
    val isOnboardingChecked = uiState.isOnboardingChecked


    LaunchedEffect(Unit) {
        rootViewModel.sendEvent(RootEvent.Init)
    }

    if (isOnboardingChecked == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Image(painterResource(R.drawable.ic_splash), contentDescription = null)
        }
    } else {
        val startDestination = if (isOnboardingChecked) HomeRoutes.HOME else OnBoardingRoute.Onboarding

        NavHost(
            navController = navController,
            startDestination = startDestination,// HomeRoutes.HOME, //OnBoardingRoute.Onboarding,
            modifier = modifier,
        ) {
            onBoardingNavGraph(
                navigateToScreenTimePermissionScreen = navController::navigateToScreenTimePermissionScreen,
                navigateToAccessPermissionScreen = navController::navigateToAccessPermissionScreen,
                navigateToAlertPermission = navController::navigateToAlertPermissionScreen,
                navigateToManageAppScreen = navController::navigateToManageAppScreen,
                navigateToStartScreenScreen = navController::navigateToStartScreenScreen,
                navigateToHome = {
                    navController.navigateToHomeScreen()
                    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
                },
                onClickBack = onPopBackStack
            )

            homeNavGraph(
                onNavigateToActiveTimer = { leftTime, timerId ->
                    navController.navigateToActiveTimerScreen(leftTime = leftTime, timerId = timerId)
                    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.NONE_SCREEN))
                },
                onNavigateToHome = {
                    navController.navigateToHomeScreen()
                    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
                },
                onPopBackStack = onPopBackStack,
                onNavigateToSetTimer = {
                    rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.TIMER_SCREEN))
                    navController.navigateToTimer()
                },
                onNavigateToRecall = navController::navigateToRecallScreen
            )

            timerNavGraph(onNavigateToActiveHome = {
                navController.navigateToHomeScreen()
                rootViewModel.sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
            }, onNavigateToHome = navController::navigateToHomeScreen)

            laboratoryGraph()

        }
    }

}

