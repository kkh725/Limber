package com.kkh.multimodule.timer

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getTodayUsageStats
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class ChipInfo(
    val text: String,
    val isSelected: Boolean = false,
)

data class TimerState(
    val timerScreenState: TimerScreenType,
    val chipList: List<ChipInfo>,
    val isSheetVisible: Boolean = false,
    val isModalVisible: Boolean = false,
) : UiState {
    companion object {
        fun init() = TimerState(
            timerScreenState = TimerScreenType.Now,
            chipList = listOf(
                ChipInfo("하나"),
                ChipInfo("둘"),
                ChipInfo("셋"),
                ChipInfo("넷"),
                ChipInfo("직접 추가")
            )
        )
    }
}

sealed class TimerEvent : UiEvent {
    data class OnClickTimerScreenButton(val timerScreenState: TimerScreenType) : TimerEvent()
    data class OnClickFocusChip(val chipText: String) : TimerEvent()
    data class ShowSheet(val isSheetVisible: Boolean) : TimerEvent()
    data class ShowModal(val isModalVisible: Boolean) : TimerEvent()
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
                setState(oldState.copy(isSheetVisible = event.isSheetVisible))
            }
            is TimerEvent.ShowModal -> {
                setState(oldState.copy(isModalVisible = event.isModalVisible))
            }
        }
    }
}
