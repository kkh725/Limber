package com.kkh.multimodule.feature.timer


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.designsystem.snackbar.showImmediately
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.feature.reservation.ReservationPage
import com.kkh.multimodule.core.ui.ui.WarnDialog
import com.kkh.multimodule.core.ui.ui.component.LimberChip
import com.kkh.multimodule.core.ui.ui.component.LimberChipWithPlus
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.core.ui.ui.component.LimberSnackBar
import com.kkh.multimodule.core.ui.ui.component.LimberText
import com.kkh.multimodule.core.ui.ui.component.LimberTimePicker24
import com.kkh.multimodule.core.ui.ui.component.RegisterBlockAppBottomSheet
import com.kkh.multimodule.core.ui.util.getCurrentTimeInKoreanFormat
import com.kkh.multimodule.core.ui.util.getStartAndEndTime
import com.kkh.multimodule.feature.reservation.SideEffect
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(onNavigateToActiveHome: () -> Unit, onNavigateToHome: () -> Unit) {
    val timerViewModel: TimerViewModel = hiltViewModel()
    val uiState by timerViewModel.uiState.collectAsState()

    var isVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val timerScreenState = uiState.timerScreenState
    val chipList = uiState.chipList
    val selectedChip = chipList.find { it.isSelected }
    val context = LocalContext.current
    val isTimerActive = uiState.isTimerActive

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetVisible = uiState.isSheetVisible
    val isModalVisible = uiState.isModalVisible

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    val appList = uiState.appDataList

    val checkedList = uiState.checkedList
    val modalAppList = uiState.modalAppDataList

    var selectedTime by remember { mutableStateOf(LocalTime(1, 0)) }

    LaunchedEffect(Unit) {
        timerViewModel.sendEvent(TimerEvent.OnEnterTimerScreen(context))

        timerViewModel.sideEffect.collect { effect ->
            when (effect) {
                is CommonEffect.NavigateToHome -> {
                    onNavigateToHome()
                }

                is CommonEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackbarHostState.showImmediately(effect.message)
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> timerViewModel.sendEvent(TimerEvent.OnClickTimerScreenButton(TimerScreenType.Now))
            1 -> timerViewModel.sendEvent(TimerEvent.OnClickTimerScreenButton(TimerScreenType.Reserved))
        }
    }

    Scaffold(
        containerColor = Gray50,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TimerScreenTopBar(
                selectedTimerType = timerScreenState,
                onClickStartNowBtn = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                onClickReservationBtn = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        },
        bottomBar = {
            if (pagerState.currentPage == 0 && !uiState.isTimerActive) {
                StartButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    enabled = chipList.any { it.isSelected },
                    onClick = { timerViewModel.sendEvent(TimerEvent.ShowModal(true, context)) }
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(paddingValues)
        ) { page ->
            when (page) {
                0 -> TimerScreenContent(
                    chipList = chipList,
                    onSelectedChanged = { chipText ->
                        if (chipText.isEmpty()) {
                            timerViewModel.sendEvent(TimerEvent.OnClickFocusChip("")) // 해제
                        } else {
                            timerViewModel.sendEvent(TimerEvent.OnClickFocusChip(chipText))
                        }
                    },
                    isActive = isTimerActive,
                    snackbarHostState = snackbarHostState,
                    selectedTime = selectedTime,
                    onValueChanged = { newTime ->
                        selectedTime = newTime
                    }
                )

                1 -> ReservationPage(
                    modifier = Modifier,
                    onNavigateToHome = onNavigateToHome
                )
            }
        }
    }
    if (isModalVisible) {
        Dialog({ timerViewModel.sendEvent(TimerEvent.ShowModal(false, context)) }) {
            WarnDialog(
                title = "${selectedTime.hour}시간 ${selectedTime.minute}분 동안\n 다음의 앱들이 차단돼요",
                appInfoList = modalAppList,
                onClickModifyButton = {
                    timerViewModel.sendEvent(TimerEvent.ShowSheet(true, context))
                },
                onClickStartButton = {
                    val (startTime, endTime) = getStartAndEndTime(selectedTime.toString())
                    timerViewModel.sendEvent(
                        TimerEvent.OnClickStartTimerNow(
                            startBlockReservationInfo = ReservationItemModel.currentActive.copy(
                                reservationInfo = ReservationInfo.init().copy(
                                    startTime = startTime,
                                    endTime = endTime,
                                )
                            ), context = context
                        )
                    )
                },
                onDismissRequest = {
                    timerViewModel.sendEvent(TimerEvent.ShowModal(false, context))
                }
            )
        }
    }

    if (isSheetVisible) {
        RegisterBlockAppBottomSheet(
            sheetState = sheetState, onDismissRequest = {
                timerViewModel.sendEvent(TimerEvent.ShowSheet(false, context))
            }, onClickComplete = { checkedAppList ->
                timerViewModel.sendEvent(TimerEvent.OnClickSheetCompleteButton(checkedAppList))
            }, appList = appList, checkedList = checkedList,
            onCheckClicked = { index ->
                timerViewModel.sendEvent(TimerEvent.ToggleCheckedIndex(index))
            })
    }
}

@Preview
@Composable
fun TimerScreenTopBar(
    modifier: Modifier = Modifier,
    selectedTimerType: TimerScreenType = TimerScreenType.Now,
    onClickStartNowBtn: () -> Unit = {},
    onClickReservationBtn: () -> Unit = {}
) {

    Row(
        modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimerSelectorButton(
            modifier = Modifier.weight(1f),
            text = "지금 시작",
            isSelected = selectedTimerType == TimerScreenType.Now,
            onClick = onClickStartNowBtn
        )
        TimerSelectorButton(
            modifier = Modifier.weight(1f),
            text = "예약 설정",
            isSelected = selectedTimerType == TimerScreenType.Reserved,
            onClick = onClickReservationBtn
        )
    }
}

@Composable
fun TimerSelectorButton(
    modifier: Modifier = Modifier, text: String, isSelected: Boolean, onClick: () -> Unit
) {

    val textColor = if (isSelected) Gray800 else LimberColorStyle.Gray400
    Box(
        modifier
            .background(Color.White)
            .fillMaxHeight()
            .clickable(
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            style = LimberTextStyle.Heading5,
            color = textColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            color = if (isSelected) LimberColorStyle.Primary_Main else LimberColorStyle.Gray300,
            thickness = if (isSelected) 2.dp else 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun FocusChipRow(
    chipList: List<ChipInfo>,
    onSelectedChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        chipList.forEach { chip ->
            if (chip.text == "직접 추가") {
                LimberChipWithPlus(
                    text = chip.text,
                    isSelected = chip.isSelected,
                    onClick = {
                        if (chip.isSelected) {
                            onSelectedChanged("") // 해제
                        } else {
                            onSelectedChanged(chip.text)
                        }
                    }
                )
            } else {
                LimberChip(
                    text = chip.text,
                    isSelected = chip.isSelected,
                    imageResId = chip.imageResId,
                    onClick = {
                        if (chip.isSelected) {
                            onSelectedChanged("") // 해제
                        } else {
                            onSelectedChanged(chip.text)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenContent(
    chipList: List<ChipInfo> = ChipInfo.mockList,
    onSelectedChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    selectedTime: LocalTime = LocalTime.fromSecondOfDay(1200),
    onValueChanged: (LocalTime) -> Unit = {}
) {
    Box(modifier.fillMaxSize()) {
        if (isActive) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(280.dp),
                    painter = painterResource(R.drawable.bg_isactive_timer),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(12.dp))
                LimberText("현재 진행중인 실험이 있어요", style = LimberTextStyle.Heading1, Gray800)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "실험이 종료된 후에\n" +
                            "새로운 실험을 시작할 수 있어요",
                    style = LimberTextStyle.Body2,
                    color = Gray600,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    "어떤 활동에 집중하고 싶은가요?",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = LimberTextStyle.Heading3,
                    color = Gray800
                )

                FocusChipRow(
                    chipList = chipList,
                    onSelectedChanged = onSelectedChanged,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(52.dp))

                Text(
                    "얼마 동안 집중할까요?",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = LimberTextStyle.Heading3,
                    color = Gray800
                )
                Spacer(modifier = Modifier.height(22.dp))

                LimberTimePicker24(
                    onValueChanged = onValueChanged
                )
            }
        }
        SnackbarHost(
            snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 15.5.dp)
        ) {
            LimberSnackBar(
                text = snackbarHostState.currentSnackbarData?.visuals?.message ?: "Unknown"
            )
        }
    }
}

@Composable
fun StartButton(
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false
) {
    LimberGradientButton(
        onClick = onClick, enabled = enabled, text = "시작하기", modifier = modifier.height(54.dp)
    )
}




