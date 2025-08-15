package com.kkh.multimodule.core.domain.model

import com.kkh.multimodule.core.domain.RepeatCycleCodeModel
import com.kkh.multimodule.core.domain.TimerStatusModel


data class SingleTimerModel(
    val id: Int = -1,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val startTime: String,
    val endTime: String,
    val status: TimerStatusModel
) {
    companion object {
        val empty = SingleTimerModel(
            id = -1,
            title = "",
            focusTypeId = 1,
            repeatCycleCode = RepeatCycleCodeModel.NONE.code,
            repeatDays = "",
            startTime = "",
            endTime = "",
            status = TimerStatusModel.OFF
        )
    }

    fun focusTypeIdToCategory(focusTypeId: Int): String {
        return when (focusTypeId) {
            1 -> "학습"
            2 -> "업무"
            3 -> "회의"
            4 -> "작업"
            5 -> "독서"
            else -> "기타"
        }
    }

    fun repeatDaysToString(repeatDays: String): List<String> {
        // 0: 일요일, 1: 월요일, ..., 6: 토요일
        val dayMap = mapOf(
            "0" to "일",
            "1" to "월",
            "2" to "화",
            "3" to "수",
            "4" to "목",
            "5" to "금",
            "6" to "토"
        )

        return repeatDays
            .split(",")// "1,2,3" → ["1","2","3"]       // 다시 ","로 연결
            .mapNotNull { dayMap[it] }
    }
    fun repeatCycleCodeToString(repeatCycleCode: String): String {
        return when (repeatCycleCode) {
            RepeatCycleCodeModel.NONE.code -> "없음"
            RepeatCycleCodeModel.EVERYDAY.code -> "매일"
            RepeatCycleCodeModel.WEEKDAY.code -> "평일"
            RepeatCycleCodeModel.WEEKEND.code -> "주말"
            else -> "기타"
        }
    }
}

typealias TimerListModel = List<SingleTimerModel>

