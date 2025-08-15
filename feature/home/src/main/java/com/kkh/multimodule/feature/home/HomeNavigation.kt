package com.kkh.multimodule.feature.home

import android.R.attr.defaultValue
import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kkh.multimodule.feature.home.activeTimer.ActiveTimerScreen
import com.kkh.multimodule.feature.home.recall.RecallScreen

object HomeRoutes {
    const val HOME = "home"
    const val ACTIVE_TIMER = "active_timer"
    const val RECALL = "recall"
    // 파라미터 포함 경로 템플릿
    const val ACTIVE_TIMER_WITH_PARAM = "$ACTIVE_TIMER?leftTime={leftTime}"

}

fun NavController.navigateToHomeScreen() {
    navigate(HomeRoutes.HOME) {
        popUpTo(0) { inclusive = true }  // 스택 전체 제거
        launchSingleTop = true           // 동일 화면 중복 방지
    }
}

// ✅ leftTime 전달 가능
fun NavController.navigateToActiveTimerScreen(leftTime: String) {
    val encodedTime = Uri.encode(leftTime) // 공백/특수문자 안전하게 인코딩
    navigate("${HomeRoutes.ACTIVE_TIMER}?leftTime=$encodedTime") {
        launchSingleTop = true
    }
}

fun NavController.navigateToRecallScreen() =
    navigate(HomeRoutes.RECALL)

fun NavGraphBuilder.homeNavGraph(
    onNavigateToActiveTimer: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRecall: () -> Unit,
    onNavigateToSetTimer: () -> Unit,
    onPopBackStack: () -> Unit
) {
    composable(HomeRoutes.HOME) {
        HomeScreen(onNavigateToActiveTimer = onNavigateToActiveTimer,
            onNavigateToSetTimer = onNavigateToSetTimer)
    }
    composable(
        route = "${HomeRoutes.ACTIVE_TIMER}?leftTime={leftTime}",
        arguments = listOf(navArgument("leftTime") {
            type = NavType.StringType
            defaultValue = "00:00:00" // leftTime 없을 때 기본값
        })
    ) { backStackEntry ->
        val leftTime = backStackEntry.arguments?.getString("leftTime") ?: "00:00:00"

        ActiveTimerScreen(
            leftTime = leftTime,
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
