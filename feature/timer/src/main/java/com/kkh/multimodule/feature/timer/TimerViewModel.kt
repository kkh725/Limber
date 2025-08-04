package com.kkh.multimodule.feature.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfoProvider.getAppInfoListFromPackageNames
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import java.time.temporal.TemporalQueries.localTime

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val reservationRepository: BlockReservationRepository
) :
    ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when (e) {
                is TimerEvent.OnClickSheetCompleteButton -> {
                    // modal 에서 시작하기를 눌러야 차단해야할듯.
                    setBlockedPackageList()
                }

                is TimerEvent.ShowModal -> {
                    if (e.isModalVisible){
                        getBlockedAppList(e.context)
                    }
                }

                is TimerEvent.OnClickModalCompleteButton -> {
                    val type = uiState.value.chipList.find { it.isSelected }
                    type?.let {
                        val newReservation = e.startBlockReservationInfo.reservationInfo.copy(category = it.text)
                        val reservationItem = e.startBlockReservationInfo.copy(reservationInfo = newReservation)
                        // 타이머 지금 시작 추가.
                        setActiveBlockReservation(reservationItem)

                    }
                }

                else -> {}
            }
        }
    }

    private suspend fun getBlockedAppList(context : Context){
        val blockedPackageList = appDataRepository.getBlockedPackageList()
        val blockedAppList = getAppInfoListFromPackageNames(context , blockedPackageList)

        reducer.setState(uiState.value.copy(modalAppDataList = blockedAppList))
    }

    private suspend fun setBlockedPackageList() {
        val packageList = uiState.value.appDataList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    // 당장 타이머 시작.
    private suspend fun setActiveBlockReservation(
        startReservationInfo: ReservationItemModel
    ) {
        // 예약 리스트에 당장 추가.
        val oldReservationList = reservationRepository.getReservationList()
        val newReservationList = oldReservationList.toMutableList().apply {
            add(startReservationInfo)
        }
        reservationRepository.setReservationList(newReservationList)
    }
}