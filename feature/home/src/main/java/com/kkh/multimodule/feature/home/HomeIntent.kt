package com.kkh.multimodule.feature.home

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import com.kkh.multimodule.core.accessibility.appinfo.AppInfo
import com.kkh.multimodule.core.accessibility.appinfo.AppInfoProvider
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class HomeState(
    val usageAppInfoList: List<AppInfo>,
    val blockingAppPackageList: List<String>,
    val blockReservationItemList: List<ReservationItemModel> = emptyList(),
    val isTimerActive: Boolean = false,
    val leftTime : String,
    val currentTimerId : Int,
    val focusDistributionList : List<FocusDistributionModel>,
    val checkedList : List<Boolean> = List(usageAppInfoList.size) { false }
) : UiState {
    companion object {
        fun init() = HomeState(
            usageAppInfoList = emptyList() ,
            blockingAppPackageList = emptyList(),
            leftTime = "00:00:00",
            currentTimerId = 0,
            focusDistributionList = emptyList()
        )
    }
}

sealed class HomeEvent : UiEvent {
    data class EnterHomeScreen(val context: Context) : HomeEvent()
    data class OnCompleteRegisterButton (val appList : List<AppInfo>): HomeEvent()
    data class SetBlockingAppList(val appList: List<String>) : HomeEvent()
    data class SetIsTimerActive(val isActive: Boolean) : HomeEvent()
    data object EndTimer : HomeEvent()
    data class UpdateCheckedList(val checkedList: List<Boolean>) : HomeEvent()
    data class ToggleCheckedIndex(val index: Int) : HomeEvent()
}

class HomeReducer(state: HomeState) : Reducer<HomeState, HomeEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: HomeState, event: HomeEvent) {
        when (event) {
            is HomeEvent.EnterHomeScreen -> {
                // sheet이 올라오는지 내려가는지 확인
                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getUsageAppInfoList(event.context, period = UsageStatsManager.INTERVAL_DAILY)
                }
                Log.d("TAG", "reduce: ${newList.first()}${newList[1]}${newList[2]}")

                setState(
                    oldState.copy(
                        usageAppInfoList = newList
                    )
                )
            }
            is HomeEvent.SetBlockingAppList -> {
                setState(oldState.copy(blockingAppPackageList = event.appList))
            }
            is HomeEvent.SetIsTimerActive -> {
                setState(oldState.copy(isTimerActive = event.isActive))
            }
            is HomeEvent.EndTimer -> {
                setState(oldState.copy(isTimerActive = false))
            }
            is HomeEvent.UpdateCheckedList -> {
                setState(oldState.copy(checkedList = event.checkedList))
            }

            is HomeEvent.ToggleCheckedIndex -> {
                val updatedChecked = oldState.checkedList.toMutableList().also {
                    it[event.index] = !it[event.index]
                }
                setState(oldState.copy(checkedList = updatedChecked))
            }
            else -> {}
        }
    }
}