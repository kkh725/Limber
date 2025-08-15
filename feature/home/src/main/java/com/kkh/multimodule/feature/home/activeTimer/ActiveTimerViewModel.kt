package com.kkh.multimodule.feature.home.activeTimer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.ui.util.getStartAndEndTime
import com.kkh.multimodule.core.ui.util.getTimeDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ActiveTimerViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository
) :
    ViewModel() {

    private val reducer = HomeReducer(ActiveTimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: ActiveTimerEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is ActiveTimerEvent.OnEnterScreen->{
                    getTotalTime()
                }
                is ActiveTimerEvent.SheetExpanded -> {
                    if (e.isExpanded) {
                        setBlockedAppInfoList(e.context)
                    }
                }

                else -> {}
            }
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
        sendEvent(ActiveTimerEvent.SetBlockedAppList(newAppInfoList))
    }

    private suspend fun getTotalTime(){
        timerRepository.getSingleTimer(timerRepository.getActiveTimerId())
            .onSuccess {
                val diff = getTimeDifference(startTimeStr = it.startTime, endTimeStr = it.endTime)
                reducer.setState(uiState.value.copy(totalTime = diff))
                reducer.setState(uiState.value.copy(totalTime = diff, isVisible = true))
            }.onFailure {
                // Handle failure here
            }
    }
}