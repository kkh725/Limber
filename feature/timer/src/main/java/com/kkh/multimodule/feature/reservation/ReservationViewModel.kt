package com.kkh.multimodule.feature.reservation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.data.mapper.toReservationItemModelList
import com.kkh.multimodule.core.domain.model.PatchTimerModel
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val timerRepository: TimerRepository,
    private val blockReservationRepository: BlockReservationRepository
) : ViewModel() {

    private val reducer = ReservationReducer(ReservationState.Companion.init())
    val uiState get() = reducer.uiState
    val sideEffect get() = reducer.uiEffect

    fun sendEvent(e: ReservationEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is ReservationEvent.OnEnteredScreen -> {
                    getTimerList()
                }

                is ReservationEvent.BottomSheet.OnClickReservationButton -> {
                    if (e.title.isEmpty()) {
                        reducer.sendEffect(CommonEffect.ShowSnackBar("실험의 제목을 설정해주세요."))
                    } else {
                        reserveScheduledTimer(e.title)
                    }
                }

                is ReservationEvent.OnToggleChanged -> {
                    patchTimerStatus(e.id, e.checked)
                }

                is ReservationEvent.OnClickRemoveButton -> {
                    deleteTimer()
                }

                else -> {}
            }
        }
    }

    // 사용자 예약 목록 조회
    private suspend fun getTimerList() {
        reducer.sendEffect(CommonEffect.IsLoading(true))
        timerRepository.getTimerList("UUID1")
            .onSuccess {
                reducer.setState(uiState.value.copy(ReservationItemModelList = it.toReservationItemModelList()))
                blockReservationRepository.setReservationList(it.toReservationItemModelList())
            }.onFailure { throwable ->
                val message = throwable.message ?: "error"
                reducer.sendEffect(CommonEffect.ShowSnackBar(message))
            }
        reducer.sendEffect(CommonEffect.IsLoading(false))

    }

    // 예약하기 api 전송
    private suspend fun reserveScheduledTimer(title: String) {
        val reservationInfo = uiState.value.ReservationInfo.copy(title = title)

        timerRepository.reserveScheduledTimer(reservationInfo.toSingleTimerModel())
            .onSuccess {
                reducer.setState(
                    uiState.value.copy(
                        ReservationInfo = ReservationInfo.init(),
                        isSheetVisible = false
                    ),
                )
                reducer.sendEffect(SideEffect.NavigateToHome)
            }.onFailure { throwable ->
                val message = throwable.message ?: "error"
                Log.d(TAG, "error message: ${throwable.message}")
                reducer.sendEffect(CommonEffect.ShowSnackBar(message))
            }
    }

    // 예약 on off toggle event
    private suspend fun patchTimerStatus(id: Int, action: Boolean) {
        val status = if (action) "ON" else "OFF"

        val newList = uiState.value.ReservationItemModelList.map {
            if (it.id == id) it.copy(isToggleChecked = action) else it
        }
        reducer.setState(uiState.value.copy(ReservationItemModelList = newList))

        timerRepository.patchTimerStatus(timerId = id, status = PatchTimerModel(status))
            .onSuccess {
            }
            .onFailure { throwable ->
                val newList = uiState.value.ReservationItemModelList.map {
                    if (it.id == id) it.copy(isToggleChecked = !action) else it
                }
                reducer.setState(uiState.value.copy(ReservationItemModelList = newList))
                val message = throwable.message ?: "error"
                reducer.sendEffect(CommonEffect.ShowSnackBar(message))
            }
        reducer.sendEffect(CommonEffect.IsLoading(false))
    }

    private suspend fun deleteTimer() {
        val checkedList = uiState.value.ReservationItemModelList.filter { it.isRemoveChecked }.map { it.id }
        timerRepository.deleteTimerList(checkedList)
            .onSuccess {
                Log.d(TAG, "deleteTimer: success")
            }.onFailure { e ->
                Log.d(TAG, "deleteTimer: fail $e")
            }
    }
}
