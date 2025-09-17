package com.kkh.multimodule.feature.home.activeTimer

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.multimodule.core.accessibility.appinfo.AppInfo
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState

data class ActiveTimerState(
    val blockedAppList: List<AppInfo>,
    val sheetState: Boolean,
    val timerPercent: Float,
    val focusType: String,
    val totalTime : String,
    val isVisible : Boolean = false
) : UiState {
    companion object {
        fun init() = ActiveTimerState(
            blockedAppList = listOf(),
            sheetState = false,
            timerPercent = 0f,
            focusType = "학습",
            totalTime = "00:00"
        )
    }
}

sealed class ActiveTimerEvent : UiEvent {
    data object OnEnterScreen : ActiveTimerEvent()
    data class SheetExpanded(val isExpanded: Boolean, val context: Context) : ActiveTimerEvent()
    data class SetBlockedAppList(val blockedAppList: List<AppInfo>) : ActiveTimerEvent()
    data class SetTimerPercent(val percent: Float) : ActiveTimerEvent()
    data class SetFocusType(val focusType: String) : ActiveTimerEvent()
}

class HomeReducer(state: ActiveTimerState) : Reducer<ActiveTimerState, ActiveTimerEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: ActiveTimerState, event: ActiveTimerEvent) {
        when (event) {
            is ActiveTimerEvent.SetBlockedAppList -> {
                setState(
                    oldState.copy(
                        blockedAppList = event.blockedAppList
                    )
                )
            }

            is ActiveTimerEvent.SheetExpanded -> {
                setState(
                    oldState.copy(
                        sheetState = event.isExpanded
                    )
                )
            }

            is ActiveTimerEvent.SetTimerPercent -> {
                setState(
                    oldState.copy(
                        timerPercent = event.percent
                    )
                )
            }

            is ActiveTimerEvent.SetFocusType -> {
                setState(
                    oldState.copy(
                        focusType = event.focusType
                    )
                )
            }
            else -> {}
        }
    }
}