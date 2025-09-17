package com.kkh.multimodule.core.network.datasource.timer

import com.kkh.multimodule.core.network.api.timer.RetrospectsApi
import com.kkh.multimodule.core.network.api.timer.TimerApi
import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.request.DeleteTimerRequestDto
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequestDto
import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.request.UnLockRequestDto
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import jakarta.inject.Inject
import retrofit2.Response
import kotlin.concurrent.timer

class TimerDataSourceImpl @Inject constructor(
    private val timerApi: TimerApi,
    private val retrospectsApi: RetrospectsApi
) : TimerDataSource {

    override suspend fun reserveTimer(request: SingleTimerRequestDto): Response<SingleTimerResponse> {
        return timerApi.reserveTimer(request)
    }

    override suspend fun unlockTimer(timerId: Int, failReason: String): Response<BaseResponse> {
        val request = UnLockRequestDto(timerId, failReason)
        return timerApi.unlockTimer(request)
    }

    override suspend fun getCurrentTimerStatus(timerId: Int): Response<CurrentTimerStatusResponse> {
        return timerApi.getCurrentTimerStatus(timerId)
    }

    override suspend fun getSingleTimer(timerId: Int): Response<SingleTimerResponse> {
        return timerApi.getSingleTimer(timerId)
    }

    override suspend fun getTimerList(userId: String): Response<TimerListResponse> {
        return timerApi.getTimerList(userId)
    }

    override suspend fun patchTimerStatus(timerId: Int, status: PatchTimerStatusRequestDto) : Response<BaseResponse> {
        return timerApi.patchTimerStatus(timerId, patchTimerStatusRequestDto = status)
    }

    override suspend fun deleteTimer(timerIdList: List<Int>): Response<Unit> {
        return timerApi.deleteTimerList(DeleteTimerRequestDto(timerIdList))
    }

    override suspend fun writeRetrospects(request: RetrospectsRequestDto): Response<BaseResponse> {
        return retrospectsApi.writeRetrospects(request)
    }
}
