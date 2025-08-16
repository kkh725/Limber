package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class ActualByWeekendResponseDto(
    val weekdayIndex: Int,
    val dayOfWeek: String,
    val totalActualMinutes: Int
)

typealias ActualByWeekendResponse = ApiResponse<List<ActualByWeekendResponseDto>>

