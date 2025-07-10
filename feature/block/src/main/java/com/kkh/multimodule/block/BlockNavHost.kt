package com.kkh.multimodule.block

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun BlockNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = BlockRoute.ROUTE,
        modifier = modifier,
    ) {
        blockScreen(onClickUnBlock = navController::navigateToUnBlockReason, onClickContinue = {})
        unBlockReasonScreen(
            onClickBack = { navController.popBackStack() },
            onNavigateToComplete = navController::navigateToUnBlockComplete
        )
        unBlockCompleteScreen()
    }
}