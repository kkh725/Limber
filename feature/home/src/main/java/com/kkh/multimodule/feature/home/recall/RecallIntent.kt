package com.kkh.multimodule.feature.home.recall

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState

data class RecallState(
    val appInfo: AppInfo? = null,
    val sheetState : Boolean = false,
    val modalState : Boolean = false,
    val focusText : String = "123"
) : UiState {
    companion object {
        fun init() = RecallState(

        )
    }
}

sealed class RecallEvent : UiEvent {
    data class SetSheetState(val state : Boolean) : RecallEvent()
    data class SetModalState(val state : Boolean) : RecallEvent()
    data class OnValueChange(val text : String) : RecallEvent()
    data object OnClickSheetComplete : RecallEvent()
}

class RecallReducer(state: RecallState) : Reducer<RecallState, RecallEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: RecallState, event: RecallEvent) {
        when(event){
            is RecallEvent.OnValueChange -> {
                setState(oldState.copy(focusText = event.text))
            }
            is RecallEvent.SetSheetState ->{
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