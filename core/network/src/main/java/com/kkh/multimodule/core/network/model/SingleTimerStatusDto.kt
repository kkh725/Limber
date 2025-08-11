package com.kkh.multimodule.core.network.model

import com.google.gson.annotations.SerializedName

data class SingleTimerStatusDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("focusTypeId")
    val focusTypeId: Int,
    @SerializedName("repeatCycleCode")
    val repeatCycleCode: String,
    @SerializedName("repeatDays")
    val repeatDays: String,
    @SerializedName("startTime")
    val startTime: TimeData,
    @SerializedName("endTime")
    val endTime: TimeData,
    @SerializedName("status")
    val status: String
) {
    data class TimeData(
        @SerializedName("hour")
        val hour: Int,
        @SerializedName("minute")
        val minute: Int,
        @SerializedName("second")
        val second: Int,
        @SerializedName("nano")
        val nano: Int
    )
}