package com.kkh.multimodule.feature.block.block

import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState

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
    data class OnClickUnBlockButton(val reasonId : Int) : BlockEvent()
    data object OnClickContinueButton : BlockEvent()
}

class BlockReducer(state: BlockState) : Reducer<BlockState, BlockEvent>(state) {
    override suspend fun reduce(oldState: BlockState, event: BlockEvent) {
        when (event) {
            is BlockEvent.OnClickUnBlockButton -> {
                setState(oldState.copy(loadingState = "changed"))
            }
            BlockEvent.OnClickContinueButton -> {
                setState(oldState.copy(loadingState = "continue"))
            }
        }
    }
}