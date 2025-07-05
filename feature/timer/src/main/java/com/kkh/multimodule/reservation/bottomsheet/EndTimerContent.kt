package com.kkh.multimodule.reservation.bottomsheet

import android.R.attr.onClick
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton

@Preview
@Composable
fun EndTimerContent(
    head: String,
    onClickBack: () -> Unit,
    onClickClose: () -> Unit,
    onClickComplete: () -> Unit = {}
) {
    Scaffold(containerColor = Color.White,topBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            StartTimerTopBar(
                onClickBack = onClickBack,
                onClickClose = onClickClose,
                text = head
            )
        }
    }, bottomBar = {
        LimberGradientButton(
            onClick = onClickComplete,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            text = "완료"
        )
    }) {
        Column(modifier = Modifier.padding(it).padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(32.dp))
            Text(
                "종료할 시간을 선택해주세요",
                color = LimberColorStyle.Gray800,
                style = LimberTextStyle.Heading3
            )

        }
    }
}
