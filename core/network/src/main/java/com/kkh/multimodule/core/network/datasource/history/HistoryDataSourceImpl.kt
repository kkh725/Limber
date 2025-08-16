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
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.HistoryResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import jakarta.inject.Inject

internal class HistoryDataSourceImpl @Inject constructor(
    private val historyApi: HistoryApi
) : HistoryDataSource {
    override suspend fun getHistoryList(historyRequestDto: HistoryRequestDto): HistoryResponse {
        return historyApi.getTimerHistories(historyRequestDto)
    }
}
