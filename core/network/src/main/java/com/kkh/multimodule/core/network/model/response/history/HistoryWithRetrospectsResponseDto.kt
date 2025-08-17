package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class HistoryWithRetrospectsResponseDto(
    val weekStart : String,
    val weekEnd : String,
    val items : List<LatestTimerHistoryDto>
)

typealias HistoryWithRetrospectsResponse = ApiResponse<List<HistoryWithRetrospectsResponseDto>>
