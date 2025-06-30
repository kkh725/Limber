package com.kkh.multimodule.timer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.ui.component.LimberChip
import com.kkh.multimodule.ui.component.LimberChipWithPlus
import java.util.Calendar
import java.util.Timer

@Composable
fun TimerScreen() {

    val timerViewModel: TimerViewModel = hiltViewModel()
    val uiState by timerViewModel.uiState.collectAsState()

    val selectedFocusChipIndex = uiState.selectedFocusChipIndex
    val timerScreenState = uiState.timerScreenState

    val chipTexts = listOf("하나", "둘", "셋", "넷")

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TimerScreenTopBar(
                selectedTimerType = timerScreenState,
                onClickStartNowBtn = {
                    timerViewModel.sendEvent(
                        TimerEvent.OnClickTimerScreenButton(
                            TimerScreenType.Now
                        )
                    )
                },
                onClickReservationBtn = {
                    timerViewModel.sendEvent(
                        TimerEvent.OnClickTimerScreenButton(
                            TimerScreenType.Reserved
                        )
                    )
                })
        }) { paddingValues ->
        TimerScreenContent(
            chipTexts = chipTexts,
            selectedIndex = selectedFocusChipIndex,
            onSelectedChanged = { newIndex ->
                timerViewModel.sendEvent(TimerEvent.OnClickFocusChip(newIndex))
                if (newIndex == 4) {
                    // 직접 선택.
                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}

@Composable
fun TimerScreenTopBar(
    selectedTimerType: TimerScreenType,
    onClickStartNowBtn: () -> Unit,
    onClickReservationBtn: () -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StartNowButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedTimerType == TimerScreenType.Now,
            onClick = onClickStartNowBtn
        )
        ReservationButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedTimerType == TimerScreenType.Reserved,
            onClick = onClickReservationBtn
        )
    }
}

@Composable
fun StartNowButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        Text(
            "지금 시작", modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            color = if (isSelected) Color.Black else Color.Gray,
            thickness = if (isSelected) 2.dp else 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ReservationButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        Text(
            "에약 설정", modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            color = if (isSelected) Color.Black else Color.Gray,
            thickness = if (isSelected) 2.dp else 1.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun FocusChipRow(
    texts: List<String>,
    selectedIndex: Int,
    onSelectedChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        texts.forEachIndexed { index, text ->
            LimberChip(
                text = text,
                isSelected = selectedIndex == index,
                onClick = {
                    onSelectedChanged(index)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LimberChipWithPlus(
            "추가하기",
            isSelected = selectedIndex == 4
        ) {
            onSelectedChanged(4)
        }
    }
}

@Composable
fun TimerScreenContent(
    chipTexts: List<String>,
    selectedIndex: Int,
    onSelectedChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text("무엇에 집중할 것이오..")

        Spacer(modifier = Modifier.height(24.dp))

        FocusChipRow(
            texts = chipTexts,
            selectedIndex = selectedIndex,
            onSelectedChanged = onSelectedChanged,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(52.dp))

        Text("무엇에 집중할 것이오..")

        SpinnerTimePicker { hour, minute ->
            println("선택된 시간: $hour:$minute")
        }

    }
}
@Composable
fun SpinnerTimePicker(
    modifier: Modifier = Modifier,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val hourList = (0..23).toList()
    val minuteList = (0..59).toList()

    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = 8)
    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = 30)

    val selectedHour = remember { derivedStateOf { hourList.getOrNull(hourState.firstVisibleItemIndex + 1) ?: 0 } }
    val selectedMinute = remember { derivedStateOf { minuteList.getOrNull(minuteState.firstVisibleItemIndex + 1) ?: 0 } }

    Box(
        modifier = modifier
            .height(150.dp)
            .padding(16.dp)
    ) {
        // ✅ 라인 먼저 배경에 깔기 (40dp 높이)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xFFEAEAEA).copy(alpha = 0.3f))
        )

        // ✅ Row 위에 얹기
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            NumberPicker(
                values = hourList,
                listState = hourState,
                label = "시"
            )
            NumberPicker(
                values = minuteList,
                listState = minuteState,
                label = "분"
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun NumberPicker(
    values: List<Int>,
    listState: androidx.compose.foundation.lazy.LazyListState,
    label: String
) {
    Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .height(120.dp)
        ) {
            itemsIndexed(listOf("") + values.map { it.toString().padStart(2, '0') } + listOf("")) { _, item ->
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = 20.sp,
                        color = if (item.isNotBlank()) Color.Black else Color.Transparent
                    )
                }
            }
        }

        // 가운데 강조 라인
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xFFEAEAEA).copy(alpha = 0.3f))
        )

        // 고정된 label
        Text(
            text = label,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
        )
    }
}

