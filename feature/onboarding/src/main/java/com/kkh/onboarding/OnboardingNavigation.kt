package com.kkh.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.ui.RightHorizontalEnterAnimation
import com.kkh.permission.AccessPermissionScreen
import com.kkh.permission.ManageAppScreen
import com.kkh.permission.ScreenTimePermissionScreen
import com.kkh.permission.SelectTypeScreen
import com.kkh.permission.StartScreen

object OnBoardingRoute {
    const val Onboarding = "onboarding"
    const val AccessPermission = "access_permission"
    const val ManageApp = "manage_app"
    const val ScreenTimePermission = "screen_time_permission"
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

fun NavController.navigateToSelectTypeScreen() =
    navigate(OnBoardingRoute.SelectType)

fun NavController.navigateToStartScreenScreen() =
    navigate(OnBoardingRoute.StartScreen)

fun NavGraphBuilder.onBoardingNavGraph(
    navigateToScreenTimePermissionScreen: () -> Unit,
    navigateToAccessPermissionScreen: () -> Unit,
    navigateToManageAppScreen: () -> Unit,
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
            AccessPermissionScreen(navigateToManageApp = navigateToManageAppScreen)
        }
    }
    composable(OnBoardingRoute.ManageApp) {
        RightHorizontalEnterAnimation {
            ManageAppScreen(navigateToSelectType = navigateToSelectTypeScreen)
        }
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

