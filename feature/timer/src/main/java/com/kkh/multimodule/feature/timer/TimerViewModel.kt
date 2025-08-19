package com.kkh.multimodule.feature.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.appinfo.AppInfoProvider.getAppInfoListFromPackageNames
import com.kkh.multimodule.core.accessibility.block.BlockAlarmManager
import com.kkh.multimodule.core.data.mapper.toReservationItemModel
import com.kkh.multimodule.core.data.error.onLimberFailure
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState
    val sideEffect get() = reducer.uiEffect

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when (e) {
                is TimerEvent.OnEnterTimerScreen -> {
                    setTimerState()
                    setBottomSheetCheckedList()
                }

                is TimerEvent.OnClickSheetCompleteButton -> {
                    // modal 에서 시작하기를 눌러야 차단해야할듯.
                    setBlockedPackageList()
                }

                is TimerEvent.ShowModal -> {
                    if (e.isModalVisible) {
                        setBlockedAppList(e.context)
                    }
                }

                is TimerEvent.OnClickStartTimerNow -> {
                    val type = uiState.value.chipList.find { it.isSelected }
                    type?.let {
                        val newReservation =
                            e.startBlockReservationInfo.reservationInfo.copy(category = it.text)
                        val reservationItem =
                            e.startBlockReservationInfo.copy(reservationInfo = newReservation)
                        // 타이머 지금 시작 추가.
                        startImmediateTimer(
                            context = e.context,
                            timerItemModel = reservationItem
                        )

                    }
                }

                else -> {}
            }
        }
    }

    private suspend fun setBlockedAppList(context: Context) {
        val blockedPackageList = appDataRepository.getBlockedPackageList()
        val blockedAppList = getAppInfoListFromPackageNames(context, blockedPackageList)

        reducer.setState(uiState.value.copy(modalAppDataList = blockedAppList))
    }

    private suspend fun setBlockedPackageList() {
        val packageList = uiState.value.appDataList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setTimerState() {
        val isTimerActive = appDataRepository.getBlockMode()
        reducer.setState(uiState.value.copy(isTimerActive = isTimerActive))
    }

    private suspend fun setBottomSheetCheckedList() {
        val res = appDataRepository.getBlockedPackageList()
        val appInfoList = uiState.value.appDataList
        val checkedList = appInfoList.map { res.contains(it.packageName) }
        reducer.setState(uiState.value.copy(checkedList = checkedList))
    }

    // 당장 타이머 시작.
    private suspend fun startImmediateTimer(
        context: Context,
        timerItemModel: ReservationItemModel
    ) {

        val request = timerItemModel.toSingleTimerModel()
        //api 전송
        timerRepository.reserveImmediateTimer(request)
            .onSuccess {
                // success라면 로컬에서 실시함.
                appDataRepository.setBlockMode(true)
                BlockAlarmManager.scheduleBlockTrigger(
                    context = context,
                    reservation = it.toReservationItemModel(),
                    isStart = false
                )
                timerRepository.setActiveTimerId(it.id)
                reducer.sendEffect(CommonEffect.NavigateToHome)
            }
            .onFailure { throwable ->
                val message = throwable.message ?: "error"
                reducer.sendEffect(CommonEffect.ShowSnackBar(message))
            }
    }
}