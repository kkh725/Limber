package com.kkh.multimodule.core.domain.model.history

data class HistoryModel(
    val id: Int,
    val timerId: Int,
    val userId: String,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val historyDt: String,
    val historyStatus: String,
    val failReason: String?,
    val startTime: String,
    val endTime: String
)

data class TotalImmersionModel(
    val totalActualMinutes: String,
    val totalScheduledMinutes: String,
    val ratio: Double
)

data class TotalActualModel(
    val totalMinutes: String,
    val label: String
)

data class ImmersionByWeekdayModel(
    val weekdayIndex: Int,
    val dayOfWeek: String,
    val totalActualMinutes: Int,
    val totalScheduledMinutes: Int,
    val ratio: Double
)

data class FocusDistributionModel(
    val focusTypeId: Int,   // 예: "DAILY"
    val focusTypeName: String, // 예: "일간"
    val totalActualMinutes : Int// 예: 560
)

data class ActualByWeekendModel(
    val weekdayIndex: Int,
    val dayOfWeek: String,
    val totalActualMinutes: Int
)

data class LatestTimerHistoryModel(
    val id: Int,
    val timerId: Int,
    val userId: String,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val historyDt: String,             // ISO 8601 문자열, 필요하면 Instant로 변환 가능
    val historyStatus: String,
    val failReason: String?,
    val startTime: String,
    val endTime: String,
    val hasRetrospect: Boolean,
    val retrospectId: Int?,
    val retrospectImmersion: Int?,
    val retrospectComment: String?,
    val focusTypeTitle: String,
    val retrospectSummary: String
){
    companion object{
        val mockLatestTimerHistory = LatestTimerHistoryModel(
            id = 1,
            timerId = 101,
            userId = "user123",
            title = "CS 공부 타이머",
            focusTypeId = 5,
            repeatCycleCode = "WEEKLY",
            repeatDays = "MON,WED,FRI",
            historyDt = "2025-08-19T14:30:00Z",   // ISO 8601
            historyStatus = "COMPLETED",
            failReason = null,
            startTime = "2025-08-19T14:30:00Z",
            endTime = "2025-08-19T15:30:00Z",
            hasRetrospect = true,
            retrospectId = 501,
            retrospectImmersion = 4,
            retrospectComment = "오늘은 집중이 잘 됐음 👍",
            focusTypeTitle = "알고리즘 문제풀이",
            retrospectSummary = "문제 3개 풀었음"
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
}

data class HistoryWithRetrospectsModel(
    val weekStart: String,
    val weekEnd: String,
    val items: List<LatestTimerHistoryModel>
)