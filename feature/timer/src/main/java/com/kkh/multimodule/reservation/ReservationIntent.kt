package com.kkh.multimodule.reservation

import com.kkh.multimodule.timer.ReservationScreenState
import com.kkh.multimodule.timer.ChipInfo
import com.kkh.multimodule.timer.ReservationTime
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
import kotlinx.datetime.LocalTime

sealed class BottomSheetState {
    data object Idle : BottomSheetState()
    data object Start : BottomSheetState()
    data object End : BottomSheetState()
    data object Repeat : BottomSheetState()
}

data class ReservationState(
    val reservationScreenState: ReservationScreenState,
    val reservationItemList: List<ReservationInfo>,
    val reservationTime : ReservationTime,
    val isClickedAllSelected: Boolean,
    val reservationBottomSheetState: BottomSheetState,
    val repeatOptionList: List<ChipInfo>,
    val isSheetVisible: Boolean = false,
    val chipList: List<ChipInfo>,
    val dayList: List<ChipInfo>
) : UiState {
    companion object {
        fun init() = ReservationState(
            reservationScreenState = ReservationScreenState.Idle,
            isClickedAllSelected = false,
            reservationItemList = listOf(
                ReservationInfo(1, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false),
                ReservationInfo(2, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false),
                ReservationInfo(3, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false),
                ReservationInfo(4, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false),
                ReservationInfo(5, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false),
                ReservationInfo(6, ReservationTime.init(),isToggleChecked = true,isRemoveChecked = false)
            ),
            chipList = listOf(
                ChipInfo("하나"),
                ChipInfo("둘"),
                ChipInfo("셋"),
                ChipInfo("넷"),
                ChipInfo("직접 추가")
            ),
            repeatOptionList = listOf(
                ChipInfo("매일"),
                ChipInfo("평일"),
                ChipInfo("주말"),
            ),
            reservationBottomSheetState = BottomSheetState.Idle,
            reservationTime = ReservationTime.init(),
            dayList = listOf(
                ChipInfo("월"),
                ChipInfo("화"),
                ChipInfo("수"),
                ChipInfo("목"),
                ChipInfo("금"),
                ChipInfo("토"),
                ChipInfo("일")
            )
        )
    }
}

sealed class ReservationEvent : UiEvent {
    data object OnClickModifyButton : ReservationEvent()
    data class OnToggleChanged(val id: Int, val checked: Boolean) : ReservationEvent()
    data object OnClickModifyCompleteButton : ReservationEvent()
    data class OnRemoveCheckChanged(val id: Int, val checked: Boolean) : ReservationEvent()
    data object OnClickAllSelected : ReservationEvent()

    sealed class BottomSheet : ReservationEvent() {
        data class NavigateTo(val state: BottomSheetState) : BottomSheet()
        data object Back : BottomSheet()
        data object Close : BottomSheet()
        data class Show(val isVisible: Boolean) : BottomSheet()
        data class OnClickFocusChip(val chipText: String) : ReservationEvent()
        data class OnClickRepeatOptionChip(val chipText: String) : BottomSheet()
        data class OnClickDayChip(val chipText: String) : BottomSheet()
        data object OnClickRepeatOptionCompleteButton : BottomSheet()
        data class OnClickStartTimeCompleteButton(val time : LocalTime) : BottomSheet()
        data class OnClickEndTimeCompleteButton(val time : LocalTime) : BottomSheet()
        data class OnClickReservationButton(val title : String) : BottomSheet()
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

            // 수정페이지 전체선택.
            is ReservationEvent.OnClickAllSelected -> {
                val newAllSelected = !oldState.isClickedAllSelected
                var newList = oldState.reservationItemList
                newList = if (newAllSelected){
                    oldState.reservationItemList.map {
                        it.copy(isRemoveChecked = true)
                    }
                } else {
                    oldState.reservationItemList.map {
                        it.copy(isRemoveChecked = false)
                    }
                }
                setState(oldState.copy(reservationItemList = newList, isClickedAllSelected = newAllSelected))
            }

            is ReservationEvent.OnClickModifyCompleteButton -> {
                val newList = oldState.reservationItemList.map {
                    it.copy(isRemoveChecked = false)
                }
                setState(
                    oldState.copy(
                        reservationScreenState = ReservationScreenState.Idle,
                        reservationItemList = newList,
                        isClickedAllSelected = false
                    )
                )
            }

            // 바텀시트 집중 칩 선택
            is ReservationEvent.BottomSheet.OnClickFocusChip -> {
                val newChipList = oldState.chipList.map { chip ->
                    chip.copy(isSelected = chip.text == event.chipText)
                }
                setState(oldState.copy(chipList = newChipList))
            }

            // 바텀시트 화면이동
            is ReservationEvent.BottomSheet.NavigateTo -> {
                setState(oldState.copy(reservationBottomSheetState = event.state))
            }

