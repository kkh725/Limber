package com.kkh.multimodule.feature.onboarding.pre

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import kotlinx.coroutines.delay

@Preview
@Composable
fun PreOnboardingScreen1(onNavigateToNextScreen: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(1000)
        onNavigateToNextScreen()
    }
    Box(Modifier.fillMaxSize().background(LimberColorStyle.Primary_Main)) {
        Image(
            painter = painterResource(R.drawable.onboarding_pre_1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PreOnboardingScreen2(onNavigateToOnboarding: () -> Unit = {}) {
    Box(Modifier.fillMaxSize().background(LimberColorStyle.Primary_Main)) {
        Image(
            painter = painterResource(R.drawable.onboarding_pre_2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.align(Alignment.Center)
        )
        LimberGradientButton(
            onClick = onNavigateToOnboarding,
            text = "집중 실험 알아보기",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(20.dp)
        )
    }

}