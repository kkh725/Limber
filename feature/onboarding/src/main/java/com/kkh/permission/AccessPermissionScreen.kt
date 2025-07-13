package com.kkh.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton
import kotlinx.coroutines.launch

@Composable
fun AccessPermissionScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(Modifier.padding(vertical = 20.dp), onClickBack = {})
        LimberProgressBar(0.4f)
        Spacer(Modifier.height(40.dp))
        Text(
            "림버를 사용하기 위해\n" +
                    "스크린타임 데이터가 필요해요",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading1,
            color = Gray800
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "서비스 이용에 꼭 필요한 데이터만 수집해요", style = LimberTextStyle.Body2, color = Gray600
        )
        Spacer(Modifier.height(40.dp))
        PermissionBox(headText = "스크린 타임 데이터", bodyText = "앱 별 사용시간 조회")
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

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AccessPermissionScreenPreview() {
    AccessPermissionScreen()
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onClickBack: () -> Unit = {}) {
    Box(
        modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onClickBack, modifier = Modifier.size(24.dp)) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "ic_back"
            )
        }
    }
}

@Composable
fun LimberProgressBar(percentage: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(100.dp)) // 배경 바
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(percentage.coerceIn(0f, 1f)) // 퍼센트만큼 채움
                .background(
                    color = LimberColorStyle.Primary_Main,
                    shape = RoundedCornerShape(100.dp)
                )
        )
    }
}

@Composable
fun PermissionBox(headText: String, bodyText: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .border(1.dp, color = Gray300, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.ic_info), contentDescription = "info")
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    headText, style = LimberTextStyle.Heading4, color = Gray800
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    bodyText, style = LimberTextStyle.Body2, color = Gray500
                )
            }
        }
    }
}
