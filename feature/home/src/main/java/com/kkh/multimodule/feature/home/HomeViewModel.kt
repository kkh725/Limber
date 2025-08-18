package com.kkh.multimodule.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.util.getRemainingTimeFormattedSafe
import com.kkh.multimodule.core.ui.util.getTodayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val reservationRepository: BlockReservationRepository,
    private val timerRepository: TimerRepository,
    private val historyRepository: HistoryRepository
) :
    ViewModel() {

    private val reducer = HomeReducer(HomeState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: HomeEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is HomeEvent.OnCompleteRegisterButton -> {
                    setBlockedPackageList(e.appList)
                    setPackageList()
                }

                is HomeEvent.EnterHomeScreen -> {
                    setTimerState()
                    setBlockReservationList()
                    setPackageList()
                    getActiveTimerId()
                    getFocusDistributionList()
                    setBottomSheetCheckedList()
                }


                is HomeEvent.EndTimer -> {
                    setBlockMode(false)
                }

                else -> {}
            }
        }
    }

    private suspend fun setBlockedPackageList(appList: List<AppInfo>) {
        val packageList = appList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setPackageList() {
        sendEvent(HomeEvent.SetBlockingAppList(appDataRepository.getBlockedPackageList()))
    }

    private suspend fun setBlockMode(isBlockModeOn: Boolean = true) {
        appDataRepository.setBlockMode(isBlockModeOn)
    }

    private suspend fun setTimerState() {
        val isTimerActive = appDataRepository.getBlockMode()
        reducer.setState(uiState.value.copy(isTimerActive = isTimerActive))
    }

    private suspend fun setBlockReservationList() {
        val list = reservationRepository.getReservationList()
        reducer.setState(uiState.value.copy(blockReservationItemList = list))
    }

    private suspend fun setBottomSheetCheckedList(){
        val res = appDataRepository.getBlockedPackageList()
        val appInfoList = uiState.value.usageAppInfoList

        val checkedList = appInfoList.map { res.contains(it.packageName) }
        reducer.setState(uiState.value.copy(checkedList=checkedList))
    }

    private suspend fun getActiveTimerId() {

        val activeTimerId = timerRepository.getActiveTimerId()
        Log.d("getActiveTimerId", "현재 진행중인 타이머 id : $activeTimerId")
        timerRepository.getSingleTimer(activeTimerId)
            .onSuccess {
                if (it.status != TimerStatusModel.OFF) {
                    Log.d(
                        "getActiveTimerId",
                        "남은 시간 ${getRemainingTimeFormattedSafe(it.endTime)}"
                    )

                    reducer.setState(
                        uiState.value.copy(
                            leftTime = getRemainingTimeFormattedSafe(it.endTime),
                            currentTimerId = it.id
                        )
                    )
                }

            }.onFailure { throwable ->
                //수시로 호출하기 때문에 부수효과 x
//                    reducer.sendEffect(CommonEffect.ShowSnackBar(throwable.message.toString()))
            }
    }

    private suspend fun getFocusDistributionList() {
        val res = historyRepository.getFocusDistribution(
            startTime = getTodayDate(),
            endTime = getTodayDate()
        )
        res.onSuccess {
            reducer.setState(uiState.value.copy(focusDistributionList = it))
        }.onFailure { throwable ->
            reducer.sendEffect(CommonEffect.ShowSnackBar(throwable.message.toString()))
        }
    }
}