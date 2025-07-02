package com.kkh.multimodule.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import com.kkh.multimodule.core.ui.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.designsystem.LimberColorStyle.Primary_BG_Normal
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.DopamineActBox
import com.kkh.multimodule.ui.component.LimberSquareButton
import com.kkh.multimodule.ui.component.LimberChip
import com.kkh.multimodule.ui.component.LimberChipWithPlus
import com.kkh.multimodule.ui.component.LimberFilterChip
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter

@Preview
@Composable
fun WarnModal(
    appinfoList: List<AppInfo> = listOf(
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간"),
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간"),
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간")
    ),
    onClickModifyButton: () -> Unit = {},
    onClickStartButton: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(522.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(482.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(Modifier.height(56.dp))

                Text(
                    "1시간 4분동안\n다음의 앱을이 차단돼요",
                    modifier = Modifier.fillMaxWidth(),
                    style = LimberTextStyle.Heading2,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("12개의 앱", style = LimberTextStyle.Heading4)
                    LimberFilterChip(
                        "수정하기",
                        textColor = LimberColorStyle.Gray600,
                        backgroundColor = LimberColorStyle.Gray200,
                        onclick = onClickModifyButton
                    )
                }
                Spacer(Modifier.height(20.dp))
                AppList(appinfoList)

                Spacer(Modifier.weight(1f))
                Text(
                    "바로 시작하시겠습니까?",
                    modifier = Modifier.fillMaxWidth(),
                    color = Gray500,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))

                LimberSquareButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "시작하기",
                    onClick = onClickStartButton
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_checked),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun AppList(
    appInfoList: List<AppInfo>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.height(152.dp)
    ) {
        itemsIndexed(appInfoList) { index, appInfo ->
            val isLastItem = index == appInfoList.lastIndex
            val bitmap = appInfo.appIcon?.toBitmap()
            val imageBitmap = bitmap?.asImageBitmap()
            val painter = imageBitmap?.let { BitmapPainter(it) }

            Row(verticalAlignment = Alignment.CenterVertically) {
                painter?.let {
                    Image(
                        painter = it,
                        contentDescription = "App Icon",
                        modifier = Modifier
                            .size(28.dp)
                            .blur(
                                radius = if (isLastItem) 5.dp else 0.dp
                            )
                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(text = appInfo.appName, style = LimberTextStyle.Body2)
            }
        }
    }
}