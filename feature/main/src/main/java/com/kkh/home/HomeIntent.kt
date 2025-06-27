package com.kkh.home

import com.kkh.multimodule.Reducer
import com.kkh.multimodule.UiEvent
import com.kkh.multimodule.UiState

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