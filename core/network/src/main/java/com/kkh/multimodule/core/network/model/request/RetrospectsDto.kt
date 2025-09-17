package com.kkh.multimodule.core.network.model.request

data class RetrospectsRequestDto(
    val userId: String,
    val timerHistoryId: Int,
    val timerId: Int,
    val immersion: Int,
    val comment: String
)