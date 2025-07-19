package com.kkh.multimodule.core.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object LimberColorStyle {
    val Gray50 = Color(0xFFFCFCFC)
    val Gray100 = Color(0xFFF9F7FA)
    val Gray200 = Color(0xFFF3F0F5)
    val Gray300 = Color(0xFFEAE6ED)
    val Gray400 = Color(0xFFB0ABB2)
    val Gray500 = Color(0xFF938F96)
    val Gray600 = Color(0xFF625F66)
    val Gray700 = Color(0xFF322F37)
    val Gray800 = Color(0xFF1D1A22)


    val Primary_Main = Color(0xFFB05AF6)
    val Primary_Light = Color(0xFFDFB8FF)
    val Primary_Vivid = Color(0xFF8308D2)

    val Primary_Dark = Color(0xFF2F044B)
    val Primary_Background_Dark = Color(0xFFF6EAFF)
    val Secondary_Main = Color(0xFFFF725E)

    val Primary_BG_Normal = Color(0xFFF5EBFE)
    val Primary_BG_Dark = Color(0xFFF6EAFF)

}

fun gradientModifier(): Modifier {
    return Modifier.background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFB961FF), Color(0xFF8308D2)),
            start = Offset(0f, 0f),
            end = Offset.Infinite
        )
    )
}

