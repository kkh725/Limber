package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import jakarta.inject.Inject

class TimerRepositoryImpl @Inject constructor(private val timerDataSource: TimerDataSource) {
    suspend fun reserveTimer(request : SingleTimerStatusDto) : Result<SingleTimerResponse>{
        return runCatching {
            timerDataSource.reserveTimer(request)
        }
    }
}