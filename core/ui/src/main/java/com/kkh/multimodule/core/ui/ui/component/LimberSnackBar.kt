package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray700
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle

@Preview
@Composable
fun LimberSnackBar(text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Gray700, shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_warning_snackbar),
            contentDescription = null,
            tint = androidx.compose.ui.graphics.Color.Unspecified
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text, color = Color.White, style = LimberTextStyle.Body2
        )
    }
}