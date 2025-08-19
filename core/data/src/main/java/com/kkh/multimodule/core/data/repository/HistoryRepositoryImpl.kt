package com.kkh.multimodule.core.data.repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import com.kkh.multimodule.core.data.error.TimerApiException
import com.kkh.multimodule.core.data.error.TimerError
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.domain.model.history.ActualByWeekendModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.HistoryModel
import com.kkh.multimodule.core.domain.model.history.HistoryWithRetrospectsModel
import com.kkh.multimodule.core.domain.model.history.ImmersionByWeekdayModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.domain.model.history.TotalActualModel
import com.kkh.multimodule.core.domain.model.history.TotalImmersionModel
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.network.datasource.history.HistoryDataSource
import com.kkh.multimodule.core.network.model.request.HistoryRequestDto
import com.kkh.multimodule.core.network.model.request.history.TotalImmersionRequestDto
import java.util.UUID
import javax.inject.Inject
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    @ApplicationContext private val context: Context
) :
    HistoryRepository {
    /**
     * 타이머 이력 조회
     */
    override suspend fun getHistoryList(): Result<List<HistoryModel>> =
        runCatching {
            val requestModel = HistoryRequestDto(userId = getOrCreateUUID())
            val response = historyDataSource.getHistoryList(requestModel)
            if (response.success) {
                response.data?.toDomain(getOrCreateUUID()) ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 회고 여부가 포함된 타이머 이력 조회
     */
    override suspend fun getHistoryListWithRetrospects(): Result<List<HistoryWithRetrospectsModel>> {
        return runCatching {
            val response =
                historyDataSource.getHistoryListWithRetrospects(getOrCreateUUID())
            if (response.success) {
                response.data?.toDomain() ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }
    }

    /**
     * 가장 최근 이력 조회
     */
    override suspend fun getLatestHistoryId(
        timerId: Int
    ): Result<LatestTimerHistoryModel> =
        runCatching {
            val response =
                historyDataSource.getLatestHistoryId(getOrCreateUUID(), timerId)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                Log.e(TAG, "code : ${response.error?.code}, message :${response.error?.message}", )
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 전체 몰입도
     */
    override suspend fun getTotalImmersion(
        startTime: String,
        endTime: String
    ): Result<TotalImmersionModel> =
        runCatching {
            val request =
                TotalImmersionRequestDto(getOrCreateUUID(), startTime, endTime)

            val response = historyDataSource.getTotalImmersion(request)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                Log.e(TAG, "code : ${response.error?.code}, message :${response.error?.message}", )
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 전체 총 실험시간
     */
    override suspend fun getTotalActual(
        startTime: String,
        endTime: String
    ): Result<TotalActualModel> =
        runCatching {
            val request =
                TotalImmersionRequestDto(getOrCreateUUID(), startTime, endTime)
            val response = historyDataSource.getTotalActual(request)
            if (response.success) {
                response.data?.toDomain() ?: throw Exception("Unknown")
            } else {
                Log.e(TAG, "code : ${response.error?.code}, message :${response.error?.message}", )
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 요일 별 총 몰입도
     */
    override suspend fun getImmersionByWeekend(
        startTime: String,
        endTime: String
    ): Result<List<ImmersionByWeekdayModel>> =
        runCatching {
            val request =
                TotalImmersionRequestDto(getOrCreateUUID(), startTime, endTime)
            val response = historyDataSource.getImmersionByWeekend(request)
            if (response.success) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                Log.e(TAG, "code : ${response.error?.code}, message :${response.error?.message}", )
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    /**
     * 몰입 유형 별 실제 시간 합
     */
    override suspend fun getFocusDistribution(
        startTime: String,
        endTime: String
    ): Result<List<FocusDistributionModel>> =
        runCatching {
            val request =
                TotalImmersionRequestDto(getOrCreateUUID(), startTime, endTime)
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
        startTime: String,
        endTime: String
    ): Result<List<ActualByWeekendModel>> =
        runCatching {
            val request =
                TotalImmersionRequestDto(getOrCreateUUID(), startTime, endTime)
            val response = historyDataSource.getActualByWeekend(request)
            if (response.success) {
                response.data?.map { it.toDomain() } ?: emptyList()
            } else {
                val error = TimerError.from(response.error?.code, response.error?.message)
                throw TimerApiException(error)
            }
        }

    @SuppressLint("HardwareIds")
    private fun getOrCreateUUID(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}