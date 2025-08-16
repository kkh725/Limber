package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class FocusDistributionResponseDto(
    val repeatCycleCode: String,   // 예: "DAILY"
    val totalActualMinutes: Int    // 예: 560
)

typealias FocusDistributionResponse = ApiResponse<List<FocusDistributionResponseDto>>
