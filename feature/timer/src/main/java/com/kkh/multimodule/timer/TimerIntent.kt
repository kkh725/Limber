package com.kkh.multimodule.timer

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getUsageStats
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime
import java.util.Timer

data class ChipInfo(
    val text: String,
    val isSelected: Boolean = false,
)

data class TimerState(
    val timerScreenState: TimerScreenType,
    val chipList: List<ChipInfo>,
    val isSheetVisible: Boolean = false,
    val isModalVisible: Boolean = false,
    val appDataList: List<AppInfo>,
    val modalAppDataList: List<AppInfo>
) : UiState {
    companion object {
        fun init() = TimerState(
            timerScreenState = TimerScreenType.Now,
            chipList = listOf(
                ChipInfo("학습"),
                ChipInfo("업무"),
                ChipInfo("회의"),
                ChipInfo("작업"),
                ChipInfo("독서"),
                ChipInfo("직접 추가")
            ),
            appDataList = emptyList(),
            modalAppDataList = emptyList()
        )
    }
}

sealed class TimerEvent : UiEvent {
    data class OnClickTimerScreenButton(val timerScreenState: TimerScreenType) : TimerEvent()
    data class OnClickFocusChip(val chipText: String) : TimerEvent()
    data class ShowSheet(val isSheetVisible: Boolean, val context: Context) : TimerEvent()
    data class ShowModal(val isModalVisible: Boolean) : TimerEvent()
    data class OnClickSheetCompleteButton(val appDataList: List<AppInfo>) : TimerEvent()
    data class OnClickModalCompleteButton(val selectedTime : LocalTime) : TimerEvent()
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
                setState(
                    oldState.copy(
                        isModalVisible = false,
                        appDataList = event.appDataList
                    )
                )
            }
        }
    }
}
