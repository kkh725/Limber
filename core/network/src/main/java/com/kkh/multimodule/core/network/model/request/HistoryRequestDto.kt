package com.kkh.multimodule.core.network.model.request

data class HistoryRequestDto(
    val userId: String,
    val searchRange : String = SearchRange.WEEKLY.name,
    val onlyIncompleteRetrospect : Boolean = false
){
    enum class SearchRange{
        WEEKLY, ALL
    }
}
