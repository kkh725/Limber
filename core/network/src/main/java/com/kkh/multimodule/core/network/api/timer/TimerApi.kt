package com.kkh.multimodule.core.network.api.timer

import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerRequestDto
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.request.DeleteTimerRequestDto
import com.kkh.multimodule.core.network.model.request.PatchTimerStatusRequestDto
import com.kkh.multimodule.core.network.model.request.UnLockRequestDto
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TimerApi {
    /**
     * 타이머 예약 및 즉시 시작
     */
    @POST("/api/timers")
    suspend fun reserveTimer(
        @Body singleTimerRequest : SingleTimerRequestDto
    ): Response<SingleTimerResponse>

    /**
     * 타이머 잠금 해제[]
     */
    @POST("/api/timers/unlock")
    suspend fun unlockTimer(
        @Body unLockRequestDto: UnLockRequestDto
    ): Response<BaseResponse>

    /**
     * 타이머 상태 조회
     */
    @GET("/api/timers/{timerId}/status")
    suspend fun getCurrentTimerStatus(
        @Path("timerId") timerId : Int
    ): Response<CurrentTimerStatusResponse>

    /**
     * 단일 타이머 조회
     */
    @GET("/api/timers/{timerId}")
    suspend fun getSingleTimer(
        @Path("timerId") timerId : Int
    ): Response<SingleTimerResponse>

    /**
     * 타이머 목록 조회
     */
    @GET("/api/timers/user/{userId}")
    suspend fun getTimerList(
        @Path("userId") userId : String
    ): Response<TimerListResponse>

    /**
     * 타이머 상태 변경 toggle on off
     */
    @PATCH("/api/timers/{timerId}/status")
    suspend fun patchTimerStatus(
        @Path("timerId") timerId : Int,
        @Body patchTimerStatusRequestDto: PatchTimerStatusRequestDto
    ): Response<BaseResponse>

    /**
     * 타이머 삭제 204 no content 이기때문에 Unit으로
     */
    @HTTP(method = "DELETE", path = "/api/timers", hasBody = true)
    suspend fun deleteTimerList(
        @Body timerIdList: DeleteTimerRequestDto
    ): Response<Unit>

}