package com.kkh.multimodule.core.domain.model

import com.kkh.multimodule.core.domain.TimerStatusModel

data class SingleTimerModel(
    val id: Int,
    val title: String,
    val focusTypeId: Int,
    val repeatCycleCode: String,
    val repeatDays: String,
    val startTime: TimeModel,
    val endTime: TimeModel,
    val status: TimerStatusModel
){
    data class TimeModel(
        val hour: Int,
        val minute: Int,
    )
    companion object{
        val empty = SingleTimerModel(
            id = -1,
            title = "",
            focusTypeId = -1,
            repeatCycleCode = "",
            repeatDays = "",
            startTime = TimeModel(0,0),
            endTime = TimeModel(0,0),
            status = TimerStatusModel.READY
        )
    }
}

typealias TimerListModel = List<SingleTimerModel>

