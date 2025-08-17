package com.kkh.multimodule.feature.laboratory.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.util.getWeekDateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val reducer = ReportReducer(ReportState.init())
    val uiState get() = reducer.uiState
    val uiEffect get() = reducer.uiEffect

    fun sendEvent(e: UiEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                CommonEvent.ScreenEntered -> {
                    getTotalImmersion(getWeekDateRange().first, getWeekDateRange().second)
                    getTotalHistoryTime(getWeekDateRange().first, getWeekDateRange().second)
                }

                else -> {}
            }
        }
    }

    // 총 집중시간
    private suspend fun getTotalHistoryTime(startTime: String, endTime: String) {
        val res = historyRepository.getTotalActual("UUID1", startTime, endTime)

        res.onSuccess {
            reducer.setState(uiState.value.copy(totalTime = it.label))
        }.onFailure {
            reducer.sendEffect(CommonEffect.ShowSnackBar(it.message.toString()))
        }
    }

    // 총 몰입도
    private suspend fun getTotalImmersion(startTime: String, endTime: String) {
        val res = historyRepository.getTotalImmersion("UUID1", startTime, endTime)

        res.onSuccess {
            reducer.setState(
                uiState.value.copy(
                    totalImmersion = "${(it.ratio * 100).roundToInt()}%"
                )
            )
        }
    }
}