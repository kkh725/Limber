package com.kkh.multimodule.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider.getUsageAppInfoList
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray700
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Secondary_Light
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Secondary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.DopamineActBox
import com.kkh.multimodule.core.ui.ui.component.LimberText
import com.kkh.multimodule.core.ui.ui.component.RegisterBlockAppBottomSheet
import com.kkh.multimodule.core.ui.util.convertTimeStringToMinutes
import com.kkh.multimodule.core.ui.util.decrementOneSecond
import com.kkh.multimodule.core.ui.util.formatMinutesToHoursMinutes
import com.kkh.multimodule.core.ui.util.getCurrentTimeInKoreanFormat
import com.kkh.multimodule.core.ui.util.isNowWithinTimeRange
import com.kkh.multimodule.core.ui.util.sumTimeStringsToHourMinuteFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToActiveTimer: (String, Int) -> Unit,
    onNavigateToSetTimer: () -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val appInfoList = uiState.usageAppInfoList
    val blockingAppPackageList = uiState.blockingAppPackageList
    val localBlockReservationList = uiState.blockReservationItemList

    val isTimerActive = uiState.isTimerActive
    var leftTime by rememberSaveable { mutableStateOf("00:00:00") }
    val focusDistributionList = uiState.focusDistributionList
    var isSheetVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var checkedList = uiState.checkedList

    val totalDopamineTime =
        sumTimeStringsToHourMinuteFormat(appInfoList.take(3).map { it.usageTime })
    val totalFocusTime =
        formatMinutesToHoursMinutes(focusDistributionList.take(3).sumOf { it.totalActualMinutes })

    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(HomeEvent.EnterHomeScreen(context))
    }

    LaunchedEffect(uiState.leftTime) {
        leftTime = uiState.leftTime
    }

    LaunchedEffect(isTimerActive) {
        // 현재 진행되고 있는 타이머가 존재하는지 확인.
        if (isTimerActive) {
            while (isActive) {
                delay(1000)
                if (leftTime != "00:00:00") {
                    leftTime = decrementOneSecond(leftTime)
                } else {
                    homeViewModel.sendEvent(HomeEvent.EndTimer)
                }
            }
        }
    }

    Image(
        painterResource(R.drawable.bg_home),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )

    Column(
        Modifier
            .fillMaxSize()
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
            navigateToActiveTimer = {
                onNavigateToActiveTimer(it, uiState.currentTimerId)
            },
            navigateToSetTimer = onNavigateToSetTimer,
            leftTime = leftTime,
            totalFocusTime = totalFocusTime,
            focusDistributionList = focusDistributionList,
            totalDopamineTime = totalDopamineTime,
            isTimerActive = isTimerActive
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
            appList = appInfoList,
            checkedList = checkedList,
            onCheckClicked = { index ->
                homeViewModel.sendEvent(HomeEvent.ToggleCheckedIndex(index))
            }
        )
    }
}

@Composable
private fun HomeScreenMainBody(
    modifier: Modifier = Modifier,
    appInfoList: List<AppInfo>,
    navigateToActiveTimer: (String) -> Unit = {},
    navigateToSetTimer: () -> Unit = {},
    leftTime: String = "00:00:00",
    focusDistributionList: List<FocusDistributionModel>,
    totalFocusTime: String = "1시간 2분",
    totalDopamineTime: String = "1시간 0분",
    isTimerActive: Boolean = false
) {
    val focusMinutes = convertTimeStringToMinutes(totalFocusTime)
    val dopamineMinutes = convertTimeStringToMinutes(totalDopamineTime)

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_lv1_good),
                modifier = Modifier.size(114.dp),
                contentDescription = null
            )
            Spacer(Modifier.height(10.dp))
            TimerButton(
                onClickActiveTimer = navigateToActiveTimer,
                onClickStartTimer = navigateToSetTimer,
                leftTime = leftTime,
                isTimerActive = isTimerActive
            )
            Spacer(Modifier.height(24.dp))
            TodayActivityBar(
                modifier = Modifier.padding(horizontal = 20.dp),
                isFocusTimeMore = focusMinutes > dopamineMinutes
            )
            Spacer(Modifier.height(13.dp))
            HomeMainContent(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 20.dp),
                appInfoList = appInfoList,
                focusTime = totalFocusTime,
                focusDistributionList = focusDistributionList,
                dopamineTime = totalDopamineTime
            )
        }
    }
}

