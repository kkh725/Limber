package com.kkh.multimodule.core.ui.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kkh.multimodule.core.ui.R

object LimberTextStyle {
    private val suitSemiboldFamily = FontFamily(Font(R.font.suit_semibold))
    private val defaultTextColor = Color(0xFF1D1A22)
    private val fontWeightSemiBold = FontWeight(600)
    private val fontWeightMedium = FontWeight(500)

    val DisPlay1: TextStyle
        get() = TextStyle(
            fontSize = 40.sp,
            lineHeight = 48.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val DisPlay2: TextStyle
        get() = TextStyle(
            fontSize = 28.sp,
            lineHeight = 33.6.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val Heading1: TextStyle
        get() = TextStyle(
            fontSize = 24.sp,
            lineHeight = 33.6.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val Heading2: TextStyle
        get() = TextStyle(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val Heading3: TextStyle
        get() = TextStyle(
            fontSize = 18.sp,
            lineHeight = 25.2.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val Heading4: TextStyle
        get() = TextStyle(
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightSemiBold,
            color = defaultTextColor
        )

    val Heading5: TextStyle
        get() = TextStyle(
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            fontFamily = FontFamily(Font(R.font.suit_semibold)),
            fontWeight = FontWeight(600),
            color = defaultTextColor,
            textAlign = TextAlign.Center,
        )

    val Body1: TextStyle
        get() = TextStyle(
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = defaultTextColor
        )

    val Body2: TextStyle
        get() = TextStyle(
            fontSize = 14.sp,
            lineHeight = 19.6.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = defaultTextColor
        )

    val Body3: TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.8.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = defaultTextColor
        )

    val Caption1: TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.8.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = defaultTextColor
        )

    val graphBody3_Gray400 : TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.8.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = LimberColorStyle.Gray400
        )

    val graphBody3_Primary_Vivid : TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.8.sp,
            fontFamily = suitSemiboldFamily,
            fontWeight = fontWeightMedium,
            color = LimberColorStyle.Primary_Vivid
        )
}