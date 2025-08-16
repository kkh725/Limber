package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class LatestTimerHistoryDto(
    val id: Int,
    val timerId: Int,
    val userId: String,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val historyDt: String,             // ISO 8601 문자열, 필요하면 Instant로 변환 가능
    val historyStatus: String,
    val failReason: String,
    val startTime: String,
    val endTime: String,
    val hasRetrospect: Boolean,
    val retrospectId: Int,
    val retrospectImmersion: Int,
    val retrospectComment: String,
    val focusTypeTitle: String,
    val retrospectSummary: String
)

typealias LatestTimerResponse = ApiResponse<LatestTimerHistoryDto>

