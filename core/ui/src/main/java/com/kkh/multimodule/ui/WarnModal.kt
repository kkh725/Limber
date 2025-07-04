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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.kkh.multimodule.core.ui.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.Brush
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
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.designsystem.LimberColorStyle.Primary_BG_Normal
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.DopamineActBox
import com.kkh.multimodule.ui.component.LimberSquareButton
import com.kkh.multimodule.ui.component.LimberChip
import com.kkh.multimodule.ui.component.LimberChipWithPlus
import com.kkh.multimodule.ui.component.LimberFilterChip
import com.kkh.multimodule.ui.component.LimberGradientButton
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Preview
@Composable
fun WarnDialog(
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
    onClickStartButton: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(482.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(152.dp)
                    .background(Gray200)
            ) {
                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 32.dp, end = 20.dp)
                ) {
                    IconButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            tint = Gray400
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                "1시간 4분동안\n다음의 앱들이 차단돼요",
                modifier = Modifier.fillMaxWidth(),
                style = LimberTextStyle.Heading2,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))

            AppList(appInfoList = appinfoList)
            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier.clickable(onClick = onClickModifyButton),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_write),
                        contentDescription = "Write Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "편집하기",
                        style = LimberTextStyle.Body2,
                        color = Gray500
                    )
                }
            }


            Spacer(Modifier.weight(1f))
            Text(
                "버튼을 누르면 실험이 시작돼요",
                modifier = Modifier.fillMaxWidth(),
                color = Gray500,
                textAlign = TextAlign.Center,
                style = LimberTextStyle.Body3
            )
            Spacer(Modifier.height(8.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
            ) {
                LimberGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "시작하기",
                    onClick = onClickStartButton
                )
            }
        }
    }
}


@Composable
fun AppList(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .height(76.dp)
            .padding(start = 20.dp)
    ) {
        itemsIndexed(appInfoList) { index, appInfo ->
            AppInfoItem(appInfo = appInfo)
        }
    }
}

@Composable
fun AppInfoItem(appInfo: AppInfo) {

    val bitmap = appInfo.appIcon?.toBitmap()
    val imageBitmap = bitmap?.asImageBitmap()
    val painter = imageBitmap?.let { BitmapPainter(it) }

    Column(
        Modifier
            .width(100.dp)
            .height(76.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray100),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter ?: painterResource(R.drawable.ic_info),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(appInfo.appName, style = LimberTextStyle.Body2)
    }
}