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

data class TimerState(
    val timerScreenState: TimerScreenType,
    val selectedFocusChipIndex : Int
) : UiState {
    companion object {
        fun init() = TimerState(
            timerScreenState = TimerScreenType.Now,
            selectedFocusChipIndex = 0
        )
    }
}

sealed class TimerEvent : UiEvent {
    data class OnClickTimerScreenButton(val timerScreenState: TimerScreenType) : TimerEvent()
    data class OnClickFocusChip(val selectedFocusChipIndex: Int) : TimerEvent()
}

class TimerReducer(state: TimerState) : Reducer<TimerState, TimerEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: TimerState, event: TimerEvent) {
        when (event) {
            is TimerEvent.OnClickTimerScreenButton -> {
                setState(
                    oldState.copy(timerScreenState = event.timerScreenState)
                )
            }
            is TimerEvent.OnClickFocusChip -> {
                setState(
                    oldState.copy(selectedFocusChipIndex = event.selectedFocusChipIndex)
                )
            }
        }
    }
}