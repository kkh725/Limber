package com.kkh.multimodule.reservation

import com.kkh.multimodule.timer.ReservationScreenState
import com.kkh.multimodule.timer.ChipInfo
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState

sealed class BottomSheetState {
    data object Idle : BottomSheetState()
    data object Start : BottomSheetState()
    data object End : BottomSheetState()
    data object Repeat : BottomSheetState()
}

data class ReservationState(
    val reservationScreenState: ReservationScreenState,
    val reservationItemList: List<ReservationInfo>,
    val reservationBottomSheetState: BottomSheetState,
    val isSheetVisible: Boolean = false,
    val chipList: List<ChipInfo>
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
            ),
            chipList = listOf(
                ChipInfo("하나"),
                ChipInfo("둘"),
                ChipInfo("셋"),
                ChipInfo("넷"),
                ChipInfo("직접 추가")
            ),
            reservationBottomSheetState = BottomSheetState.Idle
        )
    }
}

sealed class ReservationEvent : UiEvent {
    data object OnClickModifyButton : ReservationEvent()
    data class OnToggleChanged(val id: Int, val checked: Boolean) : ReservationEvent()
    data object OnClickModifyCompleteButton : ReservationEvent()
    data class OnRemoveCheckChanged(val id: Int, val checked: Boolean) : ReservationEvent()
    data class OnClickFocusChip(val chipText: String) : ReservationEvent()

    sealed class BottomSheet : ReservationEvent() {
        data class NavigateTo(val state: BottomSheetState) : BottomSheet()
        data object Back : BottomSheet()
        data object Close : BottomSheet()
        data class Show(val isVisible: Boolean) : BottomSheet()
    }
}

class ReservationReducer(state: ReservationState) :
    Reducer<ReservationState, ReservationEvent>(state) {
    override suspend fun reduce(oldState: ReservationState, event: ReservationEvent) {
        when (event) {
            is ReservationEvent.OnClickModifyButton -> {
                setState(oldState.copy(reservationScreenState = ReservationScreenState.Modify))
            }

            is ReservationEvent.OnToggleChanged -> {
                val newList = oldState.reservationItemList.map {
                    if (it.id == event.id) it.copy(isToggleChecked = event.checked) else it
                }
                setState(oldState.copy(reservationItemList = newList))
            }

            is ReservationEvent.OnRemoveCheckChanged -> {
                val newList = oldState.reservationItemList.map {
                    if (it.id == event.id) it.copy(isRemoveChecked = event.checked) else it
                }
                setState(oldState.copy(reservationItemList = newList))
            }

            is ReservationEvent.OnClickModifyCompleteButton -> {
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

            is ReservationEvent.OnClickFocusChip -> {
                val newChipList = oldState.chipList.map { chip ->
                    chip.copy(isSelected = chip.text == event.chipText)
                }
                setState(oldState.copy(chipList = newChipList))
            }

            is ReservationEvent.BottomSheet.NavigateTo -> {
                setState(oldState.copy(reservationBottomSheetState = event.state))
            }

            is ReservationEvent.BottomSheet.Back -> {
                setState(oldState.copy(reservationBottomSheetState = BottomSheetState.Idle))
            }

            is ReservationEvent.BottomSheet.Close -> {
                setState(
                    oldState.copy(
                        reservationBottomSheetState = BottomSheetState.Idle,
                        isSheetVisible = false
                    )
                )
            }

            is ReservationEvent.BottomSheet.Show -> {
                setState(oldState.copy(isSheetVisible = event.isVisible))
            }
        }
    }
}