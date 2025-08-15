package com.kkh.multimodule.core.network.model.response

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T?,
    @SerializedName("error")
    val error: ApiError?
)

data class ApiError(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)

// 단일 조회
typealias SingleTimerResponse = ApiResponse<SingleTimerStatusDto>
// 리스트 조회
typealias TimerListResponse = ApiResponse<List<SingleTimerStatusDto>>
// 기본
typealias BaseResponse = ApiResponse<Unit>
