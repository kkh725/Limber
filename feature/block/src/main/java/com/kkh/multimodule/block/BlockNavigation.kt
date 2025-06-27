package com.kkh.multimodule.block

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object BlockRoute {
    const val ROUTE = "BLOCK"
}

fun NavController.navigateToBlock() =
    navigate(route = BlockRoute.ROUTE)

fun NavGraphBuilder.blockScreen(
    onClickUnBlock: () -> Unit,
    onClickContinue: () -> Unit
) {
    composable(BlockRoute.ROUTE) {
        BlockScreen(
            onClickUnBlock = onClickUnBlock,
            onClickContinue = onClickContinue
        )
    }
}