@Composable
fun HomeTopBar(
    number: Int,
    onClick: () -> Unit,
    onClickNoti: () -> Unit
) {
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
            Row(
                Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color(0xFF9133E6))
                    .clickable { onClick() }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_apps),
                    contentDescription = "ic_apps"
                )
                Spacer(Modifier.width(5.33.dp))
                Text(
                    "$number",
                    style = LimberTextStyle.Heading5,
                    color = LimberColorStyle.Primary_Light
                )
            }
            Spacer(Modifier.width(6.dp))
            // todo 주석해제
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
    focusDistributionList: List<FocusDistributionModel>,
    focusTime: String,
    dopamineTime: String
) {
    // 문자열 -> 분 단위 변환
    val focusMinutes = convertTimeStringToMinutes(focusTime)
    val dopamineMinutes = convertTimeStringToMinutes(dopamineTime)
    val total = focusMinutes + dopamineMinutes

    // 비율 계산
    val targetFocusRatio = when (focusMinutes) {
        0 -> 0.1f
        else -> if (total > 0) focusMinutes.toFloat() / total else 0.5f
    }
    val targetDopamineRatio = 1f - targetFocusRatio

    // 애니메이션 적용
    val focusRatio by animateFloatAsState(
        targetValue = targetFocusRatio.takeIf { it != 0f } ?: 0.01f,
        animationSpec = tween(durationMillis = 500)
    )
    val dopamineRatio by animateFloatAsState(
        targetValue = targetDopamineRatio.takeIf { it != 0f } ?: 0.01f,
        animationSpec = tween(durationMillis = 500)
    )

    val totalFocusMinutes = focusDistributionList.take(3).sumOf { it.totalActualMinutes }
    val animatedFocusMinutes by animateIntAsState(
        targetValue = totalFocusMinutes,
        animationSpec = tween(durationMillis = 500)
    )
    val animatedDopamineMinutes by animateIntAsState(
        targetValue = dopamineMinutes,
        animationSpec = tween(durationMillis = 500)
    )

    // 텍스트 색상 애니메이션
    val focusTextColor by animateColorAsState(
        targetValue = if (focusMinutes > dopamineMinutes) Gray800 else Gray800.copy(alpha = 0.3f),
        animationSpec = tween(300)
    )
    val dopamineTextColor by animateColorAsState(
        targetValue = if (dopamineMinutes > focusMinutes) Gray800 else Gray800.copy(alpha = 0.3f),
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .height(327.dp)
            .shadow(
                12.dp,
                shape = RoundedCornerShape(12.dp),
                clip = true,
                spotColor = Color(0x14000000)
            )
            .background(Color.White)
            .padding(20.dp, top = 20.dp, end = 20.dp, bottom = 14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("집중한 시간", style = LimberTextStyle.Body2, color = focusTextColor)
                Text("도파민 노출시간", style = LimberTextStyle.Body2, color = dopamineTextColor)
            }
            Spacer(Modifier.height(2.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatMinutesToHoursMinutes(animatedFocusMinutes),
                    style = LimberTextStyle.Heading1,
                    color = focusTextColor
                )
                Text(
                    text = formatMinutesToHoursMinutes(animatedDopamineMinutes),
                    style = LimberTextStyle.Heading1,
                    color = dopamineTextColor
                )
            }
            Spacer(Modifier.height(12.dp))

            // 그래프 애니메이션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Box(
                    Modifier
                        .weight(focusRatio)
                        .fillMaxHeight()
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
                        .weight(dopamineRatio)
                        .fillMaxHeight()
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

            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                FocusActBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(138.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100)
                        .padding(16.dp),
                    focusDistributionList = focusDistributionList
                )
                Spacer(modifier = Modifier.width(12.dp))
                DopamineActBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(138.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100)
                        .padding(16.dp),
                    appInfoList = appInfoList
                )
            }

            Spacer(Modifier.height(14.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LimberText("분석 더보기", LimberTextStyle.Body2, Gray700)
                Image(painterResource(R.drawable.ic_next), contentDescription = "")
            }
        }
    }
}



