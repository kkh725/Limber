package com.kkh.multimodule.block

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun BlockNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onClickCloseScreen: () -> Unit,
    onNavigateToStartTimerNow : () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BlockNavRoutes.BLOCK,
        modifier = modifier,
    ) {
        blockScreen(
            onClickUnBlock = navController::navigateToUnBlockReason,
            onClickContinue = {}
        )
        unBlockReasonScreen(
            onClickBack = { navController.popBackStack() },
            onNavigateToComplete = navController::navigateToUnBlockComplete
        )
        unBlockCompleteScreen(
            onClickCloseScreen = onClickCloseScreen,
            onNavigateToStartTimerNow = onNavigateToStartTimerNow
        )
    }
}