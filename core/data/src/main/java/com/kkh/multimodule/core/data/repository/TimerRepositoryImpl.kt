package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import jakarta.inject.Inject
import com.kkh.multimodule.core.domain.model.SingleTimerStatus

class TimerRepositoryImpl @Inject constructor(private val timerDataSource: TimerDataSource) :
    TimerRepository {
    suspend fun reserveTimer(request : SingleTimerStatusDto) : Result<SingleTimerResponse>{
        return runCatching {
            timerDataSource.reserveTimer(request)
        }
    }
}

fun SingleTimerStatusDto.toDomain(): SingleTimerStatus = SingleTimerStatus(
    id = id,
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime.toDomain(),
    endTime = endTime.toDomain(),
    status = status
)

fun SingleTimerStatusDto.TimeData.toDomain(): SingleTimerStatus.TimeData =
    SingleTimerStatus.TimeData(
        hour = hour,
        minute = minute,
        second = second,
        nano = nano
    )