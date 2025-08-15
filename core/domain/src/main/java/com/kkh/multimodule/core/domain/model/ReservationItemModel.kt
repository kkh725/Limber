package com.kkh.multimodule.core.domain.model

import com.kkh.multimodule.core.domain.RepeatCycleCodeModel
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.getCurrentTime
import com.kkh.multimodule.core.domain.getTimePlus30Minutes
import kotlinx.serialization.Serializable

@Serializable
data class ReservationItemModel(
    val id: Int = -1,
    val reservationInfo: ReservationInfo,
    var isToggleChecked: Boolean = false,
    var isRemoveChecked: Boolean = false,
) {
    companion object {
        val currentActive = ReservationItemModel(
            id = -1,
            reservationInfo = ReservationInfo.init(),
            isToggleChecked = true,
            isRemoveChecked = false
            )
        val init = ReservationItemModel(
            id = 0,
            reservationInfo = ReservationInfo.init(),
            isToggleChecked = false,
            isRemoveChecked = false
        )

        fun mockList() = mockReservationItems()
    }
    fun toSingleTimerModel(
        focusTypeId: Int = 1, // 필요 시 호출부에서 전달
        repeatCycleCode: String = RepeatCycleCodeModel.NONE.code, // 필요 시 호출부에서 전달
        status: TimerStatusModel = TimerStatusModel.OFF
    ): SingleTimerModel {
        return SingleTimerModel(
            id = id,
            title = reservationInfo.title,
            focusTypeId = focusTypeId,
            repeatCycleCode = repeatCycleCode,
            repeatDays = reservationInfo.repeatDays.joinToString(","), // List -> String 변환
            startTime = reservationInfo.startTime,
            endTime = reservationInfo.endTime,
            status = status
        )
    }
}




@Serializable
data class ReservationInfo(
    val title: String,
    val category: String,
    val startTime: String,
    val endTime: String,
    val repeatDays: List<String> = emptyList(),
    val repeatOption: String = RepeatCycleCodeModel.NONE.code
) {
    companion object {
        fun init() = ReservationInfo(
            startTime = getCurrentTime(),
            endTime = getTimePlus30Minutes(),
            title = "포트폴리오 작업",
            category = "공부",
            repeatDays = emptyList()
        )
    }
    fun toSingleTimerModel(
        focusTypeId: Int = 1, // 필요 시 호출부에서 전달
        repeatCycleCode: String = RepeatCycleCodeModel.NONE.code, // 필요 시 호출부에서 전달
        status: TimerStatusModel = TimerStatusModel.OFF
    ): SingleTimerModel {
        return SingleTimerModel(
            title = title,
            focusTypeId = focusTypeId,
            repeatCycleCode = repeatCycleCode,
            repeatDays = repeatDays.joinToString(","), // List -> String 변환
            startTime = startTime,
            endTime = endTime,
            status = status
        )
    }
}

/**
 * mock
 */

// 실제 UI에서 쓸 임시 mock 데이터
fun mockReservationItems(): List<ReservationItemModel> {
    return listOf(
        ReservationItemModel(
            id = 1,
            reservationInfo = ReservationInfo(
                title = "포트폴리오 작업하기",
                category = "학습",
                startTime = "07 : 30",
                endTime = "09 : 00",
                repeatDays = listOf("월", "수", "금")
            ),
            isToggleChecked = true
        ),
        ReservationItemModel(
            id = 2,
            reservationInfo = ReservationInfo(
                title = "운동",
                category = "건강",
                startTime = "06 : 00",
                endTime = "07 : 00",
                repeatDays = listOf("화", "목")
            ),
            isToggleChecked = false
        ),
        ReservationItemModel(
            id = 3,
            reservationInfo = ReservationInfo(
                title = "책 읽기",
                category = "취미",
                startTime = "20 : 00",
                endTime = "21 : 00",
                repeatDays = listOf("월", "화", "수", "목", "금")
            ),
            isToggleChecked = true,
            isRemoveChecked = true
        ),
        ReservationItemModel(
            id = 4,
            reservationInfo = ReservationInfo(
                title = "",
                category = "기타",
                startTime = "12 : 00",
                endTime = "13 : 00",
                repeatDays = emptyList()
            ),
            isToggleChecked = false
        )
    )
}
