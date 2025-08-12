package com.kkh.multimodule.core.domain.repository

import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel

interface TimerRepository {
    suspend fun reserveTimer(request : SingleTimerModel) : Result<SingleTimerModel>
    suspend fun getCurrentTimerStatus(timerId: Int): Result<TimerStatusModel>
    suspend fun getSingleTimer(timerId: Int): Result<SingleTimerModel>
    suspend fun getTimerList(userId: String): Result<List<SingleTimerModel>>
}