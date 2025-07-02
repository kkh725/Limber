package com.kkh.multimodule.ui.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.designsystem.LimberTextStyle

@Composable
fun LimberChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Primary_Main else Gray300
    val textColor = if (isSelected) Color.Black else Color.Gray

    Box(
        Modifier
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(100.dp))
            .width(68.dp)
            .height(38.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor)
    }
}

@Composable
fun LimberChipWithPlus(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFFEAE6ED) else Gray100
    val textColor = if (isSelected) Color(0xFFEAE6ED) else Color.Black

    Box(
        Modifier
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(100.dp))
            .width(68.dp)
            .height(38.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("+", color = textColor, modifier = Modifier.padding(end = 4.dp))
            Text(text, color = textColor)
        }
    }
}

@Preview
@Composable
fun LimberFilterChip(
    text: String = "토글",
    textColor: Color = Color.White,
    backgroundColor: Color = Gray600,
    onclick: () -> Unit = {}
) {
    Box(
        Modifier
            .clickable(onClick = onclick)
            .padding(0.8.dp)
            .height(36.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 100.dp))
            .padding(start = 16.dp, top = 6.dp, end = 16.dp, bottom = 6.dp)
    ) {
        Text(text, style = LimberTextStyle.Body2, color = textColor)
    }
}