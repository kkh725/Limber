package com.kkh.multimodule.home

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getTodayUsageStats
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class HomeState(
    val usageAppInfoList: List<AppInfo>
) : UiState {
    companion object {
        fun init() = HomeState(
            usageAppInfoList = emptyList()
        )
    }
}

sealed class HomeEvent : UiEvent {
    data class EnterHomeScreen(val context: Context) : HomeEvent()
}

class HomeReducer(state: HomeState) : Reducer<HomeState, HomeEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: HomeState, event: HomeEvent) {
        when (event) {
            is HomeEvent.EnterHomeScreen -> {
                val context = event.context.applicationContext

                // IO 스레드에서 무거운 작업 실행
                val newList = withContext(Dispatchers.IO) {
                    val usageStatsAppList = getTodayUsageStats(context)

                    usageStatsAppList.map {
                        AppInfo(
                            AppInfoProvider.getAppLabel(context, it.packageName) ?: "",
                            it.packageName,
                            AppInfoProvider.getAppIcon(context, it.packageName)
                                ?: context.getDrawable(R.drawable.logo_limber)!!,
                            AppUsageStatsManager.formatUsageTime(it.totalTimeInForeground)
                        )
                    }
                }

                // UI 스레드에서 상태 업데이트
                setState(oldState.copy(usageAppInfoList = newList))
            }
        }
    }
}