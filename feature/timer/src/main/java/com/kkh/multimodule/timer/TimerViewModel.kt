package com.kkh.multimodule.timer

import android.app.usage.UsageStats
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.multimodule.data.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.util.Timer

@HiltViewModel
class TimerViewModel @Inject constructor(private val appDataRepository: AppDataRepository) :
    ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when (e) {
                // 바텀시트 올라갈 때마다 앱 리스트 상위 10개를 갱신
                is TimerEvent.ShowSheet -> {
                    if (e.isSheetVisible){
                        setAppDataList(context = e.context)
                    }
                }
                else -> {}
            }
        }
    }

    // app 리스트를 가져와서 순서가 많은대로 탑10 정렬.
    private fun setAppDataList(context: Context) {
        val usageStatsList = appDataRepository.getTodayUsageStats(context)

        val top10AppList = usageStatsList
            .sortedByDescending { it.totalTimeInForeground }
            .take(10)
            .map {
                AppInfo(
                    packageName = it.packageName,
                    appName = AppInfoProvider.getAppLabel(context, it.packageName).toString(),
                    appIcon = null,
                    usageTime = it.totalTimeInForeground.toString()
                )
            }
        sendEvent(TimerEvent.SetAppDataList(top10AppList))
    }
}