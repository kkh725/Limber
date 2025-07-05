package com.kkh.multimodule.ui.component

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.designsystem.LimberColorStyle

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
