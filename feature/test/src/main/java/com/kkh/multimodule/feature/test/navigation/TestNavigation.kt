package com.kkh.multimodule.feature.test.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kkh.multimodule.feature.test.TestScreen

object TestRoute{
    const val ROUTE = "test"
}

fun NavController.navigateToTest() =
    navigate(route = TestRoute.ROUTE)

fun NavGraphBuilder.testScreen(
    onClickButtonToNavigate: () -> Unit
) {
    composable(TestRoute.ROUTE) {
        TestScreen(onClickButtonTonNavigate = onClickButtonToNavigate)
    }
}