package com.kkh.multimodule.ui.component

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongchyeon.timepicker.ItemLabel
import com.dongchyeon.timepicker.TimeFormat
import com.dongchyeon.timepicker.TimePicker
import com.dongchyeon.timepicker.TimePickerDefaults
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Preview(showBackground = true)
@Composable
fun LimberTimePicker(){
//    var selectedTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time) }
    var selectedTime by remember { mutableStateOf(LocalTime(1, 0)) }

    Box(Modifier.fillMaxWidth().height(224.dp)){
        TimePicker(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            initialTime = selectedTime,
            timeFormat = TimeFormat.TWELVE_HOUR_KOREAN,
            itemLabel = ItemLabel(
                style = LimberTextStyle.Heading2,
                color = Gray800
            ),
            selector = TimePickerDefaults.pickerSelector(
                color = Gray200,
                shape = RoundedCornerShape(8.dp)
            )
        ) { newTime ->
            selectedTime = newTime
        }
        Row(Modifier.fillMaxSize(),verticalAlignment = Alignment.CenterVertically){
            Spacer(Modifier.fillMaxWidth(0.585f))
            Text("시",style = LimberTextStyle.Heading2,color = Gray800)
            Spacer(Modifier.fillMaxWidth(0.56f))
            Text("분",style = LimberTextStyle.Heading2,color = Gray800)
        }
    }

}