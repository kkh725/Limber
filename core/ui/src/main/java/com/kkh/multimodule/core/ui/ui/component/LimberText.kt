package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle

@Composable
fun LimberText(text: String, style: TextStyle, color: Color, modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = text, style = style, color = color)
}