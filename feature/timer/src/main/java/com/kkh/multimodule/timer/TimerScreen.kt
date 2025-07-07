package com.kkh.multimodule.timer


import android.content.Context
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberColorStyle.Primary_BG_Normal
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.reservation.ReservationPage
import com.kkh.multimodule.ui.WarnDialog
import com.kkh.multimodule.ui.component.LimberChip
import com.kkh.multimodule.ui.component.LimberChipWithPlus
import com.kkh.multimodule.ui.component.LimberGradientButton
import com.kkh.multimodule.ui.component.RegisterBlockAppBottomSheet
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    val timerViewModel: TimerViewModel = hiltViewModel()
    val uiState by timerViewModel.uiState.collectAsState()

    val timerScreenState = uiState.timerScreenState
    val chipList = uiState.chipList
    val selectedChip = chipList.find { it.isSelected }
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetVisible = uiState.isSheetVisible
    val isModalVisible = uiState.isModalVisible

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    val appList = uiState.appDataList

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> timerViewModel.sendEvent(TimerEvent.OnClickTimerScreenButton(TimerScreenType.Now))
            1 -> timerViewModel.sendEvent(TimerEvent.OnClickTimerScreenButton(TimerScreenType.Reserved))
        }
    }

    Scaffold(
        topBar = {
            TimerScreenTopBar(
                selectedTimerType = timerScreenState,
                onClickStartNowBtn = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }

                    timerViewModel.sendEvent(
                        TimerEvent.OnClickTimerScreenButton(TimerScreenType.Now)
                    )
                },
                onClickReservationBtn = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                    timerViewModel.sendEvent(
                        TimerEvent.OnClickTimerScreenButton(TimerScreenType.Reserved)
                    )
                }
            )
        },
        bottomBar = {
            if (pagerState.currentPage == 0) {
                StartButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 40.dp),
                    enabled = chipList.any { it.isSelected },
                    onClick = { timerViewModel.sendEvent(TimerEvent.ShowModal(true)) }
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
                    onTimeSelected = { hour, minute -> },
                    modifier = Modifier.fillMaxSize()
                )

                1 -> ReservationPage(
                    modifier = Modifier
                )
            }
        }

    }
    if (isModalVisible) {
        Dialog({ timerViewModel.sendEvent(TimerEvent.ShowModal(false)) }) {
            WarnDialog(onClickModifyButton = {
                timerViewModel.sendEvent(TimerEvent.ShowSheet(true, context))
            }, onDismissRequest = { timerViewModel.sendEvent(TimerEvent.ShowModal(false)) })
        }
    }

    if (isSheetVisible) {
        RegisterBlockAppBottomSheet(sheetState = sheetState, onDismissRequest = {
            timerViewModel.sendEvent(TimerEvent.ShowSheet(false,context))
        }, onClickComplete = { checkedAppList ->
            checkedAppList.forEach {
                println(it.appName)
            }
            timerViewModel.sendEvent(TimerEvent.ShowSheet(false,context))
        }, appList = appList)
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}

@Composable
fun TimerScreenTopBar(
    modifier: Modifier = Modifier,
    selectedTimerType: TimerScreenType,
    onClickStartNowBtn: () -> Unit,
    onClickReservationBtn: () -> Unit
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
    Box(
        modifier
            .fillMaxHeight()
            .clickable(
                onClick = onClick
            )
    ) {
        Text(
            text = text, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            color = if (isSelected) LimberColorStyle.Primary_Main else Color.Gray,
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


@Composable
fun TimerScreenContent(
    chipList: List<ChipInfo>,
    onSelectedChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            "무엇에 집중하고 싶으신가요?",
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
            "얼마나 집중하시겠어요?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )

        SpinnerTimePicker(modifier = Modifier.height(224.dp)) { hour, minute ->
            println("선택된 시간: $hour:$minute")
            onTimeSelected(hour, minute)
        }
    }
}


@Composable
fun SpinnerTimePicker(
    modifier: Modifier = Modifier, onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val hourList = (0..23).toList()
    val minuteList = (0..59).toList()

    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = 8 + 1) // padding 1개 포함
    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = 30 + 1)


    val selectedHour =
        remember { derivedStateOf { hourList.getOrNull(hourState.firstVisibleItemIndex + 1) ?: 0 } }
    val selectedMinute = remember {
        derivedStateOf {
            minuteList.getOrNull(minuteState.firstVisibleItemIndex + 1) ?: 0
        }
    }

    // ✅ 값이 바뀔 때마다 외부 콜백 호출
    LaunchedEffect(selectedHour.value, selectedMinute.value) {
        onTimeSelected(selectedHour.value, selectedMinute.value)
    }

    Box(
        modifier = modifier.height(150.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 27.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Primary_BG_Normal)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 27.dp)
        ) {
            NumberPicker(
                modifier = Modifier.weight(1f),
                values = hourList,
                listState = hourState,
                label = "시간"
            )
            NumberPicker(
                modifier = Modifier.weight(1f),
                values = minuteList,
                listState = minuteState,
                label = "분"
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(FlowPreview::class)
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier, values: List<Int>, listState: LazyListState, label: String
) {
    val itemHeight = 40.dp
    val density = LocalDensity.current

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemScrollOffset to listState.isScrollInProgress
        }.debounce(100) // fling 등으로 offset 변화가 잦을 때 완충
            .filter { !it.second } // 스크롤이 멈췄을 때
            .collect { (offset, _) ->
                val visibleIndex = listState.firstVisibleItemIndex
                val itemHeightPx = with(density) { itemHeight.toPx() }

                val nextIndex = if (offset >= itemHeightPx / 2f) {
                    visibleIndex + 1
                } else {
                    visibleIndex
                }

                val maxIndex = values.size + 1 // 패딩 포함

                listState.animateScrollToItem(
                    nextIndex.coerceIn(0, maxIndex), 0
                )
            }
    }

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LazyColumn(
                state = listState, modifier = Modifier.height(120.dp) // ← 5개 보여주려면 높이 늘려주세요
            ) {
                itemsIndexed(listOf("") + values.map {
                    it.toString().padStart(2, '0')
                } + listOf("")) { _, item ->
                    Box(
                        modifier = Modifier.height(itemHeight), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            fontSize = 20.sp,
                            color = if (item.isNotBlank()) Color.Black else Color.Transparent
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(22.dp))
            Text(
                text = label,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun StartButton(
    modifier: Modifier, onClick: () -> Unit, enabled: Boolean = false
) {
    LimberGradientButton(
        onClick = onClick, enabled = enabled, text = "시작하기", modifier = modifier.height(54.dp)
    )
}




