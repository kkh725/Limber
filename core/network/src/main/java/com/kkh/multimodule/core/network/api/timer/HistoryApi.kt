package com.kkh.multimodule.core.network.api.timer

import com.kkh.multimodule.core.network.model.request.HistoryRequestDto
import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.HistoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HistoryApi {

    /**
     * 타이머 이력 저장
     */
//    @POST("/api/timer-retrospects")
//    suspend fun writeRetrospects(
//        @Body retrospectsDto : RetrospectsRequestDto
//    ): BaseResponse

    /**
     * 타이머 이력 조회
     */
    @GET("/api/timer-histories/me")
    suspend fun getTimerHistories(
        @Body historyRequestDto: HistoryRequestDto
    ): HistoryResponse

}