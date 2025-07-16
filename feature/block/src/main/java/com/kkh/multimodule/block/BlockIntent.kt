package com.kkh.multimodule.block

import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState

data class BlockState(
    val loadingState: String
) : UiState {
    companion object {
        fun init() = BlockState(
            loadingState = "init"
        )
    }
}

sealed class BlockEvent : UiEvent {
    data object OnClickUnBlockButton : BlockEvent()
    data object OnClickContinueButton : BlockEvent()
}

class BlockReducer(state: BlockState) : Reducer<BlockState, BlockEvent>(state) {
    override suspend fun reduce(oldState: BlockState, event: BlockEvent) {
        when (event) {
            BlockEvent.OnClickUnBlockButton -> {
                setState(oldState.copy(loadingState = "changed"))
            }
            BlockEvent.OnClickContinueButton -> {
                setState(oldState.copy(loadingState = "continue"))
            }
        }
    }
}