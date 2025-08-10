package com.kkh.multimodule.feature.laboratory

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.feature.laboratory.recall.ReportPagerContent
import com.kkh.multimodule.feature.laboratory.report.RecallPagerContent
import kotlinx.coroutines.launch

@Composable
fun LaboratoryScreen() {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    var currentPageType by remember { mutableStateOf(LaboratoryScreenType.Report) }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> currentPageType = LaboratoryScreenType.Report
            1 -> currentPageType = LaboratoryScreenType.Record
        }
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        LaboratoryScreenTopBar(
            modifier = Modifier,
            selectedTimerType = currentPageType,
            onClickStartNowBtn = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            onClickReservationBtn = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            })

        HorizontalPager(state = pagerState) {
            when (it) {
                0 -> ReportPagerContent()
                1 -> RecallPagerContent()
            }
        }
    }
}


@Composable
fun LaboratoryScreenTopBar(
    modifier: Modifier = Modifier,
    selectedTimerType: LaboratoryScreenType,
    onClickStartNowBtn: () -> Unit,
    onClickReservationBtn: () -> Unit
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(104.dp)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f), contentAlignment = Alignment.BottomStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "림버의 실험실",
                        style = LimberTextStyle.Heading3,
                        color = Gray800,
                    )
                    Image(
                        painter = painterResource(com.kkh.multimodule.core.ui.R.drawable.ic_info),
                        contentDescription = "Info"
                    )
                }
            }
        }
        Row(
            modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LaboratorySelectorButton(
                modifier = Modifier.weight(1f),
                text = "주간 리포트",
                isSelected = selectedTimerType == LaboratoryScreenType.Report,
                onClick = onClickStartNowBtn
            )
            LaboratorySelectorButton(
                modifier = Modifier.weight(1f),
                text = "실험 회고",
                isSelected = selectedTimerType == LaboratoryScreenType.Record,
                onClick = onClickReservationBtn
            )
        }
    }

}

@Composable
fun LaboratorySelectorButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) Gray800 else Gray400
    val borderColor = if (isSelected) LimberColorStyle.Primary_Main else Gray300

    Box(
        modifier
            .fillMaxHeight()
            .clickable(
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            style = LimberTextStyle.Heading5,
            color = textColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )

        HorizontalDivider(
            color = borderColor,
            thickness = if (isSelected) 2.dp else 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LaboratoryScreenPreview() {
    LaboratoryScreen()
}