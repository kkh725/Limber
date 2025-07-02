package com.kkh.multimodule.timer


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
import java.util.Calendar
import java.util.Timer
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    onClickStartButton: () -> Unit = {}
) {

    val timerViewModel: TimerViewModel = hiltViewModel()
    val uiState by timerViewModel.uiState.collectAsState()

    val selectedFocusChipIndex = uiState.selectedFocusChipIndex
    val timerScreenState = uiState.timerScreenState

    val chipTexts = listOf("하나", "둘", "셋", "넷")

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // true면 Half 없고 바로 Expanded
    )


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
        },
        bottomBar = {
            StartButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 40.dp),
                onClick = onClickStartButton
            )
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
            modifier = Modifier.padding(paddingValues),
            onTimeSelected = { hour, minute ->

            }
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
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
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
    modifier: Modifier = Modifier,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
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

        SpinnerTimePicker(modifier = Modifier.height(224.dp)) { hour, minute ->
            println("선택된 시간: $hour:$minute")
            onTimeSelected(hour, minute)
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
        modifier = modifier
            .height(150.dp)
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
    modifier: Modifier = Modifier,
    values: List<Int>,
    listState: LazyListState,
    label: String
) {
    val itemHeight = 40.dp
    val density = LocalDensity.current

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemScrollOffset to listState.isScrollInProgress
        }
            .debounce(100) // fling 등으로 offset 변화가 잦을 때 완충
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
                    nextIndex.coerceIn(0, maxIndex),
                    0
                )
            }
    }

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LazyColumn(
                state = listState,
                modifier = Modifier.height(120.dp) // ← 5개 보여주려면 높이 늘려주세요
            ) {
                itemsIndexed(listOf("") + values.map {
                    it.toString().padStart(2, '0')
                } + listOf("")) { _, item ->
                    Box(
                        modifier = Modifier.height(itemHeight),
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
    modifier: Modifier,
    onClick: () -> Unit
) {
    LimberSquareButton(
        onClick = onClick,
        text = "시작하기",
        modifier = modifier.height(54.dp)
    )
}




