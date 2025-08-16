package com.kkh.multimodule.core.data.error
import retrofit2.HttpException

inline fun <T> Result<T>.onLimberFailure(
    action: (Throwable) -> Unit
): Result<T> {

    exceptionOrNull()?.let { throwable ->
        when (throwable) {
            is HttpException -> {
                // HTTP 상태코드별 커스텀 매핑
                val mappedException = when (throwable.code()) {
                    409 -> TimerApiException(TimerError.TimerConflict("해당 시간에 이미 진행 중인 타이머가 존재합니다."))
                    else -> TimerApiException(TimerError.Unknown("HTTP ${throwable.code()}"))
                }
                action(mappedException)
            }
            else -> action(throwable) // 기존 예외 그대로 전달
        }
    }

    return this
}

sealed class TimerError(val code: String, val message: String) {

    data class TimerConflict(val errorMessage: String) :
        TimerError("TIMER_CONFLICT", errorMessage)

    data class Unknown(val errorMessage: String) :
        TimerError("UNKNOWN", errorMessage)

    companion object {
        fun from(code: String?, message: String?): TimerError = when (code) {
            "TIMER_CONFLICT" ->
                TimerConflict(message ?: "해당 시간에 이미 진행 중인 타이머가 존재합니다.")
            else ->
                Unknown(message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}

class TimerApiException(val error: TimerError) : RuntimeException(error.message)

