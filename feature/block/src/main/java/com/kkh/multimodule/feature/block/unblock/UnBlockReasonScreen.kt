package com.kkh.multimodule.feature.block.unblock

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberAnimation
import com.kkh.multimodule.core.ui.ui.component.LimberCheckButton
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.feature.block.block.BlockEvent
import com.kkh.multimodule.feature.block.BlockViewModel
import kotlinx.coroutines.delay

@Composable
fun UnBlockReasonScreen(onClickBack: () -> Unit, onNavigateToComplete: () -> Unit) {
    // 🔥 최상단에서 상태 선언
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var isLoading by remember { mutableStateOf(false) }
    val blockViewModel : BlockViewModel = hiltViewModel()

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            onNavigateToComplete()
            isLoading = false
        }
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Gray50,
            topBar = {
                UnBlockReasonTopBar(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp)
                        .statusBarsPadding(),
                    onClickBack = onClickBack
                )
            },
            bottomBar = {
                UnBlockBottomButton(onClick = {
                    isLoading = true
                    blockViewModel.sendEvent(BlockEvent.OnClickUnBlockButton)
                    // TODO: 선택된 이유 처리
                    println("선택된 인덱스: $selectedIndex")
                }, enabled = selectedIndex != -1)
            }
        ) { paddingValues ->
            UnBlockReasonContent(
                modifier = Modifier.padding(paddingValues),
                selectedIndex = selectedIndex,
                onSelectReason = { selectedIndex = it }
            )
        }
        if (isLoading) {
            Dialog({}) {
                LoadingUnBlockScreen()
            }
        }
    }
}

@Composable
fun UnBlockReasonContent(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectReason: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            "잠금을 푸는 이유가 무엇인가요?",
            style = LimberTextStyle.Heading1,
            color = Gray800,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "잠금을 해제하는 순간 실험이 종료돼요",
            style = LimberTextStyle.Body2,
            color = Gray600,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        ReasonItemList(
            selectedIndex = selectedIndex,
            onSelectReason = onSelectReason
        )
    }
}

@Composable
fun ReasonItemList(
    selectedIndex: Int,
    onSelectReason: (Int) -> Unit
) {
    val reasonList =
        listOf(
            "집중 의지가 부족해요",
            "휴식이 필요해요",
            "일정이 빨리 끝났어요",
            "긴급한 상황이 발생했어요",
            "외부의 방해가 있어요"
        )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        itemsIndexed(reasonList) { index, reason ->
            ReasonItem(
                text = reason,
                isSelected = index == selectedIndex,
                onClick = {
                    onSelectReason(index)
                }
            )
        }
    }
}

@Composable
fun ReasonItem(
    modifier: Modifier = Modifier,
    text: String = "",
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) {
        LimberColorStyle.Primary_Main
    } else {
        Gray300
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        LimberCheckButton(isChecked = isSelected, onClick = { onClick() })
        Spacer(Modifier.width(12.dp))
        Text(text = text, style = LimberTextStyle.Body1, color = Gray800)
    }
}

@Composable
fun UnBlockReasonTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onClickBack, modifier = Modifier.size(24.dp)) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "ic_back"
            )
        }
    }
}

@Composable
fun UnBlockBottomButton(onClick: () -> Unit, enabled: Boolean) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
            .padding(bottom = 20.dp)
    ) {
        LimberGradientButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            onClick = onClick,
            text = "잠금 풀기"
        )
    }
}

@Composable
fun LoadingUnBlockScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LimberAnimation(
            modifier = Modifier.size(50.dp),
            resId = R.raw.loading_dark
        )
        Spacer(Modifier.height(32.dp))
        Text(text = "실험 중단 중...", style = LimberTextStyle.Heading1, color = Color.White)
        Spacer(Modifier.height(12.dp))
        Text(
            text = "조금만 기다려 주세요! 집중 실험을 마무리하고 있어요.",
            style = LimberTextStyle.Body2,
            color = LimberColorStyle.Gray400
        )
    }
}