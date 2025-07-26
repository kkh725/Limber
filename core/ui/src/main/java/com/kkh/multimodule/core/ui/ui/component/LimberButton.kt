package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle

@Composable
fun LimberSquareButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    containerColor: Color = Primary_Main,
    disabledContainerColor: Color = Color.Gray
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        )
    ) {
        Text(text, style = LimberTextStyle.Heading4, color = textColor)
    }
}

@Composable
fun LimberRoundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    containerColor: Color = Primary_Main,
    disabledContainerColor: Color = Color.Gray
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(100.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        )
    ) {
        Text(text, style = LimberTextStyle.Body2, color = textColor)
    }
}

@Composable
fun LimberGradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    enabled: Boolean = true,
) {
    val backgroundModifier = if (enabled) Modifier.background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFB961FF), Color(0xFF8308D2)),
            start = Offset(0f, 0f),
            end = Offset.Infinite
        )
    ) else Modifier.background(Gray300)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .then(backgroundModifier) // ✅ 여기서 붙여준다!
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp), // 원래 contentPadding
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = LimberTextStyle.Heading4,
            color = textColor
        )
    }
}


@Composable
fun LimberFloatingButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun LimberCheckButton(
    isChecked: Boolean = false,
    onClick: (Boolean) -> Unit
) {
    IconButton(onClick = { onClick(!isChecked) }, modifier = Modifier.size(24.dp)) {
        Crossfade(targetState = isChecked, label = "CheckIconCrossfade") { checked ->
            val imageResource = if (checked) R.drawable.ic_checked else R.drawable.ic_unchecked
            val painter = painterResource(imageResource)

            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun LimberCloseButton(modifier: Modifier = Modifier,onClick: () -> Unit){
    IconButton(
        onClick = onClick,
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            tint = Gray400
        )
    }
}

@Composable
fun LimberBackButton(modifier: Modifier = Modifier,onClick: () -> Unit){
    IconButton(onClick = onClick, modifier = modifier.size(24.dp)) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_back),
            tint = Color.Unspecified,
            contentDescription = "ic_back"
        )
    }
}

@Composable
fun LimberNextButton(modifier: Modifier = Modifier,onClick: () -> Unit){
    IconButton(onClick = onClick, modifier = modifier.size(24.dp)) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_next),
            tint = Color.Unspecified,
            contentDescription = "ic_next"
        )
    }
}