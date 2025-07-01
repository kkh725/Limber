package com.kkh.multimodule.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.kkh.accessibility.AppInfo


@Composable
fun DopamineActBox(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .background(color = Color.Red, shape = RoundedCornerShape(size = 100.dp))
            )
            Spacer(Modifier.width(6.dp))
            Text("도파민 활동")
        }
        Spacer(Modifier.height(16.5.dp))
        LazyColumn {
            items(appInfoList) { appInfo ->
                val bitmap = appInfo.appIcon?.toBitmap()
                val imageBitmap = bitmap?.asImageBitmap()
                val painter = imageBitmap?.let { BitmapPainter(it) }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    painter?.let {
                        Image(
                            painter = it,
                            contentDescription = "App Icon",
                            modifier = Modifier.size(18.dp) // 원하는 크기 지정
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(text = appInfo.usageTime.toString(), fontSize = 12.sp)
                }
                Spacer(Modifier.width(10.dp))
            }
        }
    }
}