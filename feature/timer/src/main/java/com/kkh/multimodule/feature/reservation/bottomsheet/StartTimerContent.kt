package com.kkh.multimodule.feature.reservation.bottomsheet

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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.core.ui.ui.component.LimberTimePicker
import kotlinx.datetime.LocalTime

@Composable
fun StartTimerContent(
    head: String,
    onClickBack: () -> Unit,
    onClickClose: () -> Unit,
    onClickComplete: (LocalTime) -> Unit = {}
) {

    var selectedTime by remember { mutableStateOf(LocalTime(1, 0)) }

    Scaffold(containerColor = Color.White, topBar = {
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
            onClick = {
                onClickComplete(selectedTime)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            text = "완료"
        )
    }) {
        Column(modifier = Modifier
            .padding(it)
            .padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(32.dp))
            Text(
                "종료할 시간을 선택해주세요",
                color = LimberColorStyle.Gray800,
                style = LimberTextStyle.Heading3
            )
            Spacer(Modifier.height(12.dp))

            LimberTimePicker(
                selectedTime = selectedTime,
                onValueChanged = { newTime ->
                    selectedTime = newTime
                    println("Selected Time: $newTime")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTimerTopBar(
    onClickBack: () -> Unit = {},
    onClickClose: () -> Unit = {},
    text: String = "시작 시간"
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(24.dp), onClick = onClickBack
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = LimberColorStyle.Gray400
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text,
            color = LimberColorStyle.Gray800,
            style = LimberTextStyle.Heading4
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(24.dp), onClick = onClickClose
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = LimberColorStyle.Gray400
            )
        }
    }
}