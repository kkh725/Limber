package com.kkh.multimodule.feature.test

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Reducer<S : UiState, E : UiEvent>(initialState: S) {
    private val _uiState = MutableStateFlow(initialState)
    val uiState get() = _uiState.asStateFlow()

    suspend fun sendEvent(event: E) {
        reduce(_uiState.value, event)
    }

    fun setState(newState: S) {
        _uiState.value = newState
    }

    abstract suspend fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent

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