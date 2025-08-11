package com.kkh.multimodule.core.network.api.timer

import com.kkh.multimodule.core.network.model.CurrentTimerStatusResponse
import com.kkh.multimodule.core.network.model.SingleTimerStatusDto
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import com.kkh.multimodule.core.network.model.response.TimerListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TimerApi {
    /**
     * 타이머 예약 및 즉시 시작
     */
    @POST("/api/timers")
    suspend fun reserveTimer(
        @Body singleTimerRequest : SingleTimerStatusDto
    ): SingleTimerResponse

    /**
     * 타이머 상태 조회
     */
    @GET("/api/timers/{timerId}/status")
    suspend fun getCurrentTimerStatus(
        @Path("timerId") timerId : Int
    ): CurrentTimerStatusResponse

    /**
     * 단일 타이머 조회
     */
    @GET("/api/timers/{timerId}")
    suspend fun getSingleTimer(
        @Path("timerId") timerId : Int
    ): SingleTimerResponse

    /**
     * 타이머 목록 조회
     */
    @GET("/api/timers/user/{userId}")
    suspend fun getTimerList(
        @Path("userId") userId : String
    ): TimerListResponse

}