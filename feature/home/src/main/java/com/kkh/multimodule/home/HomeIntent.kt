package com.kkh.multimodule.home

import android.annotation.SuppressLint
import android.content.Context
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getUsageStats
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

                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getMonthUsageAppInfoList(context)
                }

                // UI 스레드에서 상태 업데이트
                setState(oldState.copy(usageAppInfoList = newList))
            }
        }
    }
}