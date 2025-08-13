package com.kkh.multimodule.core.domain.model

import com.kkh.multimodule.core.domain.RepeatCycleCodeModel
import com.kkh.multimodule.core.domain.TimerStatusModel


data class SingleTimerModel(
    val id: Int = -1,
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
            repeatCycleCode = RepeatCycleCodeModel.NONE.code,
            repeatDays = "",
            startTime = "",
            endTime = "",
            status = TimerStatusModel.OFF
        )
    }
}

typealias TimerListModel = List<SingleTimerModel>

