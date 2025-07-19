package com.kkh.multimodule.core.ui.ui.component

import android.widget.ToggleButton
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle

@Composable
fun LimberToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbContent: (@Composable () -> Unit)? = null,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        uncheckedThumbColor = Color.White,
        checkedTrackColor = LimberColorStyle.Primary_Main,
        uncheckedTrackColor = LimberColorStyle.Gray300,
        checkedBorderColor = Color.Transparent,
        uncheckedBorderColor = Color.Transparent,
    )
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        thumbContent = thumbContent,
        colors = colors
    )
}
@Composable
fun TextSwitch(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    optionLeft: String = "집중 시간",
    optionRight: String = "몰입도",
    modifier: Modifier = Modifier
) {
    val backgroundColor = Gray200
    val selectedColor = Color.White
    val unselectedColor = Gray500

    val indicatorWidth = 92.dp
    val indicatorHeight = 34.dp
    val padding = 3.dp
    val offsetAnim by animateDpAsState(
        targetValue = if (selected) 0.dp else indicatorWidth + padding,
        label = "slide"
    )

    Box(
        modifier
            .height(40.dp)
            .width(196.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onSelectedChange(!selected) }
            .padding(padding),
        contentAlignment = Alignment.CenterStart
    ) {
        // 움직이는 indicator(배경)
        Box(
            modifier = Modifier
                .offset(x = offsetAnim)
                .width(indicatorWidth)
                .height(indicatorHeight)
                .background(
                    color = selectedColor,
                    shape = RoundedCornerShape(17.dp)
                )
        )
        // 텍스트 Row 위에 겹치게
        Row(
            Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = optionLeft,
                    color = if (selected) Color.Black else unselectedColor,
                    style = LimberTextStyle.Body2
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = optionRight,
                    color = if (!selected) Color.Black else unselectedColor,
                    style = LimberTextStyle.Body2
                )
            }
        }
    }
}

