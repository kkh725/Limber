package com.kkh.multimodule.reservation.bottomsheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.reservation.ReservationEvent
import com.kkh.multimodule.reservation.ReservationViewModel
import com.kkh.multimodule.reservation.BottomSheetState
import com.kkh.multimodule.timer.ChipInfo
import com.kkh.multimodule.timer.FocusChipRow
import com.kkh.multimodule.ui.component.LimberCloseButton
import com.kkh.multimodule.ui.component.LimberGradientButton
import com.kkh.multimodule.ui.component.LimberOutlinedTextField
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val reservationViewModel: ReservationViewModel = hiltViewModel()

    val uiState by reservationViewModel.uiState.collectAsState()
    val bottomSheetState = uiState.reservationBottomSheetState
    val chipList = uiState.chipList
    val repeatOptionList = uiState.repeatOptionList
    val dayList = uiState.dayList

    val targetHeightFraction = when (bottomSheetState) {
        BottomSheetState.Idle -> 0.9f
        BottomSheetState.Start -> 0.5f
        BottomSheetState.End -> 0.5f
        BottomSheetState.Repeat -> 0.5f
    }

    // 이전 값을 기억하기 위해 remember 사용
    var previousHeightFraction by remember { mutableFloatStateOf(targetHeightFraction) }

    val animatedHeightFraction by animateFloatAsState(
        targetValue = targetHeightFraction,
        animationSpec = if (targetHeightFraction > previousHeightFraction) {
            // 올라갈 때
            tween(durationMillis = 300, easing = FastOutSlowInEasing)
        } else {
            // 내려갈 때
            tween(durationMillis = 300, easing = FastOutLinearInEasing)
        },
        label = "BottomSheetHeightAnimation"
    )

    // 새로운 값이 들어오면 이전 값 업데이트
    LaunchedEffect(targetHeightFraction) {
        previousHeightFraction = targetHeightFraction
    }

    ModalBottomSheet(
        containerColor = Color.White,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(animatedHeightFraction)
        ) {
            AnimatedContent(
                targetState = bottomSheetState,
                transitionSpec = {
                    fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                },
                label = "BottomSheetContentAnimation"
            ) { state ->
                when (state) {
                    BottomSheetState.Idle -> {
                        ReservationBottomSheetContent(
                            onClickTimerButton = { buttonIndex ->
                                val targetState = when (buttonIndex) {
                                    0 -> BottomSheetState.Start
                                    1 -> BottomSheetState.End
                                    2 -> BottomSheetState.Repeat
                                    else -> BottomSheetState.Idle
                                }
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.NavigateTo(targetState)
                                )
                            },
                            onClickClose = {
                                scope.launch {
                                    sheetState.hide()
                                    reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Close)
                                }
                            },
                            chipList = chipList,
                            onSelectedChanged = {}
                        )
                    }

                    BottomSheetState.Start -> {
                        StartTimerContent(
                            onClickBack = {
                                reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Back)
                            },
                            onClickClose = {
                                scope.launch {
                                    sheetState.hide()
                                    reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Close)
                                }
                            },
                            onClickComplete = {
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.NavigateTo(
                                        BottomSheetState.Idle
                                    )
                                )
                            },
                            head = "반복 설정"
                        )
                    }

                    BottomSheetState.End -> {
                        EndTimerContent(
                            onClickBack = {
                                reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Back)
                            },
                            onClickClose = {
                                scope.launch {
                                    sheetState.hide()
                                    reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Close)
                                }
                            },
                            onClickComplete = {
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.NavigateTo(
                                        BottomSheetState.Idle
                                    )
                                )
                            },
                            head = "반복 설정"
                        )
                    }

                    BottomSheetState.Repeat -> {
                        RepeatTimerContent(
                            repeatOptionList = repeatOptionList,
                            dayList = dayList,
                            onClickBack = {
                                reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Back)
                            },
                            onClickClose = {
                                scope.launch {
                                    sheetState.hide()
                                    reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Close)
                                }
                            },
                            head = "반복 설정",
                            onClickComplete = {
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.OnClickRepeatOptionCompleteButton
                                )
                            },
                            onClickOption = { chipText ->
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.OnClickRepeatOptionChip(
                                        chipText
                                    )
                                )
                            },
                            onClickDay = { day ->
                                reservationViewModel.sendEvent(
                                    ReservationEvent.BottomSheet.OnClickDayChip(
                                        day
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ReservationBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    ReservationBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
    )
}


@Preview(showBackground = true)
@Composable
fun ReservationBottomSheetContent(
    onClickClose: () -> Unit = {},
    chipList: List<ChipInfo> = listOf(),
    onSelectedChanged: (String) -> Unit = {},
    onClickTimerButton: (Int) -> Unit = {}
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ReservationTopBar(onClickClose = onClickClose)
            Spacer(Modifier.height(33.dp))
            ReservationSetting(
                value = "",
                onValueChange = {}
            )
            Spacer(Modifier.height(20.dp))
            HorizontalDivider(thickness = 4.dp, color = Gray200)
            Spacer(Modifier.height(40.dp))

            ReservationFocusSection(
                chipList = chipList,
                onSelectedChanged = onSelectedChanged
            )

            ReservationTimerSection(onClickButton = onClickTimerButton)
            Spacer(Modifier.height(120.dp))

        }
    }
    Box(Modifier.fillMaxSize()) {
        LimberGradientButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            text = "예약하기"
        )
    }

}

