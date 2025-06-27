package com.kkh.multimodule.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object HomeRoute{
    const val ROUTE = "MAIN"
}

fun NavController.navigateToMain() =
    navigate(route = HomeRoute.ROUTE)

fun NavGraphBuilder.homeScreen(
    onClickButtonToNavigate: () -> Unit
) {
    composable(HomeRoute.ROUTE) {
        HomeScreen {  }
    }
}