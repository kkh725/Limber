package com.kkh.multimodule.core.data.mapper

import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.TimerStatus

/**
 * singleTimer
 */
fun SingleTimerRequestDto.toDomain() = SingleTimerModel(
    id = -1,
    title= title,
    focusTypeId= focusTypeId,
    repeatCycleCode= repeatCycleCode,
    repeatDays= repeatDays,
    startTime= startTime,
    endTime= endTime,
    status= TimerStatusModel.READY
)

fun SingleTimerModel.toRequestDto() = SingleTimerRequestDto(
    userid = "UUID",
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime,
    endTime = endTime
)

fun SingleTimerStatusDto.toDomain() = SingleTimerModel(
    id = id,
    title= title,
    focusTypeId= focusTypeId,
    repeatCycleCode= repeatCycleCode,
    repeatDays= repeatDays,
    startTime= startTime,
    endTime= endTime,
    status= status.toDomain()
)

fun SingleTimerModel.toDto() = SingleTimerStatusDto(
    id = id,
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime,
    endTime = endTime,
    status = status.toDto()
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