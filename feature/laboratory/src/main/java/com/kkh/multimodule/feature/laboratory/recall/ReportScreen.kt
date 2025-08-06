package com.kkh.multimodule.feature.laboratory.recall

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberColumnChart
import com.kkh.multimodule.core.ui.ui.component.LimberRoundButton
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton
import com.kkh.multimodule.core.ui.ui.component.LimberText
import com.kkh.multimodule.core.ui.ui.component.TextSwitch

@Preview(showBackground = true)
@Composable
fun ReportPagerContent() {

    Column {
        ReportContent()
    }
}

@Composable
fun ReportContent() {

    var isChecked by remember { mutableStateOf(false) }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(40.dp))
            LimberText("총 실험 시간", style = LimberTextStyle.Heading4, color = Gray600)
            Spacer(Modifier.height(2.dp))
            LimberText("10시간 20분", style = LimberTextStyle.Heading1, color = Gray800)

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                LimberText("2025년 06월 23일-29일", style = LimberTextStyle.Heading4, color = Gray500)
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_small),
                        contentDescription = null,
                        tint = Gray600
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = null,
                        tint = Gray600
                    )
                }
            }
            Spacer(Modifier.height(18.dp))

            LimberColumnChart()

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextSwitch(selected = isChecked, onSelectedChange = { isChecked = it })
            }
            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth()) {
                WeeklyFocusCard(
                    text = "평균 집중 시간",
                    timerOrPercent = "10시간 20분",
                    imageResId = R.drawable.ic_time,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(12.dp))
                WeeklyFocusCard(
                    text = "평균 집중 몰입도",
                    timerOrPercent = "49%",
                    imageResId = R.drawable.ic_fire,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(20.dp))

            ThisWeekTopGoalCard(
                highlight = "학습",
                subText = "전체 집중 시간의 50%를 차지했어요.",
                infos = listOf(
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "학습",
                        duration = "4시간 12분",
                        percent = 0.5f,
                        barColor = LimberColorStyle.Primary_Main
                    ),
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "업무",
                        duration = "3시간 10분",
                        percent = 0.3f,
                        barColor = LimberColorStyle.Primary_Vivid
                    ),
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "독서",
                        duration = "2시간",
                        percent = 0.2f,
                        barColor = LimberColorStyle.Secondary_Main
                    )
                )
            )
            Spacer(Modifier.height(24.dp))

            ThisWeekTopGoalCard(
                title = "가장 많은 실험 중단 사유는",
                highlight = "휴식이 필요해서",
                afterHighlight = "였어요",
                subText = "전체 집중 시간의 20%를 차지했어요.",
                infos = listOf(
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "휴식이 필요해요",
                        duration = "6회",
                        percent = 0.5f,
                        barColor = LimberColorStyle.Primary_Main
                    ),
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "긴급한 상황이 발생했어요",
                        duration = "3회",
                        percent = 0.3f,
                        barColor = LimberColorStyle.Primary_Vivid
                    ),
                    GoalBarInfo(
                        iconRes = R.drawable.ic_info,
                        goal = "집중 의지가 부족해요",
                        duration = "1회",
                        percent = 0.2f,
                        barColor = LimberColorStyle.Secondary_Main
                    )
                )
            )
            Spacer(Modifier.height(20.dp))
        }
    }


@Composable
fun EmptyContent() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.ic_info), contentDescription = null)
        Spacer(Modifier.height(16.dp))
        Text(
            "아직 집중 데이터가 없어요. \n" +
                    "오늘부터 림버와 함께 집중 실험을 시작해보세요!",
            textAlign = TextAlign.Center,
            color = Gray600,
            style = LimberTextStyle.Body2
        )
        Spacer(Modifier.height(20.dp))

        LimberRoundButton(
            onClick = {},
            text = "실험 시작하기",
            textColor = LimberColorStyle.Primary_Main,
            containerColor = LimberColorStyle.Primary_BG_Dark
        )

    }
}

@Composable
fun WeeklyFocusCard(
    imageResId: Int,
    text: String,
    timerOrPercent: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(
                color = LimberColorStyle.Primary_BG_Normal,             // 연보라 계열의 배경색(예시)
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(imageResId), // 아이콘 교체!
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = text,
                color = LimberColorStyle.Primary_Vivid, // 프로젝트 컬러로 맞추기
                style = LimberTextStyle.Body2
            )
            Text(
                text = timerOrPercent,
                color = LimberColorStyle.Primary_Dark,
                style = LimberTextStyle.Heading2
            )
        }
    }
}

@Composable
fun ThisWeekTopGoalCard(
    title: String = "이번 주 가장 몰입한 목표는",
    highlight: String = "학습",
    afterHighlight: String = "이에요",
    subText: String = "전체 집중 시간의 50%를 차지했어요.",
    infos: List<GoalBarInfo>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .shadow(
                12.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = Color(0xFFEBEBEB)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 제목 + 하이라이트
            Text(
                buildAnnotatedString {
                    append("$title \n")
                    withStyle(SpanStyle(color = LimberColorStyle.Primary_Main)) {
                        append(highlight)
                    }
                    append(afterHighlight)
                },
                style = LimberTextStyle.Heading4,
                color = Gray800
            )
            // 서브텍스트
            Text(
                text = subText,
                style = LimberTextStyle.Body2,
                color = LimberColorStyle.Gray600
            )

            // 목표+막대 차트 목록
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                infos.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(item.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.goal,
                                    style = LimberTextStyle.Body2,
                                    color = LimberColorStyle.Gray800
                                )
                                Text(
                                    text = item.duration,
                                    style = LimberTextStyle.Body2,
                                    color = LimberColorStyle.Gray700
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            // 막대 Progress
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(LimberColorStyle.Gray200)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(item.percent)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(item.barColor)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// 데이터 모델
data class GoalBarInfo(
    val iconRes: Int,
    val goal: String,
    val duration: String,
    val percent: Float, // 0.0~1.0
    val barColor: Color
)

