package com.kkh.multimodule.core.ui.ui.component

import android.R.attr.textColor
import android.R.attr.textStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongchyeon.timepicker.PickerStyle
import com.dongchyeon.timepicker.TimeFormat
import com.dongchyeon.timepicker.TimePicker
import com.dongchyeon.timepicker.TimePickerDefaults
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@Composable
fun LimberTimePicker(
    selectedTime: LocalTime,
    onValueChanged: (LocalTime) -> Unit
) {

    Box(Modifier
        .fillMaxWidth()
        .height(224.dp)) {
        TimePicker(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            initialTime = LocalTime(0, 15),
            style = PickerStyle(
                textStyle = LimberTextStyle.Body2,
                textColor = Gray800, itemSpacing = 5.dp
            ),
            timeFormat = TimeFormat.TWELVE_HOUR_KOREAN,
            selector = TimePickerDefaults.pickerSelector(
                color = Gray200,
                shape = RoundedCornerShape(8.dp)
            )
        ) { newTime ->
            onValueChanged(newTime)
        }
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.fillMaxWidth(0.585f))
            Text("시", style = LimberTextStyle.Heading2, color = Gray800)
            Spacer(Modifier.fillMaxWidth(0.56f))
            Text("분", style = LimberTextStyle.Heading2, color = Gray800)
        }
    }
}

@Composable
fun LimberTimePicker24(
    initial: kotlinx.datetime.LocalTime = kotlinx.datetime.LocalTime(0, 15),
    timeFormat: TimeFormat = TimeFormat.TWENTY_FOUR_HOUR,
    onValueChanged: (LocalTime) -> Unit
) {
    var selectedTime by rememberSaveable { mutableStateOf(initial) } // 외부 제어용 상태
    var hasCalledInitial by remember { mutableStateOf(false) }

    Box(Modifier
        .fillMaxWidth()
        .height(224.dp)) {
        TimePicker(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            initialTime = selectedTime, // 00:15:00,
            timeFormat = timeFormat,
            style = PickerStyle(
                textStyle = LimberTextStyle.Body2,
                textColor = Gray800, itemSpacing = 5.dp
            ),
            selector = TimePickerDefaults.pickerSelector(
                color = Gray200,
                shape = RoundedCornerShape(8.dp)
            )
        ) { newTime ->
            if (!hasCalledInitial && newTime == initial) {
                // 최초 렌더에서는 콜백 수행 안함, flag만 set
                hasCalledInitial = true
                selectedTime = newTime
            } else {
                selectedTime = newTime
                onValueChanged(newTime)
            }
        }
    }
}