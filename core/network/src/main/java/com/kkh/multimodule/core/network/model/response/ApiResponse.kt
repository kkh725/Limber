package com.kkh.multimodule.core.network.model.response

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
    ){
        companion object{
            val empty = ApiError("500", "server error")
        }
    }
}


enum class HttpErrorStatus(val code: Int, val message: String){
    UNAUTHORIZED(401, "인증 오류"),
    DUPLICATE(409, "중복 에러"),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류")
}

enum class ApiErrorStatus(val code: String, val message: String){

}

fun handleHttpError(code: Int): Exception {
    return when (code) {
        HttpErrorStatus.UNAUTHORIZED.code -> IllegalAccessException(HttpErrorStatus.UNAUTHORIZED.message)
        HttpErrorStatus.DUPLICATE.code -> IllegalStateException(HttpErrorStatus.UNAUTHORIZED.message)
        HttpErrorStatus.INTERNAL_SERVER_ERROR.code -> Exception(HttpErrorStatus.INTERNAL_SERVER_ERROR.message)
        else -> Exception("HTTP 오류 코드: $code")
    }
}

fun handleApiError(apiError : ApiResponse.ApiError): Exception {
    return when (apiError.code) {
        "409" -> IllegalStateException("중복된 요청입니다.")
        else -> Exception("API 에러: [$apiError.code] ${apiError.message}")
    }
}

inline fun <reified T> Response<ApiResponse<T>>.processApiResponse(): T {
    if (this.isSuccessful) {
        val body = this.body() ?: throw NullPointerException("응답 바디가 null입니다.")

        if (body.success) {
            return body.data ?: throw NullPointerException("응답 데이터가 null입니다.")
        } else {
            throw handleApiError(body.error ?: ApiResponse.ApiError.empty)
        }
    } else {
        throw handleHttpError(this.code())
    }
}

// 단일 조회
typealias SingleTimerResponse = ApiResponse<SingleTimerStatusDto>
// 리스트 조회
typealias TimerListResponse = ApiResponse<List<SingleTimerStatusDto>>
// 기본
typealias BaseResponse = ApiResponse<Unit>
