package com.kkh.multimodule.core.network.model.request

data class HistoryRequestDto(
    val userId: String,
    val searchRange : String = "ALL",
    val onlyIncompleteRetrospect : Boolean = false
)
