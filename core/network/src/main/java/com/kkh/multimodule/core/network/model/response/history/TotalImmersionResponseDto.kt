package com.kkh.multimodule.core.network.model.response.history

data class TotalImmersionResponseDto(
    val totalActualMinutes : String,
    val totalScheduledMinutes : String,
    val ratio : Double
)

data class TotalActualResponseDto(
    val totalMinutes : String,
    val label : String
)


