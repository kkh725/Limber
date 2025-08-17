package com.kkh.multimodule.core.network.model.request

data class HistoryRequestDto(
    val userId: String,
    val searchRange : String = "WEEKLY",
    val onlyIncompleteRetrospect : Boolean = false
)
