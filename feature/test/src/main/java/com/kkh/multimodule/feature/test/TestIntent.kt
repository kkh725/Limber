package com.kkh.multimodule.feature.test

import com.kkh.multimodule.Reducer
import com.kkh.multimodule.UiEvent
import com.kkh.multimodule.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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