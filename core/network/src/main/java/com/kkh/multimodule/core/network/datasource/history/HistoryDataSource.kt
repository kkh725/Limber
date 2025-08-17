package com.kkh.multimodule.core.network.datasource.history

import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
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

interface HistoryDataSource {
    suspend fun getHistoryList(historyRequestDto: HistoryRequestDto): HistoryResponse
    suspend fun getHistoryListWithRetrospects(historyRequestDto: HistoryRequestDto): HistoryWithRetrospectsResponse
    suspend fun getLatestHistoryId(userId : String, timerId : Int) : LatestTimerResponse
    suspend fun getTotalImmersion(totalImmersionRequestDto: TotalImmersionRequestDto): ApiResponse<TotalImmersionResponseDto>
    suspend fun getTotalActual(totalImmersionRequestDto: TotalImmersionRequestDto): ApiResponse<TotalActualResponseDto>
    suspend fun getImmersionByWeekend(totalImmersionRequestDto: TotalImmersionRequestDto): ImmersionByWeekdayResponse
    suspend fun getFocusDistribution(totalImmersionRequestDto: TotalImmersionRequestDto): FocusDistributionResponse
    suspend fun getActualByWeekend(totalImmersionRequestDto: TotalImmersionRequestDto): ActualByWeekendResponse
}

