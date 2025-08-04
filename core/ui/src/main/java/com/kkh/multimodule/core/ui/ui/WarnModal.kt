package com.kkh.multimodule.core.ui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberCloseButton
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton
import com.kkh.multimodule.core.ui.ui.component.LimberText

@Preview
@Composable
fun WarnDialog(
    appInfoList: List<AppInfo> = emptyList(),
    title: String = "1시간 4분동안\n다음의 앱들이 차단돼요",
    onClickModifyButton: () -> Unit = {},
    onClickStartButton: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(536.dp)
            .verticalScroll(rememberScrollState())
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
                    .background(LimberColorStyle.Primary_Main)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_warn_modal),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillHeight
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 32.dp, end = 20.dp)
                ) {
                    LimberCloseButton { onDismissRequest() }
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                style = LimberTextStyle.Heading2,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))

            AppList(
                appInfoList = appInfoList,
                onClickModifyButton = onClickModifyButton,
                onClickEmptyBox = {}
            )
            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (appInfoList.isNotEmpty()) {
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
            }


            Spacer(Modifier.height(36.dp))

            Text(
                "버튼을 누르면 실험이 시작돼요!",
                modifier = Modifier.fillMaxWidth(),
                color = LimberColorStyle.Primary_Main,
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

@Preview
@Composable
fun UnBlockWarnModal(
    modifier: Modifier = Modifier,
    onClickUnBlock: () -> Unit = {},
    onClickContinue: () -> Unit = {}
) {
    Column(
        modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        Text(
            "정말 잠금을 푸시겠어요?\n" +
                    "이대로 잠금을 풀면 실험이 종료돼요.", style = LimberTextStyle.Heading3, color = Gray800
        )
        Spacer(Modifier.weight(1f))
        BlockScreenBottomBar(onClickUnBlock = onClickUnBlock, onClickContinue = onClickContinue)
    }
}


@Composable
fun AppList(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>,
    onClickModifyButton: () -> Unit,
    onClickEmptyBox: () -> Unit = {}
) {
    if (appInfoList.isEmpty()) {
        EmptyAppInfoBox(onClickEmptyBox)
    } else {
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
        Text(
            appInfo.appName,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Body2
        )
    }
}

@Composable
fun EmptyAppInfoBox(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(99.dp)
                .background(Gray100, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LimberText("차단될 앱이 없어요", style = LimberTextStyle.Body2, color = Gray800)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Gray200),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Gray400,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    LimberText("등록하기", style = LimberTextStyle.Body2, color = Gray500)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        LimberText(
            "등록한 앱이 안 보인다면 “권한 설정”을 확인해주세요.",
            style = LimberTextStyle.Body3,
            color = Gray400
        )
    }
}


@Composable
fun BlockScreenBottomBar(
    modifier: Modifier = Modifier,
    onClickUnBlock: () -> Unit,
    onClickContinue: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        LimberSquareButton(
            onClick = onClickUnBlock,
            containerColor = Gray200,
            textColor = Gray800,
            text = "중단하기",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        LimberSquareButton(
            onClick = onClickContinue,
            text = "실험 유지하기",
            modifier = Modifier.weight(1f)
        )

    }
}