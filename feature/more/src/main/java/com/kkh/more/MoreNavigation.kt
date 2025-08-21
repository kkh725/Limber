package com.kkh.more

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.core.ui.ui.RightHorizontalEnterAnimation

object MoreRoute {
    const val MORE = "more"
    const val FOCUS_STATE = "focus_state"
}

fun NavController.navigateMore() =
    navigate(MoreRoute.MORE)

fun NavController.navigateFocusState() =
    navigate(MoreRoute.FOCUS_STATE)

fun NavGraphBuilder.moreNavGraph(
    navigateFocusState: () -> Unit
) {
    composable(route = MoreRoute.MORE){
        MoreScreen(
            onNavigateToFocusState = navigateFocusState
        )
    }
}