            // 바텀시트 뒤로가기
            is ReservationEvent.BottomSheet.Back -> {
                // 반복 옵션 선택했던 것들 초기화.
                val newList = oldState.repeatOptionList.map { chip ->
                    chip.copy(isSelected = false)
                }
                val newDayList = oldState.dayList.map { chip ->
                    chip.copy(isSelected = false)
                }

                setState(
                    oldState.copy(
                        reservationBottomSheetState = BottomSheetState.Idle,
                        repeatOptionList = newList,
                        dayList = newDayList
                    )
                )
            }

            // 바텀시트 닫기
            is ReservationEvent.BottomSheet.Close -> {

                setState(
                    oldState.copy(
                        reservationBottomSheetState = BottomSheetState.Idle,
                        isSheetVisible = false,
                        reservationTime = ReservationTime.init()
                    )
                )
            }

            // 바텀시트 오픈
            is ReservationEvent.BottomSheet.Show -> {
                setState(oldState.copy(isSheetVisible = event.isVisible))
            }

            is ReservationEvent.BottomSheet.OnClickRepeatOptionChip -> {
                val isAlreadySelected =
                    oldState.repeatOptionList.any { it.text == event.chipText && it.isSelected }

                if (isAlreadySelected) {
                    // 이미 선택된 상태에서 다시 누르면 모두 해제
                    val newRepeatOptionList =
                        oldState.repeatOptionList.map { it.copy(isSelected = false) }
                    val newDayList = oldState.dayList.map { it.copy(isSelected = false) }

                    setState(
                        oldState.copy(
                            repeatOptionList = newRepeatOptionList,
                            dayList = newDayList
                        )
                    )
                } else {
                    // 선택 안 된 상태면 기존 로직 적용
                    val newRepeatOptionList = oldState.repeatOptionList.map { chip ->
                        chip.copy(isSelected = chip.text == event.chipText)
                    }

                    val newDayList = when (event.chipText) {
                        "매일" -> oldState.dayList.map { it.copy(isSelected = true) }
                        "평일" -> oldState.dayList.map {
                            val isWeekday = it.text in listOf("월", "화", "수", "목", "금")
                            it.copy(isSelected = isWeekday)
                        }

                        "주말" -> oldState.dayList.map {
                            val isWeekend = it.text in listOf("토", "일")
                            it.copy(isSelected = isWeekend)
                        }

                        else -> oldState.dayList.map { it.copy(isSelected = false) }
                    }

                    setState(
                        oldState.copy(
                            repeatOptionList = newRepeatOptionList,
                            dayList = newDayList
                        )
                    )
                }
            }

            // 바텀시트 day 클릭이벤트. 여러개 클릭가능
            is ReservationEvent.BottomSheet.OnClickDayChip -> {
                val newList = oldState.dayList.map { chip ->
                    if (chip.text == event.chipText) {
                        chip.copy(isSelected = !chip.isSelected) // 내가 누른 것만 토글
                    } else {
                        chip
                    }
                }
                val newOptionList = oldState.repeatOptionList.map { option ->
                    option.copy(isSelected = false)
                }
                setState(oldState.copy(repeatOptionList = newOptionList, dayList = newList))
            }

            // 바텀시트 반복 옵션 (날짜 지정) 저장 이벤트
            is ReservationEvent.BottomSheet.OnClickRepeatOptionCompleteButton -> {
                val initRepeatOption = oldState.repeatOptionList.map { it.copy(isSelected = false) }
                val initDayOption = oldState.dayList.map { it.copy(isSelected = false) }

                // 선택된 dayList 의 string 만 저장.
                val dayList = oldState.dayList
                    .filter { it.isSelected }
                    .map { it.text }
                    .toMutableList()

                // 선택되어있는 반복옵션, day 옵션 제거 후 예약time에 저장한다.
                setState(
                    oldState.copy(
                        repeatOptionList = initRepeatOption,
                        dayList = initDayOption,
                        reservationBottomSheetState = BottomSheetState.Idle,
                        reservationTime = oldState.reservationTime.copy(repeatDays = dayList)
                    )
                )
            }
            // 바텀시트 시작 시각 저장 이벤트
            is ReservationEvent.BottomSheet.OnClickStartTimeCompleteButton -> {
                val newTime = oldState.reservationTime.copy(startTime = event.time)
                setState(
                    oldState.copy(
                        reservationTime = newTime,
                        reservationBottomSheetState = BottomSheetState.Idle
                    )
                )
            }
            // 바텀시트 종료 시각 저장 이벤트
            is ReservationEvent.BottomSheet.OnClickEndTimeCompleteButton -> {
                val newTime = oldState.reservationTime.copy(endTime = event.time)
                setState(
                    oldState.copy(
                        reservationTime = newTime,
                        reservationBottomSheetState = BottomSheetState.Idle
                    )
                )
            }
            // 바텀시트 최종 예약하기 버튼
            is ReservationEvent.BottomSheet.OnClickReservationButton -> {
                val newReservationTime = oldState.reservationTime.copy(title = event.title)
                
                setState(
                    oldState.copy(
                        reservationTime = newReservationTime,
                        isSheetVisible = false
                    )
                )
            }
        }
    }
}