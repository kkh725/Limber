package com.kkh.multimodule.feature.reservation

import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.mockReservationItems
import com.kkh.multimodule.feature.timer.ReservationScreenState
import com.kkh.multimodule.feature.timer.ChipInfo
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import kotlinx.datetime.LocalTime
import kotlin.collections.map

sealed class BottomSheetState {
    data object Idle : BottomSheetState()
    data object Start : BottomSheetState()
    data object End : BottomSheetState()
    data object Repeat : BottomSheetState()
}

data class ReservationState(
    val reservationScreenState: ReservationScreenState,
    val ReservationItemModelList: List<ReservationItemModel>,
    val ReservationInfo: ReservationInfo,
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
            ReservationItemModelList = ReservationItemModel.mockList(),
            chipList = ChipInfo.mockList,
            repeatOptionList = ChipInfo.repeatOptionList,
            reservationBottomSheetState = BottomSheetState.Idle,
            ReservationInfo = ReservationInfo.init(),
            dayList = ChipInfo.dayList
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
        data class OnClickStartTimeCompleteButton(val time: String) : BottomSheet()
        data class OnClickEndTimeCompleteButton(val time: String) : BottomSheet()
        data class OnClickReservationButton(val title: String) : BottomSheet()
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
                val newList = oldState.ReservationItemModelList.map {
                    if (it.id == event.id) it.copy(isToggleChecked = event.checked) else it
                }
                setState(oldState.copy(ReservationItemModelList = newList))
            }

            is ReservationEvent.OnRemoveCheckChanged -> {
                val newList = oldState.ReservationItemModelList.map {
                    if (it.id == event.id) it.copy(isRemoveChecked = event.checked) else it
                }
                setState(oldState.copy(ReservationItemModelList = newList))
            }

            // 수정페이지 전체선택.
            is ReservationEvent.OnClickAllSelected -> {
                val newAllSelected = !oldState.isClickedAllSelected
                var newList = oldState.ReservationItemModelList
                newList = if (newAllSelected) {
                    oldState.ReservationItemModelList.map {
                        it.copy(isRemoveChecked = true)
                    }
                } else {
                    oldState.ReservationItemModelList.map {
                        it.copy(isRemoveChecked = false)
                    }
                }
                setState(
                    oldState.copy(
                        ReservationItemModelList = newList,
                        isClickedAllSelected = newAllSelected
                    )
                )
            }

            is ReservationEvent.OnClickModifyCompleteButton -> {
                val newList = oldState.ReservationItemModelList.map {
                    it.copy(isRemoveChecked = false)
                }
                setState(
                    oldState.copy(
                        reservationScreenState = ReservationScreenState.Idle,
                        ReservationItemModelList = newList,
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
                        ReservationInfo = ReservationInfo.init()
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
                val everyDay = setOf("월", "화", "수","목","금","토","일")
                val weekDay = setOf("월", "화", "수", "목", "금")
                val weekend = setOf("토", "일")

                val newList = oldState.dayList.map { chip ->
                    if (chip.text == event.chipText) {
                        chip.copy(isSelected = !chip.isSelected) // 내가 누른 것만 토글
                    } else {
                        chip
                    }
                }
                val selectedDayTexts = newList.filter { it.isSelected }.map { it.text }.toSet()
                val newOptionList = when (selectedDayTexts) {
                    everyDay -> oldState.repeatOptionList.map { option ->
                        if (option.text == "매일") {
                            option.copy(isSelected = true)
                        } else {
                            option.copy(isSelected = false)
                        }
                    }

                    weekDay -> {
                        oldState.repeatOptionList.map { option ->
                            if (option.text == "평일") {
                                option.copy(isSelected = true)
                            } else {
                                option.copy(isSelected = false)
                            }
                        }
                    }

                    weekend -> {
                        oldState.repeatOptionList.map { option ->
                            if (option.text == "주말") {
                                option.copy(isSelected = true)
                            } else {
                                option.copy(isSelected = false)
                            }
                        }
                    }

                    else -> {
                        oldState.repeatOptionList.map { option ->
                            option.copy(isSelected = false)
                        }
                    }
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
                        ReservationInfo = oldState.ReservationInfo.copy(repeatDays = dayList)
                    )
                )
            }
            // 바텀시트 시작 시각 저장 이벤트
            is ReservationEvent.BottomSheet.OnClickStartTimeCompleteButton -> {
                val newTime = oldState.ReservationInfo.copy(startTime = event.time)
                setState(
                    oldState.copy(
                        ReservationInfo = newTime,
                        reservationBottomSheetState = BottomSheetState.Idle
                    )
                )
            }
            // 바텀시트 종료 시각 저장 이벤트
            is ReservationEvent.BottomSheet.OnClickEndTimeCompleteButton -> {
                val newTime = oldState.ReservationInfo.copy(endTime = event.time)
                setState(
                    oldState.copy(
                        ReservationInfo = newTime,
                        reservationBottomSheetState = BottomSheetState.Idle
                    )
                )
            }
            // 바텀시트 최종 예약하기 버튼
            is ReservationEvent.BottomSheet.OnClickReservationButton -> {
                val newReservationInfo = oldState.ReservationInfo.copy(title = event.title)

                setState(
                    oldState.copy(
                        ReservationInfo = newReservationInfo,
                        isSheetVisible = false
                    )
                )
            }
        }
    }
}