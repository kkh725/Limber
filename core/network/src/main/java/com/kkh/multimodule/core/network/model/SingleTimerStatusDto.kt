package com.kkh.multimodule.core.network.model

import com.google.gson.annotations.SerializedName

data class SingleTimerRequestDto(
    @SerializedName("userid")
    val userid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("focusTypeId")
    val focusTypeId: Int,
    @SerializedName("repeatCycleCode")
    val repeatCycleCode: String,
    @SerializedName("repeatDays")
    val repeatDays: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String
)

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
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("status")
    val status: TimerStatus
)