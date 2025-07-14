package com.kkh.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.permission.AccessPermissionScreen
import com.kkh.permission.ManageAppScreen
import com.kkh.permission.ScreenTimePermissionScreen
import com.kkh.permission.SelectTypeScreen
object OnBoardingRoute {
    const val Onboarding = "onboarding"
    const val AccessPermission = "access_permission"
    const val ManageApp = "manage_app"
    const val ScreenTimePermission = "screen_time_permission"
    const val SelectType = "select_type"
    const val FinalOnboarding = "final_onboarding"
}

fun NavController.navigateToOnboardingScreen() =
    navigate(OnBoardingRoute.Onboarding)

fun NavController.navigateToAccessPermissionScreen() =
    navigate(OnBoardingRoute.AccessPermission)

fun NavController.navigateToManageAppScreen() =
    navigate(OnBoardingRoute.ManageApp)

fun NavController.navigateToScreenTimePermissionScreen() =
    navigate(OnBoardingRoute.ScreenTimePermission)

fun NavController.navigateToSelectTypeScreen() =
    navigate(OnBoardingRoute.SelectType)

fun NavController.navigateToFinalOnboardingScreen() =
    navigate(OnBoardingRoute.FinalOnboarding)

fun NavGraphBuilder.onBoardingNavGraph(
    navigateToScreenTimePermissionScreen: () -> Unit,
    navigateToAccessPermissionScreen: () -> Unit,
    navigateToManageAppScreen: () -> Unit,
    navigateToSelectTypeScreen: () -> Unit = {},
    navigateToFinalOnboardingScreen: () -> Unit = {}
) {
    composable(OnBoardingRoute.Onboarding) {
        OnboardingScreen(navigateToScreenTimePermissionScreen = navigateToScreenTimePermissionScreen)
    }
    composable(OnBoardingRoute.ScreenTimePermission) {
        ScreenTimePermissionScreen(navigateToAccessPermission = navigateToAccessPermissionScreen)
    }
    composable(OnBoardingRoute.AccessPermission) {
        AccessPermissionScreen(navigateToManageApp = navigateToManageAppScreen)
    }
    composable(OnBoardingRoute.ManageApp) {
        ManageAppScreen()
    }
    composable(OnBoardingRoute.SelectType) {
        SelectTypeScreen()
    }
    composable(OnBoardingRoute.FinalOnboarding) {
        OnboardingScreen()
    }
}

