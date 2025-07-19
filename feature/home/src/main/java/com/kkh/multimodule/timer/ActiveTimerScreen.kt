package com.kkh.multimodule.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Dark
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.AppList
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ActiveTimerScreen() {
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.ic_star)
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var isSheetOpen =
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            Image(painter = painterResource(R.drawable.logo_limber), contentDescription = null)
        }
        Scaffold(bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                BottomBar { }
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressBarWithHandleImage(percentage = 0.7f, handleImage = imageBitmap)
                Box(Modifier.fillMaxWidth()) {
                    TypeFocusText("학습", modifier = Modifier.align(Alignment.Center))
                }
                Spacer(Modifier.weight(1f))
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        "차단중인 앱 보기  >",
                        style = LimberTextStyle.Body2,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable { isSheetOpen.value = true }
                    )
                }
                Spacer(Modifier.height(18.dp))
            }
        }
        Box(
            Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Gray600)
            }
        }
        // BottomSheet
        if (isSheetOpen.value) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen.value = false },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    onClose = {
                        coroutineScope.launch { sheetState.hide() }
                        isSheetOpen.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun CircularProgressBarWithHandleImage(
    percentage: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
    backgroundColor: Color = Color.LightGray,
    handleImage: ImageBitmap
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
            .width(screenWidthDp)
            .height(screenWidthDp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            val radius = size.minDimension / 1.5f // 반경을 좀 작게
            val center = Offset(size.width * 0.75f, size.height * 0.25f) // 우측상단 쪽으로 이동

            val gradientBrush = Brush.radialGradient(
                colors = listOf(Color(0xFF2F044B), Color(0xFF8308D2)),
                center = center,
                radius = radius,
                tileMode = TileMode.Clamp
            )

            // 그라디언트로 원 채우기
            drawCircle(
                brush = gradientBrush,
                radius = size.minDimension / 2f,
                center = Offset(size.width / 2f, size.height / 2f)
            )
        }


        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp)
        ) {
            val sweepAngle = 360 * percentage
            val strokePx = strokeWidth.toPx()

            // 배경 원
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Sweep 그라디언트 Brush
            val sweepGradient = Brush.sweepGradient(
                colors = listOf(Color.White, Color(0xFFB961FF), Color.White),
                center = Offset(size.width / 2, size.height / 2)
            )

            // Arc 그리기
            drawArc(
                brush = sweepGradient,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // 핸들 위치 계산
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = (size.minDimension - strokePx) / 2f
            val angleInDegrees = (percentage * 360f) - 90f
            val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

            val handleX = centerX + radius * kotlin.math.cos(angleInRadians).toFloat()
            val handleY = centerY + radius * kotlin.math.sin(angleInRadians).toFloat()

            // 이미지 그리기 (중앙 정렬)
            drawImage(
                image = handleImage,
                topLeft = Offset(
                    handleX - handleImage.width / 2f - 10f,
                    handleY - handleImage.height / 2f
                )
            )
        }

        // 중앙 텍스트
        Text(
            text = "${(percentage * 100).toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TypeFocusText(typeText: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clickable(onClick = {}, enabled = false)
            .background(color = Primary_Dark, shape = RoundedCornerShape(size = 100.dp))
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Row {
            Text(
                typeText,
                style = LimberTextStyle.Body3,
                color = LimberColorStyle.Primary_Main,
                textAlign = TextAlign.Center
            )
            Text(
                "에 집중하는 실험",
                style = LimberTextStyle.Body3,
                color = Color.White,
                textAlign = TextAlign.Center
            )

        }
    }
}


@Composable
fun BottomBar(onClick: () -> Unit) {
    LimberSquareButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        text = "홈으로 가기",
        textColor = Color.Black,
        containerColor = Gray200
    )
}

@Composable
fun BottomSheetContent(onClose: () -> Unit) {
    val appinfoList: List<AppInfo> = listOf(
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간"),
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간"),
        AppInfo("인스타그램", "com.app1", null, "30분"),
        AppInfo("유튜브", "com.app2", null, "45분"),
        AppInfo("카카오톡", "com.app3", null, "1시간")
    )

    Box{
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "차단 중인 앱",
                    style = LimberTextStyle.Heading4,
                    color = Gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

            }
            Spacer(Modifier.height(33.dp))
            AppList(appInfoList = appinfoList, onClickModifyButton = {})
            Spacer(Modifier.height(24.dp))

        }
        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Gray600)
        }
    }

}