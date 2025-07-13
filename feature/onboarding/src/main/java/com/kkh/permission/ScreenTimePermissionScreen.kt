package com.kkh.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton


@Composable
fun ScreenTimePermissionScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(Modifier.padding(vertical = 20.dp), onClickBack = {})
        LimberProgressBar(0.7f)
        Spacer(Modifier.height(40.dp))
        Text(
            "림버를 사용하기 위해\n" +
                    "접근성 허용이 필요해요",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading1,
            color = Gray800
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "서비스 이용에 꼭 필요한 데이터만 수집해요", style = LimberTextStyle.Body2, color = Gray600
        )
        Spacer(Modifier.height(40.dp))
        PermissionBox(headText = "접근성 허용", bodyText = "앱 차단 허용")
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LimberGradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                },
                text = "다음으로"
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun ScreenTimePermissionScreenPreview() {
    ScreenTimePermissionScreen()
}