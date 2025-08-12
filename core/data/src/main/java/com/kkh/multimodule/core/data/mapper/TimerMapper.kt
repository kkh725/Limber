package com.kkh.multimodule.core.data.mapper

import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto.TimeData
import com.kkh.multimodule.core.network.model.TimerStatus

/**
 * singleTimer
 */
fun SingleTimerStatusDto.toDomain() = SingleTimerModel(
    id = id,
    title= title,
    focusTypeId= focusTypeId,
    repeatCycleCode= repeatCycleCode,
    repeatDays= repeatDays,
    startTime= startTime.toDomain(),
    endTime= endTime.toDomain(),
    status= status.toDomain()
)

fun TimeData.toDomain() = SingleTimerModel.TimeModel(
    minute = minute,
    hour = hour
)

fun SingleTimerModel.toDto() = SingleTimerStatusDto(
    id = id,
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime.toDto(),
    endTime = endTime.toDto(),
    status = status.toDto()
)

fun SingleTimerModel.TimeModel.toDto() = TimeData(
    minute = minute,
    hour = hour
)

/**
 * timerStatus
 */

// Mapper
fun TimerStatus.toDomain(): TimerStatusModel =
    TimerStatusModel.valueOf(this.name)

fun TimerStatusModel.toDto(): TimerStatus =
    TimerStatus.valueOf(this.name)

/**
 * timerList
 */

// List 변환 확장 함수
fun List<SingleTimerStatusDto>.toDomainList(): List<SingleTimerModel> =
    map { it.toDomain() }

fun List<SingleTimerModel>.toDtoList(): List<SingleTimerStatusDto> =
    map { it.toDto() }