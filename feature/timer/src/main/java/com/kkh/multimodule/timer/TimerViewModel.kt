package com.kkh.multimodule.timer

import android.app.usage.UsageStats
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getTodayUsageStats
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.data.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer

@HiltViewModel
class TimerViewModel @Inject constructor() :
    ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when (e) {
                // 바텀시트 올라갈 때마다 앱 리스트 상위 10개를 갱신
                is TimerEvent.ShowSheet -> {
                    if (e.isSheetVisible) {
                        setAppDataList(context = e.context)
                    }
                }

                else -> {}
            }
        }
    }

    // app 리스트를 가져와서 순서가 많은대로 탑10 정렬.
    private suspend fun setAppDataList(context: Context) {
        val newList = withContext(Dispatchers.IO) {
            AppInfoProvider.getTodayUsageAppInfoList(context)
        }

        sendEvent(TimerEvent.SetAppDataList(newList))
    }
}