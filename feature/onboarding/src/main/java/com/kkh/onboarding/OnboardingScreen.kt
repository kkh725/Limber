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
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navigateToScreenTimePermissionScreen: () -> Unit = {}) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })
    val scope = rememberCoroutineScope()

    val title: String = when (pagerState.currentPage) {
        1 -> "도파민 앱 관리하기"
        2 -> "실험 타이머 시작하기"
        3 -> "실험 결과 확인하기"
        else -> ""
    }
    val description: String = when (pagerState.currentPage) {
        1 -> "집중에 방해되는 앱을 선택해 관리해요"
        2 -> "목표에 집중할 수 있도록 방해되는 앱을 차단해요"
        3 -> "실험이 끝난 후 나의 집중 기록을 확인해요."
        else -> ""
    }

    val imageResource: Int = when (pagerState.currentPage) {
        1 -> R.drawable.ic_no1
        2 -> R.drawable.ic_no2
        3 -> R.drawable.ic_no3
        else -> R.drawable.ic_no1
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            OnBoardingContent(
                currentPage = pagerState.currentPage,
                title = title,
                description = description,
                imageResource =  imageResource
            )
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
fun OnBoardingContent(currentPage: Int, title: String, description: String, imageResource: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
        }
        if (currentPage == 0) {
            OnBoardingContent1()
        } else {
            OnBoardingContent2(title, description, imageResource)
        }
    }
}

@Composable
fun OnBoardingContent1() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = "환영해요!\n" +
                    "림버와 함께 집중 실험을 시작해볼까요?",
            style = LimberTextStyle.Heading3,
            textAlign = TextAlign.Center,
            color = Gray800
        )
    }
}

@Composable
fun OnBoardingContent2(title: String, description: String, imageResource: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(imageResource), contentDescription = null)
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            style = LimberTextStyle.Heading4,
            textAlign = TextAlign.Center,
            color = Gray800
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = description,
            style = LimberTextStyle.Body1,
            textAlign = TextAlign.Center,
            color = Gray600
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOnboarding() {
    OnboardingScreen()
}
