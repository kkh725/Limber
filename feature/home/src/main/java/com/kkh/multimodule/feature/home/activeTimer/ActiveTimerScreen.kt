package com.kkh.multimodule.feature.home.activeTimer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Dark
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.AppList
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ActiveTimerScreen(
    onPopBackStack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToRecall: () -> Unit = {}
) {
    val activeTimerViewModel: ActiveTimerViewModel = hiltViewModel()
    val uiState by activeTimerViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.ic_star)
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()
    val isSheetOpen = uiState.sheetState
    val blockedAppList = uiState.blockedAppList
    val timerPercent = uiState.timerPercent
    val focusType = uiState.focusType

    val isTimerEnd = timerPercent == 0f

    var countdownSeconds by remember(isTimerEnd) { mutableIntStateOf(10) }

    LaunchedEffect(isTimerEnd) {
        if (isTimerEnd) {
            countdownSeconds = 10
            for (i in 10 downTo 1) {
                countdownSeconds = i
                delay(1000)
            }
//            onNavigateToRecall()
        }
    }

    Box(Modifier.fillMaxSize()) {
        Image(painter = painterResource(R.drawable.logo_limber), contentDescription = null)
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            containerColor = Gray50,
            bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                BottomBar(
                    isTimerEnd = isTimerEnd,
                    onNavigateToHome = onNavigateToHome,
                    onNavigateToRecall = onNavigateToRecall
                )
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressBarWithHandleImage(
                    percentage = timerPercent,
                    handleImage = imageBitmap
                )

                Box(Modifier.fillMaxWidth()) {
                    TypeFocusText(
                        focusType,
                        isTimerEnd = isTimerEnd,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(Modifier.weight(1f))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    if (isTimerEnd) {
                        Text(
                            "${countdownSeconds}초 후에 자동으로 회고 페이지로 넘어갑니다.",
                            color = Gray500,
                            style = LimberTextStyle.Caption1,
                        )
                    } else {
                        TextButton(
                            onClick = {
                                activeTimerViewModel.sendEvent(
                                    ActiveTimerEvent.SheetExpanded(
                                        true,
                                        context
                                    )
                                )
                            }
                        ) {
                            Text(
                                "차단중인 앱 보기  >",
                                style = LimberTextStyle.Body2,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(6.dp))
            }
        }
        Box(
            Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = onPopBackStack) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Gray600)
            }
        }
        // BottomSheet
        if (isSheetOpen) {
            ModalBottomSheet(
                containerColor = Color.White,
                onDismissRequest = {
                    activeTimerViewModel.sendEvent(
                        ActiveTimerEvent.SheetExpanded(
                            false,
                            context
                        )
                    )
                },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    appInfoList = blockedAppList,
                    onClose = {
                        coroutineScope.launch { sheetState.hide() }
                        activeTimerViewModel.sendEvent(
                            ActiveTimerEvent.SheetExpanded(
                                false,
                                context
                            )
                        )
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

            val handleX = centerX + radius * cos(angleInRadians).toFloat()
            val handleY = centerY + radius * sin(angleInRadians).toFloat()

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
fun TypeFocusText(
    typeText: String,
    isTimerEnd: Boolean,
    modifier: Modifier = Modifier
) {
    val alphaValue = if (isTimerEnd) 0.5f else 1f

    Box(
        modifier
            .alpha(alphaValue) // ✅ alpha
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
fun BottomBar(
    isTimerEnd: Boolean,
    onNavigateToHome: () -> Unit,
    onNavigateToRecall: () -> Unit
) {
    if (isTimerEnd) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            LimberSquareButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = onNavigateToHome,
                text = "홈으로 가기",
                textColor = Color.Black,
                containerColor = Gray200
            )
            Spacer(Modifier.width(12.dp))
            LimberSquareButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = onNavigateToRecall,
                text = "회고하기",
                textColor = Color.White,
                containerColor = LimberColorStyle.Primary_Main
            )
        }
    } else {
        LimberSquareButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onNavigateToHome,
            text = "홈으로 가기",
            textColor = Color.Black,
            containerColor = Gray200
        )
    }

}

@Composable
fun BottomSheetContent(appInfoList: List<AppInfo>, onClose: () -> Unit) {

    Box {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                //icon button 만큼 start padding
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "차단 중인 앱",
                    style = LimberTextStyle.Heading5,
                    color = Gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onClose() },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Gray600
                    )
                }

            }
            Spacer(Modifier.height(33.dp))
            AppList(
                appInfoList = appInfoList,
                onClickModifyButton = {},
                onClickEmptyBox = {}
            )
            Spacer(Modifier.height(24.dp))

        }
    }
}