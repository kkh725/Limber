package com.kkh.multimodule.core.network.datasource.timer

import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import retrofit2.Response

interface TimerDataSource {
    suspend fun reserveTimer(request: SingleTimerStatusDto): SingleTimerResponse
    suspend fun getCurrentTimerStatus(timerId: Int): CurrentTimerStatusResponse
    suspend fun getSingleTimer(timerId: Int): SingleTimerResponse
    suspend fun getTimerList(userId: String): TimerListResponse
}
