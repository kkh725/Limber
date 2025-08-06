package com.kkh.multimodule.feature.home

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class HomeState(
    val usageAppInfoList: List<AppInfo>,
    val blockingAppPackageList: List<String>,
    val blockReservationItemList: List<ReservationItemModel> = emptyList(),
    val isTimerActive: Boolean = false
) : UiState {
    companion object {
        fun init() = HomeState(
            usageAppInfoList = emptyList() ,
            blockingAppPackageList = emptyList()
        )
    }
}

sealed class HomeEvent : UiEvent {
    data class EnterHomeScreen(val context: Context) : HomeEvent()
    data class OnCompleteRegisterButton (val appList : List<AppInfo>): HomeEvent()
    data class SetBlockingAppList(val appList: List<String>) : HomeEvent()
}

class HomeReducer(state: HomeState) : Reducer<HomeState, HomeEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: HomeState, event: HomeEvent) {
        when (event) {
            is HomeEvent.EnterHomeScreen -> {
                val context = event.context.applicationContext

                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getUsageAppInfoList(context, UsageStatsManager.INTERVAL_DAILY)
                }

                // UI 스레드에서 상태 업데이트
                setState(oldState.copy(usageAppInfoList = newList))
            }
            is HomeEvent.SetBlockingAppList -> {
                setState(oldState.copy(blockingAppPackageList = event.appList))
            }
            else -> {}
        }
    }
}