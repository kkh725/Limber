package com.kkh.multimodule.core.domain.model

import com.kkh.multimodule.core.domain.TimerStatusModel


data class SingleTimerModel(
    val id: Int,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val startTime: String,
    val endTime: String,
    val status: TimerStatusModel
){
    companion object{
        val empty = SingleTimerModel(
            id = -1,
            title = "",
            focusTypeId = 1,
            repeatCycleCode = "",
            repeatDays = "",
            startTime = "",
            endTime = "",
            status = TimerStatusModel.READY
        )
    }
}

typealias TimerListModel = List<SingleTimerModel>

