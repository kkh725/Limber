package com.kkh.multimodule.feature.block

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object BlockNavRoutes {
    const val BLOCK = "block"
    const val UNBLOCK_REASON = "unblock_reason"
    const val UNBLOCK_COMPLETE = "unblock_complete"
}

fun NavController.navigateToBlock() =
    navigate(route = BlockNavRoutes.BLOCK)

fun NavController.navigateToUnBlockReason() =
    navigate(route = BlockNavRoutes.UNBLOCK_REASON)

fun NavController.navigateToUnBlockComplete() =
    navigate(route = BlockNavRoutes.UNBLOCK_COMPLETE)

fun NavGraphBuilder.blockScreen(
    onClickUnBlock: () -> Unit,
    onClickContinue: () -> Unit
) {
    composable(BlockNavRoutes.BLOCK) {
        BlockScreen(
            onClickUnBlock = onClickUnBlock,
            onClickContinue = onClickContinue
        )
    }
}

fun NavGraphBuilder.unBlockReasonScreen(
    onClickBack : () -> Unit,
    onNavigateToComplete : () -> Unit
) {
    composable(BlockNavRoutes.UNBLOCK_REASON) {
        UnBlockReasonScreen(onClickBack, onNavigateToComplete)
    }
}

fun NavGraphBuilder.unBlockCompleteScreen(
    onClickCloseScreen : () -> Unit,
    onNavigateToStartTimerNow : () -> Unit
) {
    composable(BlockNavRoutes.UNBLOCK_COMPLETE) {
        UnBlockCompleteScreen(onClickCloseScreen, onNavigateToStartTimerNow)
    }
}