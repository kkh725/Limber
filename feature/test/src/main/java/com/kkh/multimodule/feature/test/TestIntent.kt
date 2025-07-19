package com.kkh.multimodule.feature.test

import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState

data class TestState(
    val loadingState: String
) : UiState {
    companion object {
        fun init() = TestState(
                loadingState = "init"
        )
    }
}

sealed class TestEvent : UiEvent {
    data object ClickedButton : TestEvent()
}

class TestReducer(state: TestState) : Reducer<TestState, TestEvent>(state) {
    override suspend fun reduce(oldState: TestState, event: TestEvent) {
        when (event) {
            TestEvent.ClickedButton -> {
                setState(oldState.copy(loadingState = "changed"))
            }
        }
    }
}