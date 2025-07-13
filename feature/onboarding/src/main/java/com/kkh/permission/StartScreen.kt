package com.kkh.permission

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton


@Composable
fun StartScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(Modifier.padding(vertical = 20.dp), onClickBack = {})
        Spacer(Modifier.height(66.dp))
        Text(
            "이제 림버와 함께\n" +
                    "집중 회복 실험을 시작해보세요!",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading1,
            color = Gray800
        )
        Spacer(Modifier.height(90.dp))
        Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LimberGradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                },
                text = "림버 시작하기"
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen()
}