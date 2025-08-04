package com.kkh.multimodule.feature.timer

import android.content.Context
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime

data class ChipInfo(
    val text: String,
    val isSelected: Boolean = false,
    val imageResId: Int = R.drawable.ic_info
) {
    companion object {
        val mockList = listOf(
            ChipInfo("학습", imageResId = R.drawable.ic_study),
            ChipInfo("업무", imageResId = R.drawable.ic_study),
            ChipInfo("회의", imageResId = R.drawable.ic_study),
            ChipInfo("작업", imageResId = R.drawable.ic_study),
            ChipInfo("독서", imageResId = R.drawable.ic_study),
        )

        val dayList = listOf(
            ChipInfo("월"),
            ChipInfo("화"),
            ChipInfo("수"),
            ChipInfo("목"),
            ChipInfo("금"),
            ChipInfo("토"),
            ChipInfo("일"),
        )

        val repeatOptionList = listOf(
            ChipInfo("매일"),
            ChipInfo("평일"),
            ChipInfo("주말"),
        )

        val init = ChipInfo("Chip 1")
    }
}


data class TimerState(
    val timerScreenState: TimerScreenType,
    val chipList: List<ChipInfo>,
    val isSheetVisible: Boolean = false,
    val isModalVisible: Boolean = false,
    val appDataList: List<AppInfo>,
    val modalAppDataList: List<AppInfo>,
    val startBlockReservationInfo : ReservationInfo
) : UiState {
    companion object {
        fun init() = TimerState(
            timerScreenState = TimerScreenType.Now,
            chipList = ChipInfo.mockList,
            appDataList = emptyList(),
            modalAppDataList = emptyList(),
            startBlockReservationInfo = ReservationInfo.init()
        )
    }
}

sealed class TimerEvent : UiEvent {
    data class OnClickTimerScreenButton(val timerScreenState: TimerScreenType) : TimerEvent()
    data class OnClickFocusChip(val chipText: String) : TimerEvent()
    data class ShowSheet(val isSheetVisible: Boolean, val context: Context) : TimerEvent()
    data class ShowModal(val isModalVisible: Boolean, val context: Context) : TimerEvent()
    data class OnClickSheetCompleteButton(val appDataList: List<AppInfo>) : TimerEvent()
    data class OnClickModalCompleteButton(val startBlockReservationInfo : ReservationInfo) : TimerEvent()
}

class TimerReducer(state: TimerState) : Reducer<TimerState, TimerEvent>(state) {

    override suspend fun reduce(oldState: TimerState, event: TimerEvent) {
        when (event) {
            is TimerEvent.OnClickTimerScreenButton -> {
                setState(oldState.copy(timerScreenState = event.timerScreenState))
            }

            is TimerEvent.OnClickFocusChip -> {
                val newChipList = oldState.chipList.map { chip ->
                    chip.copy(isSelected = chip.text == event.chipText)
                }
                setState(oldState.copy(chipList = newChipList))
            }

            is TimerEvent.ShowSheet -> {
                // sheet이 올라오는지 내려가는지 확인
                val newList = if (event.isSheetVisible) withContext(Dispatchers.IO) {
                    AppInfoProvider.getMonthUsageAppInfoList(event.context)
                } else emptyList()

                setState(
                    oldState.copy(
                        isSheetVisible = event.isSheetVisible,
                        appDataList = newList
                    )
                )
            }

            is TimerEvent.ShowModal -> {
                if (!event.isModalVisible) {
                    setState(
                        oldState.copy(
                            isModalVisible = false,
                            appDataList = emptyList(),
                            modalAppDataList = emptyList()
                        )
                    )
                } else {
                    setState(
                        oldState.copy(
                            isModalVisible = true
                        )
                    )
                }
            }

            // 바텀 시트 complete 버튼
            is TimerEvent.OnClickSheetCompleteButton -> {
                setState(
                    oldState.copy(
                        isSheetVisible = false,
                        modalAppDataList = event.appDataList
                    )
                )
            }

            // 모달 최종 시작하기 버튼
            is TimerEvent.OnClickModalCompleteButton -> {
                val startBlockReservationInfo = event.startBlockReservationInfo
                setState(
                    oldState.copy(
                        startBlockReservationInfo = startBlockReservationInfo,
                        isModalVisible = false
                    )
                )
            }
        }
    }
}
