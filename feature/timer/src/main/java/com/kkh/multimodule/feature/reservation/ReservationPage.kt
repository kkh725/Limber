package com.kkh.multimodule.feature.reservation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.designsystem.gradientModifier
import com.kkh.multimodule.feature.reservation.bottomsheet.ReservationBottomSheet
import com.kkh.multimodule.feature.timer.ReservationScreenState
import com.kkh.multimodule.feature.timer.ReservationTime
import com.kkh.multimodule.core.ui.ui.component.LimberCheckButton
import com.kkh.multimodule.core.ui.ui.component.LimberFilterChip
import com.kkh.multimodule.core.ui.ui.component.LimberRoundButton
import com.kkh.multimodule.core.ui.ui.component.LimberToggle

data class ReservationInfo(
    val id: Int,
    val reservationTime : ReservationTime,
    var isToggleChecked: Boolean,
    var isRemoveChecked: Boolean = false,
)



@Preview(showBackground = true)
@Composable
fun ReservationListScreenPreview() {
    ReservationPage()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationPage(modifier: Modifier = Modifier) {
    val reservationViewModel: ReservationViewModel = hiltViewModel()
    val uiState by reservationViewModel.uiState.collectAsState()

    val bottomSheetVisible = uiState.isSheetVisible
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isClickedAllSelect = uiState.isClickedAllSelected

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ReservationFloatingBtn(onClick = {
                reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Show(true))
            })
        },
        topBar = {
            AnimatedContent(
                targetState = uiState.reservationScreenState,
                transitionSpec = {
                    fadeIn(tween(50, easing = FastOutSlowInEasing)) togetherWith
                            fadeOut(tween(50, easing = FastOutSlowInEasing))
                }
            ) { state ->
                when (state) {
                    ReservationScreenState.Idle -> {
                        ReservationTopBar(
                            onClickModify = {
                                reservationViewModel.sendEvent(ReservationEvent.OnClickModifyButton)
                            }
                        )
                    }

                    else -> {
                        ReservationModifyTopBar(
                            isAllCheckButtonChecked = isClickedAllSelect,
                            onClickSelectAll = {
                                reservationViewModel.sendEvent(ReservationEvent.OnClickAllSelected)
                            },
                            onClickRemove = {},
                            onClickComplete = {
                                reservationViewModel.sendEvent(ReservationEvent.OnClickModifyCompleteButton)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ReservationList(
                modifier = Modifier.padding(horizontal = 20.dp),
                reservationList = uiState.reservationItemList,
                reservationScreenState = uiState.reservationScreenState,
                onToggleChanged = { id, checked ->
                    reservationViewModel.sendEvent(ReservationEvent.OnToggleChanged(id, checked))
                },
                onCheckButtonClicked = { id, checked ->
                    reservationViewModel.sendEvent(
                        ReservationEvent.OnRemoveCheckChanged(
                            id,
                            checked
                        )
                    )
                }
            )
        }
    }

    if (bottomSheetVisible) {
        ReservationBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = {
                reservationViewModel.sendEvent(ReservationEvent.BottomSheet.Close)
            })
    }

}

@Composable
fun ReservationTopBar(
    modifier: Modifier = Modifier,
    onClickModify: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "진행중인 실험",
                style = LimberTextStyle.Heading4,
                color = Gray800
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "3",
                style = LimberTextStyle.Heading4,
                color = Primary_Main
            )
        }
        //todo 해제
//        ModifyButton(onClick = onClickModify)
    }
}

@Composable
fun ReservationModifyTopBar(
    modifier: Modifier = Modifier,
    isAllCheckButtonChecked: Boolean = false,
    onClickSelectAll: (Boolean) -> Unit = {},
    onClickRemove: () -> Unit = {},
    onClickComplete: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LimberCheckButton(
                isChecked = isAllCheckButtonChecked,
                onClick = onClickSelectAll
            )
            Text(
                text = "전체 선택",
                style = LimberTextStyle.Heading4,
                color = Gray800
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LimberRoundButton(
                onClick = onClickRemove,
                text = "삭제",
                textColor = Color.White,
                enabled = true,
                containerColor = LimberColorStyle.Gray700
            )
            LimberRoundButton(
                onClick = onClickComplete,
                text = "완료",
                textColor = Gray500,
                enabled = true,
                containerColor = Gray200
            )
        }
    }
}

@Composable
fun ModifyButton(onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .background(Gray300)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_write),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "편집하기",
            style = LimberTextStyle.Body2,
            color = Gray600
        )
    }
}

@Composable
fun ReservationList(
    modifier: Modifier = Modifier,
    reservationList: List<ReservationInfo>,
    reservationScreenState: ReservationScreenState,
    onToggleChanged: (id: Int, checked: Boolean) -> Unit,
    onCheckButtonClicked: (id: Int, checked: Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(reservationList) { item ->
            ReservationItemComposable(
                info = item,
                reservationScreenState = reservationScreenState,
                onToggleChanged = { checked ->
                    onToggleChanged(item.id, checked)
                },
                onCheckButtonClicked = { checked ->
                    onCheckButtonClicked(item.id, checked)
                }
            )
        }
    }
}

@Composable
fun ReservationItemComposable(
    info: ReservationInfo,
    reservationScreenState: ReservationScreenState,
    onToggleChanged: (Boolean) -> Unit,
    onCheckButtonClicked: (Boolean) -> Unit = {}
) {
    val backgroundColor = if (info.isToggleChecked) Color.White else Gray100
    val chipTextColor = if (info.isToggleChecked) Primary_Main else Gray500
    val chipBackgroundColor = if (info.isToggleChecked) {
        LimberColorStyle.Primary_Background_Dark
    } else {
        Gray200
    }
    val descriptionColor = if (info.isToggleChecked) Gray800 else Gray400

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        when (reservationScreenState) {
            ReservationScreenState.Idle -> {
                IdleRow(
                    info = info,
                    chipTextColor = chipTextColor,
                    chipBackgroundColor = chipBackgroundColor,
                    onToggleChanged = onToggleChanged
                )
            }

            else -> {
                ModifiedRow(
                    info = info,
                    chipTextColor = chipTextColor,
                    chipBackgroundColor = chipBackgroundColor,
                    onClickCheckButton = onCheckButtonClicked
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        Text(
            text = info.reservationTime.title,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = info.reservationTime.category,
            style = LimberTextStyle.Body2,
            color = descriptionColor
        )
    }
}

@Composable
fun IdleRow(
    info: ReservationInfo,
    chipTextColor: Color,
    chipBackgroundColor: Color,
    onToggleChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LimberFilterChip(
            text = info.reservationTime.category,
            textColor = chipTextColor,
            backgroundColor = chipBackgroundColor,
            onclick = {}
        )
        LimberToggle(
            checked = info.isToggleChecked,
            onCheckedChange = onToggleChanged,
            modifier = Modifier.size(40.dp, 22.dp)
        )
    }
}

@Composable
fun ModifiedRow(
    info: ReservationInfo,
    chipTextColor: Color,
    chipBackgroundColor: Color,
    onClickCheckButton: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LimberCheckButton(
            isChecked = info.isRemoveChecked,
            onClick = onClickCheckButton
        )
        LimberFilterChip(
            text = info.reservationTime.category,
            textColor = chipTextColor,
            backgroundColor = chipBackgroundColor,
            enabled = false
        )
    }
}

@Composable
fun ReservationFloatingBtn(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp, bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .then(gradientModifier())
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
