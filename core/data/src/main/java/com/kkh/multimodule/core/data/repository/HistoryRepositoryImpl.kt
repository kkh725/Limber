package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.data.error.TimerApiException
import com.kkh.multimodule.core.data.error.TimerError
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.domain.model.history.ActualByWeekendModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.HistoryModel
import com.kkh.multimodule.core.domain.model.history.ImmersionByWeekdayModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.domain.model.history.TotalActualModel
import com.kkh.multimodule.core.domain.model.history.TotalImmersionModel
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.network.datasource.history.HistoryDataSource
import com.kkh.multimodule.core.network.model.request.HistoryRequestDto
import com.kkh.multimodule.core.network.model.request.history.TotalImmersionRequestDto
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyDataSource: HistoryDataSource) :
    HistoryRepository {
    /**
     * 타이머 이력 조회
     */
    override suspend fun getHistoryList(userId: String): Result<List<HistoryModel>> =
        runCatching {
            val requestModel = HistoryRequestDto(userId = userId)
            val response = historyDataSource.getHistoryList(requestModel)
            if (response.success) {
                response.data?.toDomain() ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 가장 최근 이력 조회
     */
    override suspend fun getLatestHistoryId(
        userId: String,
        timerId: Int
    ): Result<LatestTimerHistoryModel> =
        runCatching {
            val response = historyDataSource.getLatestHistoryId(userId, timerId)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 전체 몰입도
     */
    override suspend fun getTotalImmersion(
        userId: String,
        startTime: String,
        endTime: String
    ): Result<TotalImmersionModel> =
        runCatching {
            val request = TotalImmersionRequestDto(userId, startTime, endTime)

            val response = historyDataSource.getTotalImmersion(request)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 전체 총 실험시간
     */
    override suspend fun getTotalActual(
        userId: String,
        startTime: String,
        endTime: String
    ): Result<TotalActualModel> =
        runCatching {
            val request = TotalImmersionRequestDto(userId, startTime, endTime)
            val response = historyDataSource.getTotalActual(request)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 요일 별 총 몰입도
     */
    override suspend fun getImmersionByWeekend(
        userId: String,
        startTime: String,
        endTime: String
    ): Result<List<ImmersionByWeekdayModel>> =
        runCatching {
            val request = TotalImmersionRequestDto(userId, startTime, endTime)
            val response = historyDataSource.getImmersionByWeekend(request)
            if (response.success) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 몰입 유형 별 실제 시간 합
     */
    override suspend fun getFocusDistribution(
        userId: String,
        startTime: String,
        endTime: String
    ): Result<List<FocusDistributionModel>> =
        runCatching {
            val request = TotalImmersionRequestDto(userId, startTime, endTime)
            val response = historyDataSource.getFocusDistribution(request)
            if (response.success) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 요일 별 실험 시간
     */
    override suspend fun getActualByWeekend(
        userId: String,
        startTime: String,
        endTime: String
    ): Result<List<ActualByWeekendModel>> =
        runCatching {
            val request = TotalImmersionRequestDto(userId, startTime, endTime)
            val response = historyDataSource.getActualByWeekend(request)
            if (response.success) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }
}