@Composable
fun TodayActivityBar(
    modifier: Modifier = Modifier,
    isFocusTimeMore: Boolean = false
) {
    val backgroundColor = if (isFocusTimeMore)LimberColorStyle.Primary_BG_Dark else Secondary_Light
    val textColor = if (isFocusTimeMore)LimberColorStyle.Primary_Vivid else Color(0xFFFF462B)
    val imageRes = if (isFocusTimeMore) R.drawable.ic_fire else R.drawable.ic_dopamin
    val title = if (isFocusTimeMore)"집중 시간" else "도파민 노출"
    val subTitle = if (isFocusTimeMore)"이 앞서고있어요! 계속 이어가요" else "이 과다해요! 집중을 더 늘려봐요"


    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            Modifier
                .background(
                    color = backgroundColor, shape = RoundedCornerShape(100.dp)
                )
                .padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(imageRes),
                contentDescription = "Star"
            )
            Spacer(Modifier.width(10.dp))
            Row {
                Text(
                    title,
                    style = LimberTextStyle.Body2,
                    color = textColor
                )
                Text(
                    subTitle,
                    style = LimberTextStyle.Body2,
                    color = Gray800
                )
            }
        }
    }
}

@Composable
fun FocusActBox(
    modifier: Modifier = Modifier,
    focusDistributionList: List<FocusDistributionModel>
) {
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
            Text("집중 활동", style = LimberTextStyle.Body2, color = Gray600)
        }
        Spacer(Modifier.height(16.dp))
        if (focusDistributionList.isEmpty()){
            Text("집중 활동이 없습니다.", style = LimberTextStyle.Body3, color = Gray400)
        }else{
            Column {
                focusDistributionList.take(3).forEach {
                    Row {
                        Text(it.focusTypeName, style = LimberTextStyle.Body3, color = Gray400)
                        Spacer(Modifier.width(10.dp))
                        Text(formatMinutesToHoursMinutes(it.totalActualMinutes), style = LimberTextStyle.Body3, color = Gray400)
                    }
                }
            }
        }
    }
}

@Composable
fun TimerButton(
    onClickActiveTimer: (String) -> Unit,
    onClickStartTimer: () -> Unit,
    leftTime: String = "22:22:22",
    isTimerActive: Boolean
) {

    val backgroundModifier = Modifier.background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFF8308D2), Color(0xFFB961FF)),
            start = Offset(0f, 0f),         // 좌상단
            end = Offset.Infinite           // 우하단 (혹은 원하는 크기에 맞게 조절)
        )
    )
    if (isTimerActive) {
        Row(
            Modifier
                .height(60.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable(onClick = {
                    onClickActiveTimer(leftTime)
                })
                .background(Color.Red)
                .then(backgroundModifier)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_timer),
                contentDescription = null,
            )
            Spacer(Modifier.width(21.5.dp))
            Text(
                text = leftTime,
                style = LimberTextStyle.Heading2,
                color = Color.White,
            )
            Spacer(Modifier.width(8.dp))
            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                modifier = Modifier
                    .size(20.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    } else {
        Row(
            Modifier
                .clip(RoundedCornerShape(100.dp))
                .clickable(onClick = onClickStartTimer)
                .background(Color.Red)
                .then(backgroundModifier)
                .padding(horizontal = 57.dp, vertical = 17.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "집중 시작하기",
                style = LimberTextStyle.Heading5,
                color = Color.White
            )
        }
    }
}
