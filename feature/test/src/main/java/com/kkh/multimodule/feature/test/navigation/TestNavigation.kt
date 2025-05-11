package com.kkh.multimodule.feature.test.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kkh.multimodule.feature.test.TestScreen

fun NavController.navigateToTest(navOptions: NavOptions) =
    navigate(route = "test_route", navOptions)

fun NavGraphBuilder.testScreen(
    onClickButtonTonNavigate: () -> Unit
) {
    composable("route") {
        TestScreen(onClickButtonTonNavigate = onClickButtonTonNavigate)
    }
}