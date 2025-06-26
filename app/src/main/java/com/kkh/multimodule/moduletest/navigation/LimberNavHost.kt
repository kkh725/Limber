package com.kkh.multimodule.moduletest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.feature.test.navigation.TestRoute
import com.kkh.multimodule.feature.test.navigation.navigateToTest
import com.kkh.multimodule.feature.test.navigation.testScreen

@Composable
fun LimberNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TestRoute.ROUTE,
        modifier = modifier,
    ) {
        testScreen (onClickButtonToNavigate = navController::navigateToTest)
    }
}