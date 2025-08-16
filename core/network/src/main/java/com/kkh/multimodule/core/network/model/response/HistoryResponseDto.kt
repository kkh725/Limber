package com.kkh.multimodule.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class HistoryResponseDto(
    val id: Int,
    val timerId: Int,
    val userId: String,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val historyDt: String,
    val historyStatus: String,
    val failReason: String,
    val startTime: String,
    val endTime: String
){
    fun toDomain(): com.kkh.multimodule.core.domain.model.HistoryModel {

    }
}

typealias HistoryResponse = ApiResponse<List<HistoryResponseDto>>