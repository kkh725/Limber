package com.kkh.multimodule.feature.home.recall

import android.annotation.SuppressLint
import android.graphics.Color.alpha
import android.widget.Space
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.ui.WarnDialog
import com.kkh.multimodule.core.ui.ui.component.LimberCloseButton
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.core.ui.ui.component.LimberOutlinedTextField
import com.kkh.multimodule.core.ui.ui.component.LimberText
import com.kkh.multimodule.core.ui.util.getTodayInKoreanFormat
import com.kkh.multimodule.feature.home.activeTimer.ActiveTimerEvent
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecallScreen(
    onPopBackStack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val recallViewModel: RecallViewModel = hiltViewModel()
    val uiState by recallViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val text = uiState.focusText
    val isModalVisible = uiState.modalState

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetOpen = uiState.sheetState

    var selectedIndex by remember { mutableIntStateOf(-1) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .systemBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))

        RecallTopBar(
            modifier = Modifier.fillMaxWidth(),
            type = "학습",
            onClickClose = {
                if (text.isEmpty()) {
                    onPopBackStack()
                } else {
                    recallViewModel.sendEvent(RecallEvent.SetModalState(true))
                }
            }
        )
        Spacer(Modifier.height(20.dp))
        Box(
            Modifier
                .background(Primary_Dark, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                "작은 회고가 쌓여 나만의 집중 루틴을 만들어줄거에요",
                style = LimberTextStyle.Body3,
                color = LimberColorStyle.Primary_Main
            )
        }

        Spacer(Modifier.height(32.dp))

        LimberText("이번 실험, 얼마나 집중했나요?", LimberTextStyle.Heading3, Gray800)

        Spacer(Modifier.height(28.dp))

        Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)

        Spacer(Modifier.height(4.dp))
        SelectFocusDegree(selectedIndex, onSelectedIndexChange = {
            selectedIndex = it
        })
        Spacer(Modifier.height(32.dp))
        RecallTextFieldBox(text, onClick = {
            recallViewModel.sendEvent(
                RecallEvent.SetSheetState(
                    true
                )
            )
        })

        Spacer(Modifier.weight(1f))
        LimberGradientButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "저장하기",
            textColor = Color.White
        )

    }
    // BottomSheet
    if (isSheetOpen) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                recallViewModel.sendEvent(
                    RecallEvent.SetSheetState(
                        false
                    )
                )
            },
            sheetState = sheetState
        ) {
            BottomSheetContent(
                value = text,
                onValueChange = {
                    recallViewModel.sendEvent(RecallEvent.OnValueChange(it))
                },
                onClose = {
                    coroutineScope.launch {
                        sheetState.hide()
                        recallViewModel.sendEvent(
                            RecallEvent.SetSheetState(
                                false
                            )
                        )
                    }
                },
                onClickSheetComplete = {
                    coroutineScope.launch {
                        sheetState.hide()
                        recallViewModel.sendEvent(RecallEvent.OnClickSheetComplete)
                    }
                }
            )
        }
    }
    if (isModalVisible) {
        Dialog({ recallViewModel.sendEvent(RecallEvent.SetModalState(false)) }) {
            RecallCloseWarnModal(
                onClickClose = {
                    recallViewModel.sendEvent(RecallEvent.SetModalState(false))
                    onPopBackStack()
                },
                onClickCancel = {
                    recallViewModel.sendEvent(RecallEvent.SetModalState(false))
                    onNavigateToHome()
                }
            )
        }
    }
}

@Composable
fun RecallTopBar(modifier: Modifier = Modifier, type: String = "학습", onClickClose: () -> Unit) {
    val now = getTodayInKoreanFormat()

    Box(modifier) {
        Text(
            text = "$now $type 실험",
            style = LimberTextStyle.Body1,
            color = Gray400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        LimberCloseButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onClickClose
        )
    }
}

