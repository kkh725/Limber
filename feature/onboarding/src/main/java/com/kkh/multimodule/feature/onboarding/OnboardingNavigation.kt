package com.kkh.multimodule.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.core.ui.ui.RightHorizontalEnterAnimation
import com.kkh.multimodule.feature.onboarding.contents.ManageAppScreen
import com.kkh.multimodule.feature.onboarding.contents.StartScreen
import com.kkh.multimodule.feature.onboarding.contents.permission.AccessPermissionScreen
import com.kkh.multimodule.feature.onboarding.contents.permission.AlarmPermissionScreen
import com.kkh.multimodule.feature.onboarding.contents.permission.ScreenTimePermissionScreen
import com.kkh.multimodule.feature.onboarding.preview.PreOnboardingScreen1
import com.kkh.multimodule.feature.onboarding.preview.PreOnboardingScreen2

object OnBoardingRoute {
    const val PreOnboarding1 = "PreOnboarding1"
    const val PreOnboarding2 = "PreOnboarding2"
    const val Onboarding = "onboarding"
    const val AccessPermission = "access_permission"
    const val ManageApp = "manage_app"
    const val ScreenTimePermission = "screen_time_permission"
    const val AlertPermission = "alert_permission"
    const val SelectType = "select_type"
    const val StartScreen = "final_onboarding"
}

fun NavController.navigateToPreOnboarding1Screen() =
    navigate(OnBoardingRoute.PreOnboarding1)

fun NavController.navigateToPreOnboarding2Screen() =
    navigate(OnBoardingRoute.PreOnboarding2)

fun NavController.navigateToOnboardingScreen() =
    navigate(OnBoardingRoute.Onboarding)

fun NavController.navigateToAccessPermissionScreen() =
    navigate(OnBoardingRoute.AccessPermission)

fun NavController.navigateToManageAppScreen() =
    navigate(OnBoardingRoute.ManageApp)

fun NavController.navigateToScreenTimePermissionScreen() =
    navigate(OnBoardingRoute.ScreenTimePermission)

fun NavController.navigateToAlertPermissionScreen() =
    navigate(OnBoardingRoute.AlertPermission)

fun NavController.navigateToSelectTypeScreen() =
    navigate(OnBoardingRoute.SelectType)

fun NavController.navigateToStartScreenScreen() =
    navigate(OnBoardingRoute.StartScreen)

fun NavGraphBuilder.onBoardingNavGraph(
    navigateToPreOnboarding2Screen: () -> Unit,
    navigateToOnboardingScreen: () -> Unit,
    navigateToScreenTimePermissionScreen: () -> Unit,
    navigateToAccessPermissionScreen: () -> Unit,
    navigateToManageAppScreen: () -> Unit,
    navigateToAlertPermission: () -> Unit,
    navigateToStartScreenScreen: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    onClickBack: () -> Unit = {}
) {
    composable(OnBoardingRoute.PreOnboarding1) {
        PreOnboardingScreen1(onNavigateToNextScreen = navigateToPreOnboarding2Screen)
    }
    composable(OnBoardingRoute.PreOnboarding2) {
        PreOnboardingScreen2(onNavigateToOnboarding = navigateToOnboardingScreen)
    }

    composable(OnBoardingRoute.Onboarding) {
        OnboardingScreen(navigateToScreenTimePermissionScreen = navigateToScreenTimePermissionScreen)
    }
    composable(OnBoardingRoute.ScreenTimePermission) {
        RightHorizontalEnterAnimation {
            ScreenTimePermissionScreen(
                onClickBack = onClickBack,
                navigateToAccessPermission = navigateToAccessPermissionScreen
            )
        }
    }
    composable(OnBoardingRoute.AccessPermission) {
        RightHorizontalEnterAnimation {
            AccessPermissionScreen(
                navigateToAlertPermission = navigateToAlertPermission,
                onClickBack = onClickBack
            )
        }
        // 👇 여기에 BackHandler 추가
        BackHandler(enabled = true) {}
    }
    composable(OnBoardingRoute.AlertPermission) {
        AlarmPermissionScreen(navigateToManageApp = navigateToManageAppScreen)
        BackHandler(enabled = true) {}
    }
    composable(OnBoardingRoute.ManageApp) {
        RightHorizontalEnterAnimation {
            ManageAppScreen(navigateToStart = navigateToStartScreenScreen)
        }
        BackHandler(enabled = true) {}
    }
    composable(OnBoardingRoute.StartScreen) {
        RightHorizontalEnterAnimation {
            StartScreen(navigateToHome = navigateToHome, onClickBack = onClickBack)
        }
    }
}

