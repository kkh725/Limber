package com.kkh.multimodule.core.network.datasource.timer

import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.request.DeleteTimerRequestDto
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequestDto
import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import retrofit2.Response

interface TimerDataSource {
    suspend fun reserveTimer(request: SingleTimerRequestDto): Response<SingleTimerResponse>
    suspend fun unlockTimer(timerId: Int, failReason : String) : Response<BaseResponse>
    suspend fun getCurrentTimerStatus(timerId: Int): Response<CurrentTimerStatusResponse>
    suspend fun getSingleTimer(timerId: Int): Response<SingleTimerResponse>
    suspend fun getTimerList(userId: String): Response<TimerListResponse>
    suspend fun patchTimerStatus(timerId: Int, status: PatchTimerStatusRequestDto) : Response<BaseResponse>
    suspend fun deleteTimer(timerIdList: List<Int>): Response<Unit>
    suspend fun writeRetrospects(request : RetrospectsRequestDto): Response<BaseResponse>
}
