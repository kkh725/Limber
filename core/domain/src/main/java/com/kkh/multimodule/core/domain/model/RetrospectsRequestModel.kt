package com.kkh.multimodule.core.domain.model

data class RetrospectsRequestModel(
    val userId: String,
    val timerHistoryId: Int,
    val timerId: Int,
    val immersion: Int,
    val comment: String
)