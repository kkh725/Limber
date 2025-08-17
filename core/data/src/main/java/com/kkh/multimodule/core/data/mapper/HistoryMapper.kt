package com.kkh.multimodule.core.data.mapper

import com.kkh.multimodule.core.network.model.response.history.TotalImmersionResponseDto
import com.kkh.multimodule.core.network.model.response.history.TotalActualResponseDto
import com.kkh.multimodule.core.network.model.response.history.ImmersionByWeekdayResponseDto
import com.kkh.multimodule.core.network.model.response.history.FocusDistributionResponseDto
import com.kkh.multimodule.core.network.model.response.history.ActualByWeekendResponseDto
import com.kkh.multimodule.core.network.model.response.history.HistoryWithRetrospectsResponseDto
import com.kkh.multimodule.core.network.model.response.ApiResponse

import com.kkh.multimodule.core.domain.model.history.TotalImmersionModel
import com.kkh.multimodule.core.domain.model.history.TotalActualModel
import com.kkh.multimodule.core.domain.model.history.ImmersionByWeekdayModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.ActualByWeekendModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.domain.model.history.HistoryWithRetrospectsModel
import com.kkh.multimodule.core.network.model.response.history.LatestTimerHistoryDto
import kotlin.Boolean

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
        focusTypeId = this.focusTypeId,
        focusTypeName = this.focusTypeName,
        totalActualMinutes = this.totalActualMinutes,
    )

fun ActualByWeekendResponseDto.toDomain(): ActualByWeekendModel =
    ActualByWeekendModel(
        weekdayIndex = this.weekdayIndex,
        dayOfWeek = this.dayOfWeek,
        totalActualMinutes = this.totalActualMinutes
    )

fun LatestTimerHistoryDto.toDomain(): LatestTimerHistoryModel =
    LatestTimerHistoryModel(
        id = this.id,
        timerId = this.timerId,
        userId = this.userId,
        title = this.title,
        focusTypeId = this.focusTypeId,
        repeatCycleCode = this.repeatCycleCode,
        repeatDays = this.repeatDays,
        historyDt = this.historyDt,
        historyStatus = this.historyStatus,
        failReason = this.failReason,
        startTime = this.startTime,
        endTime = this.endTime,
        hasRetrospect = this.hasRetrospect,
        retrospectId = this.retrospectId,
        retrospectImmersion = this.retrospectImmersion,
        retrospectComment = this.retrospectComment,
        focusTypeTitle = this.focusTypeTitle,
        retrospectSummary = this.retrospectSummary
    )

fun HistoryWithRetrospectsResponseDto.toDomain(): HistoryWithRetrospectsModel =
    HistoryWithRetrospectsModel(
        weekStart = this.weekStart,
        weekEnd = this.weekEnd,
        items = this.items.map { it.toDomain() }
    )

fun List<HistoryWithRetrospectsResponseDto>.toDomain(): List<HistoryWithRetrospectsModel> =
    this.map { it.toDomain() }

fun ApiResponse<List<HistoryWithRetrospectsResponseDto>>.toDomain(): List<HistoryWithRetrospectsModel> =
    this.data?.map { it.toDomain() } ?: emptyList()
