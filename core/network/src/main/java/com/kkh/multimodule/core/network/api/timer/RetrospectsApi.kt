package com.kkh.multimodule.core.network.api.timer

import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.SingleTimerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrospectsApi {
    /**
     * 타이머 회고 저장
     */
    @POST("/api/timer-retrospects")
    suspend fun writeRetrospects(
        @Body retrospectsDto : RetrospectsRequestDto
    ): Response<BaseResponse>

    /**
     * 타이머 회고 삭제
     */
    @DELETE("/api/timer-retrospects/{timerRetrospectsId}")
    suspend fun deleteRetrospects(
        @Path ("timerRetrospectsId") timerRetrospectsId: Int
    ): Response<BaseResponse>

}