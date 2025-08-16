package com.kkh.multimodule.feature.laboratory.recall

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import com.kkh.multimodule.core.ui.util.getWeekDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class ReportState(
    // 총 실험 시간
    val totalTime: String,
    // 총 몰입도
    val totalImmersion: String,
    // 시작 시간~
    val startDate: String,
    // 종료시간
    val endDate: String
) : UiState {
    companion object {
        fun init() = ReportState(
            totalTime = "00시간 00분",
            totalImmersion = "49%",
            startDate = "2023-01-01",
            endDate = "2023-01-01"
        )
    }
}

sealed class ReportEvent : UiEvent {
}

sealed class SideEffect : UiEffect {

}

class ReportReducer(state: ReportState) : Reducer<ReportState, UiEvent>(state) {

    override suspend fun reduce(oldState: ReportState, event: UiEvent) {
        when (event) {
            CommonEvent.ScreenEntered -> {
                val weekDate = getWeekDateString()
                setState(oldState.copy(startDate = weekDate.second, endDate = weekDate.first))
            }

            else -> {}
        }
    }
}