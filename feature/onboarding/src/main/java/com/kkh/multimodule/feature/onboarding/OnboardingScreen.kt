package com.kkh.multimodule.feature.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navigateToScreenTimePermissionScreen: () -> Unit = {}) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val title: String = when (pagerState.currentPage) {
        0 -> "집중에 방해되는\n" +
                "도파민 앱을 선택하고"
        1 -> "타이머를 설정해\n" +
                "집중 실험을 시작해요"
        2 -> "실험이 끝나면\n" +
                "집중 기록을 확인해요"
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
                .weight(1f)
        ) { page ->
            OnBoardingContent(
                currentPage = pagerState.currentPage,
                title = title,
            )
        }

        // 아래 점(Indicator)
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
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
fun OnBoardingContent(currentPage: Int, title: String) {

    val onBoardingBg = when(currentPage){
        0 -> R.drawable.bg_onboarding1
        1 -> R.drawable.bg_onboarding2
        2 -> R.drawable.bg_onboarding3
        else -> R.drawable.bg_onboarding1
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Image(
                painter = painterResource(onBoardingBg),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )
        }
        OnBoardingContent1(title)
    }
}

@Composable
fun OnBoardingContent1(text : String) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = text,
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
