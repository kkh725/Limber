package com.kkh.multimodule.feature.home

import android.R.attr.defaultValue
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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

// ✅ leftTime + timerId 전달 가능
fun NavController.navigateToActiveTimerScreen(leftTime: String, timerId: Int) {
    val encodedTime = Uri.encode(leftTime) // 공백/특수문자 안전하게 인코딩
    navigate("${HomeRoutes.ACTIVE_TIMER}?leftTime=$encodedTime&timerId=$timerId") {
        launchSingleTop = true
    }
}

fun NavController.navigateToRecallScreen() =
    navigate(HomeRoutes.RECALL)

fun NavController.navigateToRecallScreen(timerId: Int, timerHistoryId: Int,) {
    navigate("${HomeRoutes.RECALL}?timerId=$timerId&timerHistoryId=$timerHistoryId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeNavGraph(
    onNavigateToActiveTimer: (String, Int) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRecall: () -> Unit,
    onNavigateToSetTimer: () -> Unit,
    onNavigateToReport: () -> Unit,
    onPopBackStack: () -> Unit
) {
    composable(HomeRoutes.HOME) {
        HomeScreen(onNavigateToActiveTimer = onNavigateToActiveTimer,
            onNavigateToSetTimer = onNavigateToSetTimer)
        BackHandler(enabled = true) {
            onPopBackStack()
        }
    }
    composable(
        route = "${HomeRoutes.ACTIVE_TIMER}?leftTime={leftTime}&timerId={timerId}",
        arguments = listOf(
            navArgument("leftTime") {
                type = NavType.StringType
                defaultValue = "00:00:00"
            },
            navArgument("timerId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val leftTime = backStackEntry.arguments?.getString("leftTime") ?: "00:00:00"
        val timerId = backStackEntry.arguments?.getInt("timerId") ?: -1

        ActiveTimerScreen(
            leftTime = leftTime,
            timerId = timerId,
            onPopBackStack = onPopBackStack,
            onNavigateToHome = onNavigateToHome,
            onNavigateToRecall = onNavigateToRecall
        )

        BackHandler(enabled = true) {
            onPopBackStack()
        }
    }

    composable(HomeRoutes.RECALL) {
        RecallScreen(
            onPopBackStack = onPopBackStack,
            onNavigateToHome = onNavigateToHome,
            onNavigateToReport = onNavigateToReport
        )
        BackHandler(enabled = true) {
            onPopBackStack()
        }
    }

    composable(
        route = "${HomeRoutes.RECALL}?timerId={timerId}&timerHistoryId={timerHistoryId}",
        deepLinks = listOf(navDeepLink {
            uriPattern = "limber://recall?timerId={timerId}&timerHistoryId={timerHistoryId}"
        }),
        arguments = listOf(
            navArgument("timerId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("timerHistoryId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) { backStackEntry ->
        val timerId = backStackEntry.arguments?.getInt("timerId") ?: -1
        val timerHistoryId = backStackEntry.arguments?.getInt("timerHistoryId") ?: -1

        RecallScreen(
            timerId = timerId,
            timerHistoryId = timerHistoryId,
            onPopBackStack = onPopBackStack,
            onNavigateToHome = onNavigateToHome,
            onNavigateToReport = onNavigateToReport
        )
        BackHandler(enabled = true) {
            onPopBackStack()
        }
    }

}
