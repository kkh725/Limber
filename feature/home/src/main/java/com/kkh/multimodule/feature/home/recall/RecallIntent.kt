package com.kkh.multimodule.feature.home.recall

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState

data class RecallState(
    val appInfo: AppInfo? = null,
    val sheetState: Boolean = false,
    val modalState: Boolean = false,
    val focusText: String = "",
    val category: String = "기타",
    val isRecallCompleted: Boolean = false
) : UiState {
    companion object {
        fun init() = RecallState(

        )
    }
}

sealed class RecallEvent : UiEvent {
    data class SetSheetState(val state: Boolean) : RecallEvent()
    data class SetModalState(val state: Boolean) : RecallEvent()
    data class OnValueChange(val text: String) : RecallEvent()
    data object OnClickSheetComplete : RecallEvent()
    data class OnCompleteRecall(
        val timerId: Int,
        val timerHistoryId: Int,
        val immersion: Int,
        val comment: String
    ) : RecallEvent()
}

sealed class RecallSideEffect : UiEffect {
    data object OnNavigateToHome : RecallSideEffect()
    data object OnNavigateToReport : RecallSideEffect()
}

class RecallReducer(state: RecallState) : Reducer<RecallState, UiEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: RecallState, event: UiEvent) {
        when (event) {
            is RecallEvent.OnValueChange -> {
                setState(oldState.copy(focusText = event.text))
            }

            is RecallEvent.SetSheetState -> {
                setState(oldState.copy(sheetState = event.state))
            }

            is RecallEvent.SetModalState -> {
                setState(oldState.copy(modalState = event.state))
            }

            is RecallEvent.OnClickSheetComplete -> {
                setState(oldState.copy(sheetState = false))
            }

            else -> {}
        }
    }

}