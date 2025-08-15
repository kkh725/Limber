package com.kkh.multimodule.core.data.mapper

import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.PatchTimerModel
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.TimerStatus
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequest

/**
 * singleTimer
 */
fun SingleTimerRequestDto.toDomain() = SingleTimerModel(
    id = -1,
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime,
    endTime = endTime,
    status = TimerStatusModel.OFF
)

fun SingleTimerModel.toRequestDto(userId: String, timerCode: String) = SingleTimerRequestDto(
    userId = userId,
    title = title,
    timerCode = timerCode,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime,
    endTime = endTime
)

fun SingleTimerStatusDto.toDomain() = SingleTimerModel(
    id = id,
    title = title,
    focusTypeId = focusTypeId,
    repeatCycleCode = repeatCycleCode,
    repeatDays = repeatDays,
    startTime = startTime,
    endTime = endTime,
    status = status.toDomain()
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

fun SingleTimerModel.toReservationItemModel() = ReservationItemModel(
    id = id,
    reservationInfo = ReservationInfo(
        title = title,
        startTime = startTime,
        endTime = endTime,
        category = focusTypeIdToCategory(focusTypeId),
        repeatDays = repeatDaysToString(repeatDays),
        repeatOption = repeatCycleCodeToString(repeatCycleCode)
    ),
    isToggleChecked = status == TimerStatusModel.ON
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

fun List<SingleTimerModel>.toReservationItemModelList(): List<ReservationItemModel> =
    map { it.toReservationItemModel() }

/**
 * timerstatus 토글변경
 */

fun PatchTimerStatusRequest.toDomain(): PatchTimerModel =
    PatchTimerModel(status = this.status)

fun PatchTimerModel.toDto(): PatchTimerStatusRequest =
    PatchTimerStatusRequest(status = this.status)
