package com.kkh.multimodule.feature.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.feature.onboarding.OnboardingEvent
import com.kkh.multimodule.feature.onboarding.OnboardingViewModel


@Composable
fun StartScreen(navigateToHome : () -> Unit = {}, onClickBack : () -> Unit = {}){

    val viewModel : OnboardingViewModel = hiltViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(Modifier.padding(vertical = 20.dp), onClickBack = onClickBack)
        Spacer(Modifier.height(66.dp))
        Text(
            "이제 림버와 함께\n" +
                    "집중 실험을 시작할 수 있어요!!",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(90.dp))
        Image(painter = painterResource(R.drawable.onboarding_start), contentDescription = null)
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LimberGradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigateToHome()
                    viewModel.sendEvent(OnboardingEvent.OnCompleteOnBoarding)
                },
                text = "시작하기"
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen({})
}