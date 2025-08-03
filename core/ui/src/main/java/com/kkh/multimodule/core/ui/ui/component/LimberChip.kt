package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle

@Composable
fun LimberChip(
    modifier: Modifier = Modifier,
    text: String,
    imageResId: Int = R.drawable.ic_info,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
) {
    val borderColor by animateColorAsState(if (isSelected) Primary_Main else Gray300)
    val backgroundColor by animateColorAsState(if (isSelected) Primary_Main else Color.Transparent)
    val textColor by animateColorAsState(if (isSelected) Color.White else Gray500)

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(1.dp, borderColor),
        contentPadding = contentPadding
    ) {
        Image(painter = painterResource(imageResId), contentDescription = null)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text)
    }
}


@Composable
fun LimberChipWithPlus(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = LimberColorStyle.Gray200

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Gray500 // Text 기본 컬러 (Row의 Text에 따로 색 지정할 거면 여긴 기본)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("+", color = Gray400, modifier = Modifier.padding(end = 4.dp))
            Text(text, color = Gray500)
        }
    }
}

@Composable
fun LimberFilterChip(
    text: String = "토글",
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    checkedTextColor: Color = Color.White,
    uncheckedTextColor: Color = Gray500,
    checkedBackgroundColor: Color = Color.Black,
    uncheckedBackgroundColor: Color = Gray200
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) checkedBackgroundColor else uncheckedBackgroundColor,
        label = "FilterChipBackground"
    )
    val textColor by animateColorAsState(
        targetValue = if (checked) checkedTextColor else uncheckedTextColor,
        label = "FilterChipText"
    )

    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(100.dp))
            .clip(RoundedCornerShape(100.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = LimberTextStyle.Body2,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
