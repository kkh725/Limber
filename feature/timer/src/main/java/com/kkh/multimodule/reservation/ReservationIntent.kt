package com.kkh.multimodule.reservation

import com.kkh.multimodule.timer.ReservationScreenState
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState

data class ReservationState(
    val reservationScreenState: ReservationScreenState,
    val reservationItemList: List<ReservationInfo>
) : UiState {
    companion object {
        fun init() = ReservationState(
            reservationScreenState = ReservationScreenState.Idle,
            reservationItemList = listOf(
                ReservationInfo(1, "포트폴리오 작업하기", "매일 오전 8시에 업데이트", "작업", false),
                ReservationInfo(2, "운동 루틴", "매주 월, 수, 금 알림", "건강", true),
                ReservationInfo(3, "스터디 준비", "주말에 집중", "공부", false),
                ReservationInfo(4, "스터디 준비", "주말에 집중", "공부", false),
                ReservationInfo(5, "스터디 준비", "주말에 집중", "공부", false),
                ReservationInfo(6, "스터디 준비", "주말에 집중", "공부", false)
            )
        )
    }
}

sealed class ReservationEvent : UiEvent {
    data object OnClickModifyButton : ReservationEvent()
    data class OnToggleChanged(val id: Int, val checked: Boolean) : ReservationEvent()
    data object OnClickModifyCompleteButton : ReservationEvent()
    data class OnRemoveCheckChanged(val id: Int, val checked: Boolean) : ReservationEvent() // ← 추가
}

class ReservationReducer(state: ReservationState) :
    Reducer<ReservationState, ReservationEvent>(state) {
    override suspend fun reduce(oldState: ReservationState, event: ReservationEvent) {
        when (event) {
            // 편집하기 버튼 선택 이벤트
            is ReservationEvent.OnClickModifyButton -> {
                setState(oldState.copy(reservationScreenState = ReservationScreenState.Modify))
            }

            // 예약 설정 toggle on off 이벤트
            is ReservationEvent.OnToggleChanged -> {
                val newList = oldState.reservationItemList.map {
                    if (it.id == event.id) it.copy(isToggleChecked = event.checked) else it
                }
                setState(oldState.copy(reservationItemList = newList))
            }

            // remove 체크박스 선택 이벤트
            is ReservationEvent.OnRemoveCheckChanged -> {
                val newList = oldState.reservationItemList.map {
                    if (it.id == event.id) it.copy(isRemoveChecked = event.checked) else it
                }
                setState(oldState.copy(reservationItemList = newList))
            }

            // 편집 완료 선택 이벤트
            is ReservationEvent.OnClickModifyCompleteButton -> {
                // 모든 remove check 리스트 false로 초기화
                val newList = oldState.reservationItemList.map {
                    it.copy(isRemoveChecked = false)
                }

                setState(
                    oldState.copy(
                        reservationScreenState = ReservationScreenState.Idle,
                        reservationItemList = newList
                    )
                )
            }
        }
    }
}