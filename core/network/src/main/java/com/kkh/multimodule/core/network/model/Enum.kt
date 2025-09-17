package com.kkh.multimodule.core.network.model

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.network.model.response.ApiResponse
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto

enum class TimerStatus {
    @SerializedName("ON")
    ON,
    @SerializedName("OFF")
    OFF
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