@Preview
@Composable
fun SelectFocusDegree(selectedIndex: Int = -1, onSelectedIndexChange: (Int) -> Unit = {}) {

    // 이미지 리소스 맵
    val imageResources = listOf(
        R.drawable.ic_percent20,
        R.drawable.ic_percent60,
        R.drawable.ic_percent100
    )

    // 텍스트 색상 선택 함수
    fun textColorForIndex(index: Int) =
        if (selectedIndex == index) LimberColorStyle.Primary_Main else Gray400

    Column(
        Modifier.width(290.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box로 이미지 위치 조정
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(64.dp), // 이미지 높이에 따라 조절
            contentAlignment = when (selectedIndex) {
                0 -> Alignment.CenterStart
                1 -> Alignment.Center
                else -> Alignment.CenterEnd
            }
        ) {
            Image(
                painter = painterResource(
                    imageResources.getOrElse(selectedIndex) { R.drawable.ic_percent100 }
                ),
                contentDescription = null,
                modifier = Modifier.size(48.dp) // 필요 시 이미지 크기 지정
            )
        }

        ThreeCirclesWithBarFixed(
            selectedIndex = selectedIndex,
            onSelectedIndexChange = onSelectedIndexChange
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
//            Spacer(Modifier.width(20.dp))
            listOf("거의 못했어요", "꽤 집중했어요", "완전 몰입했어요").forEachIndexed { index, text ->
                LimberText(
                    text = text,
                    style = LimberTextStyle.Body2,
                    color = textColorForIndex(index),
                    modifier = Modifier.padding(end = if (index != 2) 23.dp else 0.dp)
                )
            }
        }
    }
}


@Preview
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ThreeCirclesWithBarFixed(selectedIndex: Int = -1, onSelectedIndexChange: (Int) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val dragOffset = remember { Animatable(0f) }

    val baseCircleSize = 20.dp
    val selectedCircleSize = 32.dp
    val whiteCircleSize = 24.dp
    val whiteCircleRadiusPx = with(density) { whiteCircleSize.toPx() / 2 }

    BoxWithConstraints(
        modifier = Modifier
            .width(234.dp)
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val boxWidthPx = with(density) { maxWidth.toPx() }

        // animateDpAsState를 사용한 circleSizes (State<Dp> 리스트)
        val circleSizes = listOf(0, 1, 2).map { i ->
            animateDpAsState(
                targetValue = if (i == selectedIndex) selectedCircleSize else baseCircleSize,
                animationSpec = tween(durationMillis = 300),
                label = "circleSize_$i"
            )
        }

        // State<Dp> -> Dp 값 추출 후 px 변환
        val circleRadiiPx = circleSizes.map { with(density) { it.value.toPx() / 2 } }

        // circleRadiiPx 변화에 따라 positions 재계산
        val positions = remember(boxWidthPx, circleRadiiPx[0], circleRadiiPx[2]) {
            listOf(
                0f + circleRadiiPx[0] + 18f,          // 좌측 원 중심
                boxWidthPx / 2f,                // 가운데 원 중심 (가운데는 크기 변화가 있어도 중심은 변하지 않음)
                boxWidthPx - circleRadiiPx[2] - 18f // 우측 원 중심
            )
        }

        // 막대기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(LimberColorStyle.Primary_Main, shape = RoundedCornerShape(50))
        )

        // 고정 원들 (애니메이션 크기 반영)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            circleSizes.forEachIndexed { i, sizeState ->
                Box(
                    modifier = Modifier
                        .size(sizeState.value)
                        .background(LimberColorStyle.Primary_Main, CircleShape)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            scope.launch {
                                // 흰 원 위치 애니메이션 이동
                                val targetOffset =
                                    positions[i] - with(density) { whiteCircleSize.toPx() / 2 }
                                dragOffset.animateTo(targetOffset, animationSpec = tween(300))
                                onSelectedIndexChange(i)
                            }
                        }
                )
            }
        }


        // 드래그 가능한 흰 원
        Box(
            modifier = Modifier
                .offset { IntOffset(dragOffset.value.roundToInt(), 0) }
                .size(whiteCircleSize)
                .background(Color.White, CircleShape)
                .pointerInput(selectedIndex) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                dragOffset.snapTo(dragOffset.value + dragAmount.x)
                            }
                        },
                        onDragEnd = {
                            val currentCenter = dragOffset.value + whiteCircleRadiusPx
                            val (closestCenter, newIndex) = positions
                                .mapIndexed { i, center -> center to i }
                                .minByOrNull { abs(it.first - currentCenter) }!!

                            val targetOffset = when (newIndex) {
                                0 -> closestCenter - whiteCircleRadiusPx
                                1 -> closestCenter - whiteCircleRadiusPx
                                else -> closestCenter - whiteCircleRadiusPx
                            }

                            scope.launch {
                                dragOffset.animateTo(targetOffset, animationSpec = tween(300))
                                onSelectedIndexChange(newIndex)
                            }
                        })
                }
                .align(Alignment.CenterStart)
        )
    }
}

