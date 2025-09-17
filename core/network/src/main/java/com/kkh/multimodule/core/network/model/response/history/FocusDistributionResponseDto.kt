package com.kkh.multimodule.core.network.model.response.history

import com.kkh.multimodule.core.network.model.response.ApiResponse

data class FocusDistributionResponseDto(
    val focusTypeId: Int,   // 예: "DAILY"
    val focusTypeName: String, // 예: "일간"
    val totalActualMinutes : Int// 예: 560
)

typealias FocusDistributionResponse = ApiResponse<List<FocusDistributionResponseDto>>
