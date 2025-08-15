package com.kkh.multimodule.core.network.datasource.timer

import com.kkh.multimodule.core.network.api.timer.TimerApi
import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequest
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import jakarta.inject.Inject
import retrofit2.Response
import kotlin.concurrent.timer

internal class TimerDataSourceImpl @Inject constructor(
    private val timerApi: TimerApi
) : TimerDataSource {

    override suspend fun reserveTimer(request: SingleTimerRequestDto): SingleTimerResponse {
        return timerApi.reserveTimer(request)
    }

    override suspend fun getCurrentTimerStatus(timerId: Int): CurrentTimerStatusResponse {
        return timerApi.getCurrentTimerStatus(timerId)
    }

    override suspend fun getSingleTimer(timerId: Int): SingleTimerResponse {
        return timerApi.getSingleTimer(timerId)
    }

    override suspend fun getTimerList(userId: String): TimerListResponse {
        return timerApi.getTimerList(userId)
    }

    override suspend fun patchTimerStatus(timerId: Int, status: PatchTimerStatusRequest) : BaseResponse {
        return timerApi.patchTimerStatus(timerId, patchTimerStatusRequest = status)
    }

    override suspend fun deleteTimer(timerId: Int): BaseResponse {
        return timerApi.deleteTimerStatus(timerId)

    }
}
