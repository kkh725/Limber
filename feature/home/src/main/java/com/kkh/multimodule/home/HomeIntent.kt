package com.kkh.multimodule.home

import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState

data class HomeState(
    val loadingState: String
) : UiState {
    companion object {
        fun init() = HomeState(
            loadingState = "init"
        )
    }
}

sealed class HomeEvent : UiEvent {
    data object ClickedButton : HomeEvent()
}

class HomeReducer(state: HomeState) : Reducer<HomeState, HomeEvent>(state) {
    override suspend fun reduce(oldState: HomeState, event: HomeEvent) {
        when (event) {
            HomeEvent.ClickedButton -> {
                setState(oldState.copy(loadingState = "changed"))
            }
        }
    }
}