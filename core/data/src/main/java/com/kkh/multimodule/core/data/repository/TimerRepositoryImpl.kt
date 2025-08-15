package com.kkh.multimodule.core.data.repository

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.data.mapper.toDomainList
import com.kkh.multimodule.core.data.mapper.toDto
import com.kkh.multimodule.core.data.mapper.toRequestDto
import com.kkh.multimodule.core.domain.TimerCode
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.DeleteTimerListRequestModel
import com.kkh.multimodule.core.domain.model.PatchTimerModel
import com.kkh.multimodule.core.domain.model.RetrospectsRequestModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.domain.model.TimerListModel
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import com.kkh.multimodule.core.network.model.request.DeleteTimerRequestDto
import jakarta.inject.Inject

class TimerRepositoryImpl @Inject constructor(private val timerDataSource: TimerDataSource) :
    TimerRepository {

    /**
     * 타이머 지금시작
     */
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


    /**
     * 타이머 예약하기
     */
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

    /**
     * 타이머 현재 상태. (진행중, 레디 등)
     */
    override suspend fun getCurrentTimerStatus(timerId: Int): Result<TimerStatusModel> =
        runCatching {
            val response = timerDataSource.getCurrentTimerStatus(timerId)
            if (response.success) {
                response.data?.toDomain() ?: TimerStatusModel.OFF
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    /**
     * 타이머 싱글?
     */
    override suspend fun getSingleTimer(timerId: Int): Result<SingleTimerModel> =
        runCatching {
            val response = timerDataSource.getSingleTimer(timerId)
            if (response.success) {
                response.data?.toDomain() ?: SingleTimerModel.empty
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    /**
     * 타이머 리스트 get
     */
    override suspend fun getTimerList(userId: String): Result<TimerListModel> =
        runCatching {
            val response = timerDataSource.getTimerList(userId)
            if (response.success) {
                response.data?.toDomainList() ?: emptyList()
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    /**
     * 타이머 상태 변경 토글
     */
    override suspend fun patchTimerStatus(timerId: Int, status: PatchTimerModel): Result<Unit> =
        runCatching {
            val response = timerDataSource.patchTimerStatus(timerId, status.toDto())
            if (response.success) {
                response.data
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    /**
     * 타이머 삭제
     */

    override suspend fun deleteTimerList(timerIdList: List<Int>): Result<Unit> =
        runCatching {
            val response =
                timerDataSource.deleteTimer(timerIdList)
            if (response.success) {
                response.data
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }

    /**
     * 타이머 회고 작성
     */
    override suspend fun writeRetrospects(requestModel: RetrospectsRequestModel): Result<Unit> =
        runCatching {
            val response = timerDataSource.writeRetrospects(requestModel.toDto())
            if (response.success) {
                response.data
            } else {
                throw Exception(response.error?.message ?: "Unknown error")
            }
        }
}

