package com.kkh.multimodule.core.ui.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow


abstract class Reducer<S : UiState, E : UiEvent>(initialState: S) {
    private val _uiState = MutableStateFlow(initialState)
    val uiState get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<UiEffect>(capacity = Channel.BUFFERED)
    val uiEffect get() = _uiEffect.receiveAsFlow()

    suspend fun sendEvent(event: E) {
        reduce(_uiState.value, event)
    }

    suspend fun sendEffect(effect: UiEffect) {
        _uiEffect.send(effect)
    }

    fun setState(newState: S) {
        _uiState.value = newState
    }

    abstract suspend fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent

sealed class CommonEvent() : UiEvent {
    data object ScreenEntered : CommonEvent()
}

interface UiEffect

sealed class CommonEffect() : UiEffect {
    data class ShowToast(val message: String) : CommonEffect()
    data class ShowSnackBar(val message: String) : CommonEffect()
    data class ShowDialog(val message: String, val visible : Boolean) : CommonEffect()
    data class IsLoading(val isVisible: Boolean) : CommonEffect()
    data object NavigateToHome : CommonEffect()
    data object NavigateToRecall : CommonEffect()
}