@Composable
fun RecallTextFieldBox(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp) // 적당한 모서리 곡률
            )
            .clickable { onClick() }
            .padding(start = 20.dp, top = 20.dp)
    ) {
        if (value.isEmpty()) {
            LimberText(
                text = "구체적으로 어떤 일에 집중했나요?",
                style = LimberTextStyle.Body2,
                color = LimberColorStyle.Gray400
            )
        } else {
            LimberText(
                text = value,
                style = LimberTextStyle.Body2,
                color = Color.White // 입력된 텍스트는 흰색 처리 예시
            )
        }
    }
}

@Composable
fun BottomSheetContent(
    value: String,
    onValueChange: (String) -> Unit,
    onClose: () -> Unit,
    onClickSheetComplete: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // BottomSheet가 컴포지션되자마자 포커스 요청
    LaunchedEffect(Unit) {
        delay(100) // 살짝 딜레이 줘야 안전함
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "텍스트 입력하기",
                    style = LimberTextStyle.Heading5,
                    color = Gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        keyboardController?.hide()
                        onClose()
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Gray600
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // 포커스 리퀘스터 전달
            FocusTextField(
                value = value,
                onValueChange = onValueChange,
                focusRequester = focusRequester
            )

            Spacer(Modifier.weight(1f))

            LimberGradientButton(
                modifier = Modifier.fillMaxWidth().imePadding(),
                onClick = {
                    keyboardController?.hide()
                    onClickSheetComplete()
                },
                text = "완료",
                textColor = Color.White
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}


@Composable
fun FocusTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "경제학 공부",
    placeholderColor: Color = Gray400,
    helperText: String = "50자 이내로 작성해주세요.",
    helperColor: Color = Gray500,
    focusRequester: FocusRequester = remember { FocusRequester() } // default 제공
) {
    Column(modifier.fillMaxWidth()) {
        LimberOutlinedTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
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


@Preview(showBackground = true)
@Composable
fun RecallCloseWarnModal(
    onClickCancel: () -> Unit = {},
    onClickClose: () -> Unit = {}
) {
    Column(
        Modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            "페이지를 나가시겠어요?\n" +
                    "이대로 나가면 작성 중인 회고가 사라져요.",
            style = LimberTextStyle.Heading4,
            textAlign = TextAlign.Center,
            color = LimberColorStyle.Gray800
        )
        Spacer(Modifier.height(32.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LimberSquareButton(
                modifier = Modifier.weight(1f),
                onClick = onClickCancel,
                text = "취소",
                containerColor = Gray200,
                textColor = LimberColorStyle.Gray800
            )
            Spacer(Modifier.width(8.dp))
            LimberSquareButton(
                modifier = Modifier.weight(1f),
                onClick = onClickClose,
                text = "닫기",
                textColor = Color.White
            )
        }
        Spacer(Modifier.height(12.dp))
    }
}