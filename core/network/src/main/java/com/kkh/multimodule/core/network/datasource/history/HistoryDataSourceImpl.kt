package com.kkh.multimodule.core.network.datasource.history

import com.kkh.multimodule.core.network.api.timer.HistoryApi
import com.kkh.multimodule.core.network.api.timer.RetrospectsApi
import com.kkh.multimodule.core.network.api.timer.TimerApi
import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.request.DeleteTimerRequestDto
import com.kkh.multimodule.core.network.model.request.HistoryRequestDto
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequest
import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.request.history.TotalImmersionRequestDto
import com.kkh.multimodule.core.network.model.response.ApiResponse
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.HistoryResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import com.kkh.multimodule.core.network.model.response.history.ActualByWeekendResponse
import com.kkh.multimodule.core.network.model.response.history.FocusDistributionResponse
import com.kkh.multimodule.core.network.model.response.history.HistoryWithRetrospectsResponse
import com.kkh.multimodule.core.network.model.response.history.ImmersionByWeekdayResponse
import com.kkh.multimodule.core.network.model.response.history.LatestTimerResponse
import com.kkh.multimodule.core.network.model.response.history.TotalActualResponseDto
import com.kkh.multimodule.core.network.model.response.history.TotalImmersionResponseDto
import jakarta.inject.Inject

internal class HistoryDataSourceImpl @Inject constructor(
    private val historyApi: HistoryApi
) : HistoryDataSource {
    /**
     * 이력 조회
     */
    override suspend fun getHistoryList(historyRequestDto: HistoryRequestDto): HistoryResponse {
        return historyApi.getTimerHistories(historyRequestDto)
    }

    /**
     * 이력 조회 회고 여부 포함
     */
    override suspend fun getHistoryListWithRetrospects(historyRequestDto: HistoryRequestDto): HistoryWithRetrospectsResponse {
        return historyApi.getTimerHistoriesWithRetrospect(historyRequestDto)
    }

    /**
     * 특정 이력 조회
     */
    override suspend fun getLatestHistoryId(userId : String, timerId : Int): LatestTimerResponse {
        return historyApi.getTimerLatestId(userId, timerId)
    }

    /**
     * 전체 몰입도 조회
     */
    override suspend fun getTotalImmersion(totalImmersionRequestDto: TotalImmersionRequestDto): ApiResponse<TotalImmersionResponseDto> {
        return historyApi.getTotalImmersion(totalImmersionRequestDto)
    }

    /**
     * 전체 실험시간 조회
     */
    override suspend fun getTotalActual(totalImmersionRequestDto: TotalImmersionRequestDto): ApiResponse<TotalActualResponseDto> {
        return historyApi.getTotalActual(totalImmersionRequestDto)
    }

    /**
     * 요일별 총 몰입도
     */
    override suspend fun getImmersionByWeekend(totalImmersionRequestDto: TotalImmersionRequestDto): ImmersionByWeekdayResponse {
        return historyApi.getImmersionByWeekend(totalImmersionRequestDto)
    }

    /**
     * 몰입 유형 별 시간 합
     */
    override suspend fun getFocusDistribution(totalImmersionRequestDto: TotalImmersionRequestDto): FocusDistributionResponse {
        return historyApi.getFocusDistribution(totalImmersionRequestDto)
    }

    /**
     * 요일 별 총 실험시간
     */
    override suspend fun getActualByWeekend(totalImmersionRequestDto: TotalImmersionRequestDto): ActualByWeekendResponse {
        return historyApi.getActualByWeekend(totalImmersionRequestDto)
    }
}
