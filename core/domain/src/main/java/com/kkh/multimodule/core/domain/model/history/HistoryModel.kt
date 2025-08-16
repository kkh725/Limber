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
    val failReason: String,
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
    val repeatCycleCode: String,
    val totalActualMinutes: Int
)

data class ActualByWeekendModel(
    val weekdayIndex: Int,
    val dayOfWeek: String,
    val totalActualMinutes: Int
)