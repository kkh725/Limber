package com.kkh.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object OnBoardingRoute{
    const val ROUTE = "MAIN"
}

fun NavController.navigateToOnBoarding() =
    navigate(route = OnBoardingRoute.ROUTE)

fun NavGraphBuilder.onBoardingScreen(
) {
    composable(OnBoardingRoute.ROUTE) {
        OnboardingScreen()
    }
}