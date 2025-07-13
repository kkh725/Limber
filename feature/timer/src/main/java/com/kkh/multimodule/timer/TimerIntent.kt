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

data class ChipInfo(
    val text: String,
    val isSelected: Boolean = false,
)

data class TimerState(
    val timerScreenState: TimerScreenType,
    val chipList: List<ChipInfo>,
    val isSheetVisible: Boolean = false,
    val isModalVisible: Boolean = false,
    val appDataList : List<AppInfo>,
    val modalAppDataList : List<AppInfo>
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
    data class SetAppDataList(val list: List<AppInfo>) : TimerEvent()
    data class SetModalAppDataList(val list: List<AppInfo>) : TimerEvent()
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
            is TimerEvent.SetAppDataList -> {
                val newList = event.list
                setState(oldState.copy(appDataList = newList))
            }
            is TimerEvent.SetModalAppDataList -> {
                val newList = event.list
                setState(oldState.copy(modalAppDataList = newList))
            }
        }
    }
}
