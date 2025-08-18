package com.kkh.multimodule.feature.laboratory.recall

import android.content.ContentValues.TAG
import android.util.Log
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
class RecallViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val reducer = RecallReducer(RecallState.init())
    val uiState get() = reducer.uiState
    val uiEffect get() = reducer.uiEffect

    fun sendEvent(e: UiEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                CommonEvent.ScreenEntered -> {
                    getHistoryList()
                }

                is RecallEvent.OnUnRetrospectChanged -> {

                }

                else -> {}
            }
        }
    }

    // 이력 리스트 가져오기.
    private suspend fun getHistoryList() {
        val res = historyRepository.getHistoryListWithRetrospects()

        res.onSuccess {
            
            val newList = it.firstOrNull()?.items ?: emptyList()
            reducer.setState(uiState.value.copy(fullHistoryItemList = newList))
            if (!uiState.value.selectedUnRetrospect) {
                reducer.setState(uiState.value.copy(visibleHistoryItemList = newList))
            } else {
                reducer.setState(uiState.value.copy(visibleHistoryItemList = newList.filter { it -> !it.hasRetrospect }))
            }
        }.onFailure {
            Log.e(TAG, "getHistoryList: ${it.message}")
            reducer.sendEffect(CommonEffect.ShowSnackBar(it.message.toString()))
        }
    }
}
