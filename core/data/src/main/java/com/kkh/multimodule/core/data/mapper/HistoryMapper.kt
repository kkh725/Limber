package com.kkh.multimodule.core.data.mapper

import com.kkh.multimodule.core.network.model.response.history.TotalImmersionResponseDto
import com.kkh.multimodule.core.network.model.response.history.TotalActualResponseDto
import com.kkh.multimodule.core.network.model.response.history.ImmersionByWeekdayResponseDto
import com.kkh.multimodule.core.network.model.response.history.FocusDistributionResponseDto
import com.kkh.multimodule.core.network.model.response.history.ActualByWeekendResponseDto

import com.kkh.multimodule.core.domain.model.history.TotalImmersionModel
import com.kkh.multimodule.core.domain.model.history.TotalActualModel
import com.kkh.multimodule.core.domain.model.history.ImmersionByWeekdayModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.ActualByWeekendModel

fun TotalImmersionResponseDto.toDomain(): TotalImmersionModel =
    TotalImmersionModel(
        totalActualMinutes = this.totalActualMinutes,
        totalScheduledMinutes = this.totalScheduledMinutes,
        ratio = this.ratio
    )

fun TotalActualResponseDto.toDomain(): TotalActualModel =
    TotalActualModel(
        totalMinutes = this.totalMinutes,
        label = this.label
    )

fun ImmersionByWeekdayResponseDto.toDomain(): ImmersionByWeekdayModel =
    ImmersionByWeekdayModel(
        weekdayIndex = this.weekdayIndex,
        dayOfWeek = this.dayOfWeek,
        totalActualMinutes = this.totalActualMinutes,
        totalScheduledMinutes = this.totalScheduledMinutes,
        ratio = this.ratio
    )

fun FocusDistributionResponseDto.toDomain(): FocusDistributionModel =
    FocusDistributionModel(
        repeatCycleCode = this.repeatCycleCode,
        totalActualMinutes = this.totalActualMinutes
    )

fun ActualByWeekendResponseDto.toDomain(): ActualByWeekendModel =
    ActualByWeekendModel(
        weekdayIndex = this.weekdayIndex,
        dayOfWeek = this.dayOfWeek,
        totalActualMinutes = this.totalActualMinutes
    )
