package com.kkh.multimodule.feature.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.feature.home.activeTimer.ActiveTimerScreen
import com.kkh.multimodule.feature.home.recall.RecallScreen

object HomeRoutes {
    const val HOME = "home"
    const val ACTIVE_TIMER = "active_timer"
    const val RECALL = "recall"
}

fun NavController.navigateToHomeScreen() {
    navigate(HomeRoutes.HOME) {
        popUpTo(0) { inclusive = true }  // 스택 전체 제거
        launchSingleTop = true           // 동일 화면 중복 방지
    }
}

fun NavController.navigateToActiveTimerScreen() =
    navigate(HomeRoutes.ACTIVE_TIMER)

fun NavController.navigateToRecallScreen() =
    navigate(HomeRoutes.RECALL)

fun NavGraphBuilder.homeNavGraph(
    onNavigateToActiveTimer: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRecall: () -> Unit,
    onPopBackStack: () -> Unit
) {
    composable(HomeRoutes.HOME) {
        HomeScreen(onNavigateToActiveTimer = onNavigateToActiveTimer)
    }
    composable(HomeRoutes.ACTIVE_TIMER) {
        ActiveTimerScreen(
            onPopBackStack = onPopBackStack,
            onNavigateToHome = onNavigateToHome,
            onNavigateToRecall = onNavigateToRecall
        )
    }
    composable(HomeRoutes.RECALL) {
        RecallScreen(
            onPopBackStack = onPopBackStack,
            onNavigateToHome = onNavigateToHome
        )
    }
}
