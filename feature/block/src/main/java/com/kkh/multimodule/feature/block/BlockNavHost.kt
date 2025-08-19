package com.kkh.multimodule.feature.block

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kkh.multimodule.feature.block.block.BlockNavRoutes
import com.kkh.multimodule.feature.block.block.blockScreen
import com.kkh.multimodule.feature.block.block.navigateToUnBlockComplete
import com.kkh.multimodule.feature.block.block.navigateToUnBlockReason
import com.kkh.multimodule.feature.block.block.unBlockCompleteScreen
import com.kkh.multimodule.feature.block.block.unBlockReasonScreen

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
            onClickContinue = onClickCloseScreen
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