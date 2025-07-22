package com.kkh.multimodule.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Secondary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.DopamineActBox
import com.kkh.multimodule.core.ui.ui.component.RegisterBlockAppBottomSheet
import com.kkh.multimodule.core.ui.util.decrementOneSecond
import com.kkh.multimodule.core.ui.util.getCurrentTimeInKoreanFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToActiveTimer: () -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val appInfoList = uiState.usageAppInfoList
    val blockingAppPackageList = uiState.blockingAppPackageList

    var isSheetVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var timerText by remember { mutableStateOf(getCurrentTimeInKoreanFormat()) }

    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(HomeEvent.EnterHomeScreen(context))
    }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(1000)
            if (timerText != "00:00:00") {
                timerText = decrementOneSecond(timerText)
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.Transparent)
    ) {
        Spacer(Modifier.height(12.dp))
        HomeTopBar(
            number = blockingAppPackageList.size,
            onClick = {
                isSheetVisible = true
            },
            onClickNoti = {}
        )
        Spacer(Modifier.height(20.dp))
        HomeScreenMainBody(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            appInfoList = appInfoList,
            navigateToActiveTimer = onNavigateToActiveTimer,
            timerText = timerText
        )
    }

    if (isSheetVisible) {
        RegisterBlockAppBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetVisible = false },
            onClickComplete = { checkedAppList ->
                isSheetVisible = false
                homeViewModel.sendEvent(HomeEvent.OnCompleteRegisterButton(checkedAppList))
            },
            appList = appInfoList
        )
    }
}

@Composable
private fun HomeScreenMainBody(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>,
    navigateToActiveTimer : () -> Unit = {},
    timerText: String = "00:00:00"
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_lv1),
                modifier = Modifier.size(114.dp),
                contentDescription = null
            )
            Spacer(Modifier.height(10.dp))
            TimerButton(onClick = navigateToActiveTimer, timerText = timerText)
            Spacer(Modifier.height(24.dp))
            TodayActivityBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                onClick = {}
            )
            Spacer(Modifier.height(13.dp))
            HomeMainContent(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 20.dp),
                appInfoList = appInfoList,
                focusTime = "1시간 2분",
                dopamineTime = "1시간 0분"
            )
        }
    }
}

@Composable
fun HomeTopBar(number: Int, onClick: () -> Unit, onClickNoti: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo_limber),
            modifier = Modifier.size(65.dp, 20.dp),
            contentDescription = "Back"
        )
        Spacer(Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color(0xFF7531C6))
                    .clickable(enabled = false) { onClick() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("${number}개의 앱 관리중", style = LimberTextStyle.Body2, color = Color.White)
            }
            Spacer(Modifier.width(6.dp))
            //todo open
//            IconButton(onClick = onClickNoti, Modifier) {
//                Image(
//                    painter = painterResource(R.drawable.ic_noti),
//                    modifier = Modifier.size(20.dp),
//                    contentDescription = "Back"
//                )
//            }
        }
    }
}

@Composable
fun HomeMainContent(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>,
    focusTime: String,
    dopamineTime: String
) {

    val focusTextColor = if (focusTime == "0분") Primary_Main else Primary_Main.copy(alpha = 0.3f)
    val dopamineTextColor =
        if (dopamineTime == "0분") Secondary_Main else Secondary_Main.copy(alpha = 0.3f)
    val focusTimeColor = if (focusTime == "0분") Gray800 else Gray800.copy(alpha = 0.3f)
    val dopamineTimeColor = if (dopamineTime == "0분") Gray800 else Gray800.copy(alpha = 0.3f)

    Box(modifier = modifier) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(327.dp)
                .shadow(4.dp, shape = RoundedCornerShape(12.dp), clip = true)
                .background(Color.White)
                .padding(20.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // 콘텐츠 영역
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {


                    Spacer(Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            "집중한 시간",
                            style = LimberTextStyle.Body2,
                            color = focusTextColor
                        )
                        Text(
                            "도파민 노출시간",
                            style = LimberTextStyle.Body2,
                            color = dopamineTextColor
                        )
                    }
                    Spacer(Modifier.height(2.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(focusTime, style = LimberTextStyle.Heading1, color = focusTimeColor)
                        Text(
                            dopamineTime,
                            style = LimberTextStyle.Heading1,
                            color = dopamineTimeColor
                        )
                    }
                    Spacer(Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            Modifier
                                .size(120.dp, 24.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 100.dp,
                                        bottomStart = 100.dp,
                                        topEnd = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFFB961FF), Color(0xFF8308D2))
                                    )
                                )
                        )

                        Spacer(Modifier.width(2.dp))

                        Box(
                            Modifier
                                .weight(1f)
                                .height(24.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        bottomStart = 0.dp,
                                        topEnd = 100.dp,
                                        bottomEnd = 100.dp
                                    )
                                )
                                .background(Secondary_Main)
                        )
                    }
                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        FocusActBox(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Gray100)
                                .padding(16.dp)
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        VerticalDivider()

                        Spacer(modifier = Modifier.width(20.dp))

                        DopamineActBox(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Gray100)
                                .padding(16.dp),
                            appInfoList = appInfoList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodayActivityBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFDFB8FF), Color(0xFFFAF5FF))
                    )
                )
//                .clickable(onClick = {})
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_star),
                contentDescription = "Star"
            )
            Spacer(Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    "Today's Activity",
                    style = LimberTextStyle.Body2,
                    color = Gray600
                )
                Row {
                    Text(
                        "집중 시간",
                        style = LimberTextStyle.Heading4,
                        color = Primary_Main
                    )
                    Text(
                        "이 앞서고있어요! 계속 이어가요",
                        style = LimberTextStyle.Heading4,
                        color = Gray600
                    )

                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(modifier = Modifier.size(24.dp), onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "Arrow Forward"
                )
            }
        }
    }
}

@Composable
fun FocusActBox(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .background(
                        color = Color(0xFFB05AF6),
                        shape = RoundedCornerShape(size = 100.dp)
                    )
            )
            Spacer(Modifier.width(6.dp))
            Text("집중한 시간", style = LimberTextStyle.Body2, color = Gray600)
        }
        Spacer(Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row {
                Text("학습", style = LimberTextStyle.Body3, color = Gray400)
                Spacer(Modifier.width(10.dp))
                Text("1시간 2분", style = LimberTextStyle.Body3, color = Gray400)
            }
            Row {
                Text("학습", style = LimberTextStyle.Body3, color = Gray400)
                Spacer(Modifier.width(10.dp))
                Text("1시간 2분", style = LimberTextStyle.Body3, color = Gray400)
            }
            Row {
                Text("학습", style = LimberTextStyle.Body3, color = Gray400)
                Spacer(Modifier.width(10.dp))
                Text("1시간 2분", style = LimberTextStyle.Body3, color = Gray400)
            }
        }

    }
}

@Composable
fun TimerButton(onClick: () -> Unit, timerText: String = "22:22:22") {

    val backgroundModifier = Modifier.background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFF8308D2), Color(0xFFB961FF)),
            start = Offset(0f, 0f),         // 좌상단
            end = Offset.Infinite           // 우하단 (혹은 원하는 크기에 맞게 조절)
        )
    )

    Row(
        Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onClick, enabled = false)
            .background(Color.Red)
            .then(backgroundModifier)
            .padding(horizontal = 8.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_clock),
            modifier = Modifier.size(20.dp),
            contentDescription = null
        )
        Text(timerText, style = LimberTextStyle.Heading2, color = Color.White)
        Image(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            modifier = Modifier.size(20.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}
