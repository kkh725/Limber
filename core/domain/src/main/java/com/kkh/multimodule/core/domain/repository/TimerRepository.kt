package com.kkh.multimodule.core.domain.repository

interface TimerRepository {
    suspend fun reserveTimer(request : SingleTimerStatusDto) : Result<SingleTimerResponse>
}