package com.kkh.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkh.multimodule.core.ui.R

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navigateToScreenTimePermissionScreen: () -> Unit = {}){
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })
    val scope = rememberCoroutineScope()

    val text: String = when (pagerState.currentPage) {
        0 -> "집중 회복 실험실에 오신 걸 환영해요!\n" +
                "림버와 함께 집중력을 되찾아볼까요?"
        1 -> "방해되는 앱을 설정하고"
        2 -> "타이머로 집중 실험을 시작해요"
        3 -> "끝나면 리포트와 홈에서\n" +
                    "결과를 확인할 수 있어요"
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 화면의 대부분을 차지
        ) { page ->
            OnBoardingContent(text)
        }

        // 아래 점(Indicator)
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (isSelected) LimberColorStyle.Primary_Main else Gray300,
                            shape = CircleShape
                        )
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            LimberGradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (pagerState.currentPage != pagerState.pageCount - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navigateToScreenTimePermissionScreen()
                    }
                },
                text = "다음으로"
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun OnBoardingContent(text: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
        Spacer(Modifier.height(130.dp))
        Text(
            text = text,
            style = LimberTextStyle.Heading3,
            textAlign = TextAlign.Center,
            color = Gray800
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding() {
    OnboardingScreen()
}
