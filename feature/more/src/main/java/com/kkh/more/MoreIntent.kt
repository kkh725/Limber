package com.kkh.more

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import com.kkh.multimodule.core.accessibility.appinfo.AppInfo
import com.kkh.multimodule.core.accessibility.appinfo.AppInfoProvider
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import com.kkh.multimodule.core.ui.util.getWeekDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class MoreState(
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
        fun init() = MoreState(
            usageAppInfoList = emptyList() ,
            blockingAppPackageList = emptyList(),
            leftTime = "00:00:00",
            currentTimerId = 0,
            focusDistributionList = emptyList()
        )
    }
}

sealed class MoreEvent : UiEvent {
    data class EnterMoreScreen(val context: Context) : MoreEvent()
    data class OnCompleteRegisterButton (val appList : List<AppInfo>): MoreEvent()
    data class SetBlockingAppList(val appList: List<String>) : MoreEvent()
    data class SetIsTimerActive(val isActive: Boolean) : MoreEvent()
    data object EndTimer : MoreEvent()
    data class UpdateCheckedList(val checkedList: List<Boolean>) : MoreEvent()
    data class ToggleCheckedIndex(val index: Int) : MoreEvent()
}

class MoreReducer(state: MoreState) : Reducer<MoreState, MoreEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: MoreState, event: MoreEvent) {
        when (event) {
            is MoreEvent.EnterMoreScreen -> {
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
            is MoreEvent.SetBlockingAppList -> {
                setState(oldState.copy(blockingAppPackageList = event.appList))
            }
            is MoreEvent.SetIsTimerActive -> {
                setState(oldState.copy(isTimerActive = event.isActive))
            }
            is MoreEvent.EndTimer -> {
                setState(oldState.copy(isTimerActive = false))
            }
            is MoreEvent.UpdateCheckedList -> {
                setState(oldState.copy(checkedList = event.checkedList))
            }

            is MoreEvent.ToggleCheckedIndex -> {
                val updatedChecked = oldState.checkedList.toMutableList().also {
                    it[event.index] = !it[event.index]
                }
                setState(oldState.copy(checkedList = updatedChecked))
            }
            else -> {}
        }
    }
}