package com.kkh.multimodule.feature.laboratory.recall

import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import com.kkh.multimodule.core.ui.util.getWeekDateString


data class RecallState(
    // 전체, 주간
    val selectedFilter : Int,
    // 전체 아이템 리스트. 페이지 진입할 때만 초기화
    val fullHistoryItemList: List<LatestTimerHistoryModel>,
    // 아이템 리스트
    val visibleHistoryItemList : List<LatestTimerHistoryModel>,
    //미완료만 보기
    val selectedUnRetrospect : Boolean
) : UiState {
    companion object {
        fun init() = RecallState(
            selectedFilter = 0,
            fullHistoryItemList = emptyList(),
            visibleHistoryItemList = emptyList(),
            selectedUnRetrospect = false
        )
    }
}

sealed class RecallEvent : UiEvent {
    data class OnFilterChanged(val filter: Int) : RecallEvent()
    data class OnUnRetrospectChanged(val isSelected: Boolean) : RecallEvent()
}

sealed class SideEffect : UiEffect {

}

class RecallReducer(state: RecallState) : Reducer<RecallState, UiEvent>(state) {

    override suspend fun reduce(oldState: RecallState, event: UiEvent) {
        when (event) {
            CommonEvent.ScreenEntered -> {

            }
            is RecallEvent.OnFilterChanged -> {
                setState(oldState.copy(event.filter))
            }
            is RecallEvent.OnUnRetrospectChanged -> {
                val visibleHistoryItemList = if (event.isSelected) {
                    oldState.fullHistoryItemList.filter { !it.hasRetrospect }
                } else {
                    oldState.fullHistoryItemList
                }
                setState(oldState.copy(
                    visibleHistoryItemList = visibleHistoryItemList,
                    selectedUnRetrospect = event.isSelected
                ))
            }

            else -> {}
        }
    }
}