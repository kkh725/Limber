package com.kkh.multimodule.reservation.bottomsheet

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.timer.ChipInfo
import com.kkh.multimodule.ui.component.LimberCheckButton
import com.kkh.multimodule.ui.component.LimberGradientButton

@Composable
fun RepeatTimerContent(
    head: String,
    repeatOptionList: List<ChipInfo>,
    dayList: List<ChipInfo>,
    onClickBack: () -> Unit,
    onClickClose: () -> Unit,
    onClickComplete: () -> Unit,
    onClickOption: (String) -> Unit,
    onClickDay: (String) -> Unit
) {
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
            onClick = onClickComplete,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            text = "완료"
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(32.dp))
            Text(
                "반복할 주기를 선택해주세요",
                color = LimberColorStyle.Gray800,
                style = LimberTextStyle.Heading3
            )
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeatOptionList.forEach { chip ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        RepeatOptionItem(
                            text = chip.text,
                            isChecked = chip.isSelected,
                            onClick = {
                                onClickOption(chip.text)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                dayList.forEach { chip ->
                    RepeatDayItem(
                        text = chip.text,
                        isChecked = chip.isSelected,
                        onClick = {
                            onClickDay(chip.text)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RepeatOptionItem(text: String, isChecked: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LimberCheckButton(isChecked = isChecked, onClick = onClick)
        Text(text, style = LimberTextStyle.Body1, color = LimberColorStyle.Gray600)
    }
}

@Composable
fun RepeatDayItem(text: String, isChecked: Boolean, onClick: () -> Unit) {
    val textColor = if (isChecked) Color.White else LimberColorStyle.Gray400
    val backgroundColor = if (isChecked) LimberColorStyle.Primary_Main else LimberColorStyle.Gray100
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = LimberTextStyle.Body1, color = textColor)
    }
}
