package com.kkh.multimodule.core.network.model.response

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import retrofit2.Response

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T?,
    @SerializedName("error")
    val error: ApiError?
){
    data class ApiError(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String
    )
}

enum class HttpErrorStatus(val code: Int, val message: String){
    UNAUTHORIZED(401, "인증 오류가 발생했습니다."),
    INVALID_INPUT(400,"입력값이 올바르지 않습니다."),
    DUPLICATE(409, "해당 시간에 이미 진행중인 타이머가 존재합니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
    NOT_FOUND_TIMER(404,"타이머가 존재하지 않습니다.")
}

fun handleHttpError(code: Int): Exception {
    return when (code) {
        HttpErrorStatus.UNAUTHORIZED.code -> IllegalAccessException(HttpErrorStatus.UNAUTHORIZED.message)
        HttpErrorStatus.INVALID_INPUT.code -> IllegalArgumentException(HttpErrorStatus.INVALID_INPUT.message)
        HttpErrorStatus.DUPLICATE.code -> IllegalStateException(HttpErrorStatus.UNAUTHORIZED.message)
        HttpErrorStatus.INTERNAL_SERVER_ERROR.code -> Exception(HttpErrorStatus.INTERNAL_SERVER_ERROR.message)
        HttpErrorStatus.NOT_FOUND_TIMER.code -> Exception(HttpErrorStatus.NOT_FOUND_TIMER.message)
        else -> Exception("HTTP 오류 코드: $code")
    }
}

inline fun <reified T> Response<ApiResponse<T>>.processApiResponse(): T {
    if (this.isSuccessful) {
        val body = this.body() ?: throw NullPointerException(HttpErrorStatus.INTERNAL_SERVER_ERROR.message)

        if (body.success) {
            Log.d("ApiResponse", "success: ${true}, data: ${body.data}")
            return body.data ?: throw NullPointerException(HttpErrorStatus.INTERNAL_SERVER_ERROR.message)
        } else {
            Log.e("ApiResponse", "success: ${false}, data: ${body.data}")
            throw Exception(body.error?.message ?: HttpErrorStatus.INTERNAL_SERVER_ERROR.message)
        }
    } else {
        Log.e("HttpError", "${this.code()}")
        throw handleHttpError(this.code())
    }
}

// 단일 조회
typealias SingleTimerResponse = ApiResponse<SingleTimerStatusDto>
// 리스트 조회
typealias TimerListResponse = ApiResponse<List<SingleTimerStatusDto>>
// 기본
typealias BaseResponse = ApiResponse<Unit>
