package com.kkh.multimodule.core.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
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
import javax.inject.Inject
import com.kkh.multimodule.core.network.model.response.processApiResponse
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

            historyDataSource.getHistoryList(requestModel).processApiResponse()
                .toDomain(getOrCreateUUID())
        }

    /**
     * 회고 여부가 포함된 타이머 이력 조회
     */
    override suspend fun getHistoryListWithRetrospects(): Result<List<HistoryWithRetrospectsModel>> {
        return runCatching {
            historyDataSource.getHistoryListWithRetrospects(getOrCreateUUID()).processApiResponse()
                .toDomain()
        }
    }

    /**
     * 가장 최근 이력 조회
     */
    override suspend fun getLatestHistoryId(
        timerId: Int
    ): Result<LatestTimerHistoryModel> =
        runCatching {
            historyDataSource.getLatestHistoryId(getOrCreateUUID(), timerId).processApiResponse()
                .toDomain()
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

            historyDataSource.getTotalImmersion(request).processApiResponse().toDomain()
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

            historyDataSource.getTotalActual(request).processApiResponse().toDomain()
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

            historyDataSource.getImmersionByWeekend(request).processApiResponse()
                .map { it.toDomain() }
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

            historyDataSource.getFocusDistribution(request).processApiResponse()
                .map { it.toDomain() }
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
            historyDataSource.getActualByWeekend(request).processApiResponse().map { it.toDomain() }
        }

    // uuid 생성 코드
    @SuppressLint("HardwareIds")
    private fun getOrCreateUUID(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}