package com.kkh.multimodule.feature.laboratory.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberRoundButton
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton

@Composable
fun ReportPagerContent() {

    Column {
        EmptyContent()
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