@Composable
fun ReservationFocusSection(
    chipList: List<ChipInfo>,
    onSelectedChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            "무엇에 집중하고 싶으신가요?",
            style = LimberTextStyle.Heading4
        )
        Spacer(Modifier.height(12.dp))
        FocusChipRow(
            chipList = chipList,
            onSelectedChanged = onSelectedChanged
        )
        Spacer(Modifier.height(40.dp))
        Text(
            "얼마나 집중하시겠어요?",
            style = LimberTextStyle.Heading4
        )
    }
}

@Composable
fun ReservationTopBar(onClickClose: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, end = 20.dp)
    ) {
        Text(
            "실험 예약하기",
            style = LimberTextStyle.Heading4,
            modifier = Modifier.align(Alignment.Center)
        )
        LimberCloseButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onClickClose
        )
    }
}

@Composable
fun ReservationSetting(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    titleText: String = "예약할 실험의 제목을 설정해주세요.",
    placeholderText: String = "ex. 포트폴리오 작업, 영어 공부",
    placeholderColor: Color = Gray400,
    helperText: String = "50자 이내로 작성해주세요.",
    helperColor: Color = Gray500
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(titleText, style = LimberTextStyle.Heading4)

        Spacer(Modifier.height(12.dp))
        LimberOutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholderText,
                    style = LimberTextStyle.Body1,
                    color = placeholderColor
                )
            }
        )
        Spacer(Modifier.height(8.dp))
        Text(
            helperText,
            style = LimberTextStyle.Body3,
            color = helperColor
        )
    }
}

@Composable
fun ReservationTimerSection(
    buttons: List<Pair<String, String>> = listOf(
        "시작" to "오후 6시 30분",
        "종료" to "오후 8시 30분",
        "반복" to "매일"
    ),
    onClickButton: (Int) -> Unit = {}
) {
    Spacer(Modifier.height(40.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        buttons.forEachIndexed { idx, (label, time) ->
            ReservationTimerButton(
                label = label,
                time = time,
                onClick = { onClickButton(idx) }
            )
        }
    }
}

@Composable
fun ReservationTimerButton(
    label: String,
    time: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Gray100)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = LimberTextStyle.Body1, color = Gray600)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(time, style = LimberTextStyle.Body1, color = Gray600)
            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}