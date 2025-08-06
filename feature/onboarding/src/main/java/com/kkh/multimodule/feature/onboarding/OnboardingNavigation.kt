package com.kkh.multimodule.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.core.ui.ui.RightHorizontalEnterAnimation
import com.kkh.multimodule.feature.permission.AccessPermissionScreen
import com.kkh.multimodule.feature.permission.AlarmPermissionScreen
import com.kkh.multimodule.feature.permission.ManageAppScreen
import com.kkh.multimodule.feature.permission.ScreenTimePermissionScreen
import com.kkh.multimodule.feature.permission.SelectTypeScreen
import com.kkh.multimodule.feature.permission.StartScreen

object OnBoardingRoute {
    const val Onboarding = "onboarding"
    const val AccessPermission = "access_permission"
    const val ManageApp = "manage_app"
    const val ScreenTimePermission = "screen_time_permission"
    const val AlertPermission = "alert_permission"
    const val SelectType = "select_type"
    const val StartScreen = "final_onboarding"
}

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
    navigateToScreenTimePermissionScreen: () -> Unit,
    navigateToAccessPermissionScreen: () -> Unit,
    navigateToManageAppScreen: () -> Unit,
    navigateToAlertPermission : () -> Unit,
    navigateToSelectTypeScreen: () -> Unit = {},
    navigateToStartScreenScreen: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    onClickBack: () -> Unit = {}
) {

    composable(OnBoardingRoute.Onboarding) {
        OnboardingScreen(navigateToScreenTimePermissionScreen = navigateToScreenTimePermissionScreen)
    }
    composable(OnBoardingRoute.ScreenTimePermission) {
        RightHorizontalEnterAnimation {
            ScreenTimePermissionScreen(navigateToAccessPermission = navigateToAccessPermissionScreen)
        }
    }
    composable(OnBoardingRoute.AccessPermission) {
        RightHorizontalEnterAnimation {
            AccessPermissionScreen(navigateToAlertPermission = navigateToAlertPermission)
        }
        // 👇 여기에 BackHandler 추가
        BackHandler(enabled = true){}
    }
    composable(OnBoardingRoute.AlertPermission){
        AlarmPermissionScreen(navigateToManageApp = navigateToManageAppScreen)
        BackHandler(enabled = true){}
    }
    composable(OnBoardingRoute.ManageApp) {
        RightHorizontalEnterAnimation {
            ManageAppScreen(navigateToSelectType = navigateToSelectTypeScreen)
        }
        BackHandler(enabled = true){}
    }
    composable(OnBoardingRoute.SelectType) {
        RightHorizontalEnterAnimation {
            SelectTypeScreen(
                navigateToStart = navigateToStartScreenScreen,
                onClickBack = onClickBack
            )
        }
    }
    composable(OnBoardingRoute.StartScreen) {
        RightHorizontalEnterAnimation {
            StartScreen(navigateToHome = navigateToHome, onClickBack = onClickBack)
        }
    }
}

