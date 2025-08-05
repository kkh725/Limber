package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle


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
            Text("도파민 활동", style = LimberTextStyle.Body2, color = Gray600)
        }
        Spacer(Modifier.height(16.dp))
        if (appInfoList.isEmpty()){
            Text(
                text = "아직 활동이 없어요",
                style = LimberTextStyle.Body3,
                color = Gray400,
            )
        }else{
            LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                items(appInfoList.take(3)) { appInfo ->
                    val bitmap = appInfo.appIcon?.toBitmap()
                    val imageBitmap = bitmap?.asImageBitmap()
                    val painter = imageBitmap?.let { BitmapPainter(it) }

                    Row(verticalAlignment = Alignment.Top) {
                        if (painter != null) {
                            Image(
                                painter = painter,
                                contentDescription = "App Icon",
                                modifier = Modifier.size(18.dp) // 원하는 크기 지정
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_data),
                                contentDescription = "App Icon",
                                modifier = Modifier.size(18.dp) // 원하는 크기 지정
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = appInfo.usageTime,
                            style = LimberTextStyle.Body3,
                            color = Gray400,
                            modifier = Modifier.align(Alignment.Top)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                }
            }
        }
    }
}