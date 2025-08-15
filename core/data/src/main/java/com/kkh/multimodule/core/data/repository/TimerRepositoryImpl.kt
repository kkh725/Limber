package com.kkh.multimodule.core.data.repository

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.data.mapper.toDomainList
import com.kkh.multimodule.core.data.mapper.toDto
import com.kkh.multimodule.core.data.mapper.toRequestDto
import com.kkh.multimodule.core.domain.TimerCode
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.domain.model.TimerListModel
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import jakarta.inject.Inject

class TimerRepositoryImpl @Inject constructor(private val timerDataSource: TimerDataSource) :
    TimerRepository {

    // 지금 시작 api 전송
    override suspend fun reserveImmediateTimer(request: SingleTimerModel): Result<SingleTimerModel> {
        return runCatching {
            // 블록 안 마지막 식이 runCatching의 반환값이 됨
            val response =
                timerDataSource.reserveTimer(
                    request.toRequestDto(
                        userId = "UUID!@",
                        TimerCode.IMMEDIATE.text
                    )
                )
            if (response.success) {
                response.data?.toDomain() ?: SingleTimerModel.empty
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }
    }


    // 예약 Timer 전송
    override suspend fun reserveScheduledTimer(request: SingleTimerModel): Result<SingleTimerModel> {
        return runCatching {
            // 블록 안 마지막 식이 runCatching의 반환값이 됨
            val response =
                timerDataSource.reserveTimer(
                    request.toRequestDto(
                        userId = "UUID!@",
                        TimerCode.SCHEDULED.text
                    )
                )
            if (response.success) {
                response.data?.toDomain() ?: SingleTimerModel.empty
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }
    }


    override suspend fun getCurrentTimerStatus(timerId: Int): Result<TimerStatusModel> =
        runCatching {
            val response = timerDataSource.getCurrentTimerStatus(timerId)
            if (response.success) {
                response.data?.toDomain() ?: TimerStatusModel.OFF
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    override suspend fun getSingleTimer(timerId: Int): Result<SingleTimerModel> =
        runCatching {
            val response = timerDataSource.getSingleTimer(timerId)
            if (response.success) {
                response.data?.toDomain() ?: SingleTimerModel.empty
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }


    override suspend fun getTimerList(userId: String): Result<TimerListModel> =
        runCatching {
            val response = timerDataSource.getTimerList(userId)
            if (response.success) {
                response.data?.toDomainList() ?: emptyList()
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }
}

