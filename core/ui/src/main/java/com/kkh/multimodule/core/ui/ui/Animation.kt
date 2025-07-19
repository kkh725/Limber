package com.kkh.multimodule.core.ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun RightHorizontalEnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(false).apply { targetState = true },
        enter = slideInHorizontally(
            initialOffsetX = { it } // 왼쪽에서 시작 (음수 → 화면 왼쪽 바깥)
        ) + expandHorizontally(
            expandFrom = Alignment.Start
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutHorizontally(
            targetOffsetX = { it } // 왼쪽으로 나감
        ) + shrinkHorizontally() + fadeOut(),
    ) {
        content()
    }
}