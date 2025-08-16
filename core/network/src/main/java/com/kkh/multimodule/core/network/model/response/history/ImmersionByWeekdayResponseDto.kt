package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class ImmersionByWeekdayResponseDto(
    val weekdayIndex: Int,
    val dayOfWeek: String,
    val totalActualMinutes: Int,
    val totalScheduledMinutes: Int,
    val ratio: Double
)

typealias ImmersionByWeekdayResponse = ApiResponse<List<ImmersionByWeekdayResponseDto>>

