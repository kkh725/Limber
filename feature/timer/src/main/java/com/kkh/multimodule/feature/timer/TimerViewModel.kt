package com.kkh.multimodule.feature.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfoProvider.getAppInfoListFromPackageNames
import com.kkh.multimodule.core.accessibility.BlockAlarmManager
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.domain.model.toSingleTimerModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import java.time.temporal.TemporalQueries.localTime

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when (e) {
                is TimerEvent.OnEnterTimerScreen -> {
                    setTimerState()
                }
                is TimerEvent.OnClickSheetCompleteButton -> {
                    // modal 에서 시작하기를 눌러야 차단해야할듯.
                    setBlockedPackageList()
                }

                is TimerEvent.ShowModal -> {
                    if (e.isModalVisible){
                        setBlockedAppList(e.context)
                    }
                }

                is TimerEvent.OnClickStartTimerNow -> {
                    val type = uiState.value.chipList.find { it.isSelected }
                    type?.let {
                        val newReservation = e.startBlockReservationInfo.reservationInfo.copy(category = it.text)
                        val reservationItem = e.startBlockReservationInfo.copy(reservationInfo = newReservation)
                        // 타이머 지금 시작 추가.
                        setActiveBlockReservation(
                            context = e.context,
                            startReservationInfo = reservationItem
                        )

                    }
                }

                else -> {}
            }
        }
    }

    private suspend fun setBlockedAppList(context : Context){
        val blockedPackageList = appDataRepository.getBlockedPackageList()
        val blockedAppList = getAppInfoListFromPackageNames(context , blockedPackageList)

        reducer.setState(uiState.value.copy(modalAppDataList = blockedAppList))
    }

    private suspend fun setBlockedPackageList() {
        val packageList = uiState.value.appDataList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setTimerState(){
        val isTimerActive = appDataRepository.getBlockMode()
        reducer.setState(uiState.value.copy(isTimerActive = isTimerActive))
    }

    // 당장 타이머 시작.
    private suspend fun setActiveBlockReservation(
        context : Context,
        startReservationInfo: ReservationItemModel
    ) {
        // 지금 당장 시작 후 종료 예약 트리거 on.
//        appDataRepository.setBlockMode(true)
//        BlockAlarmManager.scheduleBlockTrigger(context = context, reservation = startReservationInfo, isStart = false)

        //todo 이거 id 수정 필요
        val request = startReservationInfo.toSingleTimerModel()
        //api 전송
        timerRepository.reserveTimer(request)
            .onFailure {
                //todo fail 토스트 올리기
            }
    }
}