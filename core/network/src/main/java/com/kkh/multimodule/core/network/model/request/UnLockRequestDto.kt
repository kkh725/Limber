package com.kkh.multimodule.core.network.model.request

data class UnLockRequestDto(
    val timerId : Int,
    val failReason : String
)

