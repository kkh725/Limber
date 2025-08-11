package com.kkh.multimodule.core.network.model

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.network.model.response.ApiResponse
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto

enum class TimerStatus {
    @SerializedName("READY")
    READY,
    @SerializedName("RUNNING")
    RUNNING,
    @SerializedName("PAUSED")
    PAUSED,
    @SerializedName("COMPLETED")
    COMPLETED,
    @SerializedName("CANCELED")
    CANCELED
}
typealias CurrentTimerStatusResponse = ApiResponse<TimerStatus>

enum class RepeatCycleCode {
    @SerializedName("NONE")
    NONE,
    @SerializedName("EVERYDAY")
    EVERYDAY,
    @SerializedName("WEEKDAY")
    WEEKDAY,
    @SerializedName("WEEKEND")
    WEEKEND
}