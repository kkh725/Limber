package com.kkh.multimodule.feature.home.recall

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.model.RetrospectsRequestModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.UiEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.concurrent.timer

@HiltViewModel
class RecallViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository,
    private val historyRepository: HistoryRepository
) :
    ViewModel() {

    private val reducer = RecallReducer(RecallState.init())
    val uiState get() = reducer.uiState
    val uiEffect get() = reducer.uiEffect

    fun sendEvent(e: UiEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is CommonEvent.ScreenEntered -> {
                    getTimerInfo()
                }

                is RecallEvent.OnCompleteRecall -> {
                    writeRetrospects(
                        timerId = e.timerId,
                        timerHistoryId = e.timerHistoryId,
                        immersion =e.immersion,
                        comment = e.comment
                    )
                }

                else -> {}
            }
        }
    }

    fun sendEffect(e: UiEffect) {
        viewModelScope.launch {
            reducer.sendEffect(e)
        }
    }

    private suspend fun setBlockedAppInfoList(context: Context) {
        val blockedAppPackageList = appDataRepository.getBlockedPackageList()
        val newAppInfoList: List<AppInfo> = blockedAppPackageList.map {
            AppInfo(
                appName = AppInfoProvider.getAppLabel(context, it) ?: "Unknown",
                packageName = it,
                appIcon = AppInfoProvider.getAppIcon(context, it)
            )
        }
    }

    private suspend fun getTimerInfo() {
        val currentTimerId = timerRepository.getActiveTimerId()
        val res = timerRepository.getSingleTimer(currentTimerId)

        res.onSuccess {
            val category = it.focusTypeIdToCategory(it.focusTypeId)
            reducer.setState(uiState.value.copy(category = category))
        }.onFailure { throwable ->
            reducer.sendEffect(CommonEffect.ShowSnackBar(throwable.message.toString()))
        }
    }

    private suspend fun writeRetrospects(
        timerId: Int,
        timerHistoryId: Int,
        immersion: Int,
        comment: String
    ) {
        val currentTimerId = timerId.takeIf { it != -1 } ?: timerRepository.getActiveTimerId()
        val res = historyRepository.getLatestHistoryId(currentTimerId)
        reducer.setState(uiState.value.copy(isRecallCompleted = true))

        res.onSuccess {
            val timerHistoryId = timerHistoryId.takeIf { it-> it != -1 } ?: it.id
            val request =
                RetrospectsRequestModel(
                    com.kkh.multimodule.core.domain.UUID,
                    timerHistoryId = timerHistoryId,
                    timerId = currentTimerId,
                    immersion = immersion,
                    comment = comment
                )
            timerRepository.writeRetrospects(request)
                .onSuccess {
                    reducer.setState(uiState.value.copy(isRecallCompleted = true))
                }.onFailure { throwable ->
                    reducer.sendEffect(CommonEffect.ShowSnackBar(throwable.message.toString()))
                }
        }.onFailure { throwable ->
            reducer.sendEffect(CommonEffect.ShowSnackBar(throwable.message.toString()))
        }
    }
}