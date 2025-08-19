package com.kkh.multimodule.feature.laboratory.recall

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.domain.model.mockRecallItems
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.designsystem.snackbar.showImmediately
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.component.LimberCheckButton
import com.kkh.multimodule.core.ui.ui.component.LimberFilterChip
import com.kkh.multimodule.core.ui.ui.component.LimberRoundButton
import com.kkh.multimodule.core.ui.ui.component.LimberSnackBar
import com.kkh.multimodule.core.ui.ui.component.LimberText
import kotlinx.coroutines.launch

@Composable
fun RecallHistoryPagerContent(onNavigateToRecall: (LatestTimerHistoryModel) -> Unit) {
    // 0: 전체, 1: 주간
    val recallViewModel: RecallViewModel = hiltViewModel()
    val uiState by recallViewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val selectedFilter = uiState.selectedFilter
    val radioSelected = uiState.selectedUnRetrospect
    val recallItems = uiState.visibleHistoryItemList

    LaunchedEffect(Unit) {
        recallViewModel.sendEvent(CommonEvent.ScreenEntered)

        recallViewModel.uiEffect.collect { effect ->
            when (effect) {
                is CommonEffect.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showImmediately(effect.message)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(LimberColorStyle.Gray50)
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        RecallTopBar(
            selectedFilter = selectedFilter,
            onFilterClick = {
                recallViewModel.sendEvent(RecallEvent.OnFilterChanged(it))
            },
            radioSelected = radioSelected,
            onRadioClick = {
                if (radioSelected) {
                    recallViewModel.sendEvent(RecallEvent.OnUnRetrospectChanged(false))
                } else {
                    recallViewModel.sendEvent(RecallEvent.OnUnRetrospectChanged(true))
                }
            })

        Spacer(Modifier.height(23.dp))
        RecallContent(
            selectedFilter = selectedFilter,
            recallItems = recallItems,
            snackbarHostState = snackbarHostState,
            onRecallClick = {
                onNavigateToRecall(it)
            })
    }
}

@Composable
fun RecallTopBar(
    selectedFilter: Int, // 0: 전체, 1: 주간
    onFilterClick: (Int) -> Unit,
    radioSelected: Boolean,
    onRadioClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LimberFilterChip(
                text = "전체",
                checked = selectedFilter == 0,
                onCheckedChange = { onFilterClick(0) }
            )
            Spacer(Modifier.width(8.dp))
            LimberFilterChip(
                text = "주간",
                checked = selectedFilter == 1,
                onCheckedChange = { onFilterClick(1) }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                modifier = Modifier.size(24.dp),
//                selected = radioSelected,
//                onClick = onRadioClick
//            )
            LimberCheckButton(isChecked = radioSelected, onClick = {
                onRadioClick()
            })
            Spacer(Modifier.width(8.dp))
            LimberText(
                "미회고만 보기",
                style = LimberTextStyle.Body2,
                color = Gray600
            )
        }
    }
}

@Composable
fun RecallContent(
    selectedFilter: Int,
    recallItems: List<LatestTimerHistoryModel>,
    snackbarHostState: SnackbarHostState,
    onRecallClick: (LatestTimerHistoryModel) -> Unit
) {
    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedVisibility (selectedFilter == 1) {
                LimberText(
                    text = "2025년 5월 24일~31일",
                    style = LimberTextStyle.Heading5,
                    color = Gray800,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            RecallItemList(
                selectedFilter = selectedFilter,
                recallItems = recallItems,
                onRecallClick = onRecallClick
            )
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RecallItemList(
    selectedFilter: Int,
    recallItems: List<LatestTimerHistoryModel>,
    onRecallClick: (LatestTimerHistoryModel) -> Unit,
    modifier: Modifier = Modifier
) {
    // 리스트 교체 시 AnimatedContent의 targetState로 사용.
    // (List가 새로운 인스턴스로 오면 전환이 발생)
    AnimatedContent(
        targetState = recallItems,
        transitionSpec = {
            // 원하는 전환을 여기서 조정 가능
            (fadeIn(tween(220)) + slideInVertically(tween(300)) { height -> height / 8 }) with
                    (fadeOut(tween(180)) + slideOutVertically(tween(220)) { height -> -height / 8 })
        },
        label = "RecallListSwitch",
        modifier = modifier.fillMaxSize()
    ) { itemsState ->
        // itemsState는 AnimatedContent가 전달하는 현재 리스트(이전/다음)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(
                items = itemsState,
                key = { it.id } // 고유 키 필수
            ) { item ->
                // 카드 내부 크기 변화(확장 등)를 부드럽게
                RecallCard(
                    item = item,
                    onRecallClick = onRecallClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                )
            }
        }
    }
}

@Composable
fun RecallCard(
    item: LatestTimerHistoryModel,
    onRecallClick: (LatestTimerHistoryModel) -> Unit,
    modifier: Modifier = Modifier     // << modifier 파라미터 추가!!
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                spotColor = Color(0x14000000),
                ambientColor = Color(0x14000000)
            )
            .fillMaxWidth()
            .height(116.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                LimberText(
                    item.retrospectSummary,
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray500
                )
            }

            Spacer(Modifier.height(4.dp))
            Row {
                LimberText(
                    item.focusTypeTitle,
                    style = LimberTextStyle.Heading5,
                    color = LimberColorStyle.Primary_Main
                )
                LimberText(
                    "에 집중한 시간",
                    style = LimberTextStyle.Heading5,
                    color = Gray800
                )
            }

            if (item.title.isNotBlank()) {
                Spacer(Modifier.height(10.dp))
                LimberText(
                    item.title,
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray600
                )
            }
            Spacer(Modifier.height(8.dp))
        }
        // 회고를 한 이력이라면
        if (item.hasRetrospect) {
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_study),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp) // or 32.dp, 40.dp 등
                )
            }
        } else {
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                LimberRoundButton(
                    modifier = Modifier,
                    text = "회고하기",
                    enabled = true,
                    onClick = { onRecallClick(item) }
                )
            }
        }
    }
}
