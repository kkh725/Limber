package com.kkh.multimodule.core.network.api.timer

import com.kkh.multimodule.core.network.model.request.HistoryRequestDto
import com.kkh.multimodule.core.network.model.request.RetrospectsRequestDto
import com.kkh.multimodule.core.network.model.response.history.TotalActualResponseDto
import com.kkh.multimodule.core.network.model.request.history.TotalImmersionRequestDto
import com.kkh.multimodule.core.network.model.response.history.TotalImmersionResponseDto
import com.kkh.multimodule.core.network.model.response.ApiResponse
import com.kkh.multimodule.core.network.model.response.BaseResponse
import com.kkh.multimodule.core.network.model.response.HistoryResponse
import com.kkh.multimodule.core.network.model.response.history.ActualByWeekendResponse
import com.kkh.multimodule.core.network.model.response.history.FocusDistributionResponseDto
import com.kkh.multimodule.core.network.model.response.history.FocusDistributionResponse
import com.kkh.multimodule.core.network.model.response.history.HistoryWithRetrospectsResponse
import com.kkh.multimodule.core.network.model.response.history.ImmersionByWeekdayResponse
import com.kkh.multimodule.core.network.model.response.history.LatestTimerResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryApi {

    /**
     * 타이머 이력 저장
     */
//    @POST("/api/timer-retrospects")
//    suspend fun writeRetrospects(
//        @Body retrospectsDto : RetrospectsRequestDto
//    ): BaseResponse

    /**
     * 타이머 이력 조회 회고 여부를 볼 수 없이 all로 나옴
     */
    @GET("/api/timer-histories/me")
    suspend fun getTimerHistories(
        @Body historyRequestDto: HistoryRequestDto
    ): HistoryResponse

    /**
     * 타이머 이력조회 회고 여부 true,false로 구별가능함.
     */
    @GET("/api/timer-histories/search")
    suspend fun getTimerHistoriesWithRetrospect(
        @Query("userId") userId: String,
        @Query("searchRange") searchRange: String = "WEEKLY",
        @Query("onlyIncompleteRetrospect") onlyIncompleteRetrospect: Boolean = false
    ): HistoryWithRetrospectsResponse

    /**
     * 가장 최근 이력 조회
     */
    @GET("/api/timer-histories/latest-id")
    suspend fun getTimerLatestId(
        @Query("userId") userId: String,
        @Query("timerId") timerId: Int
    ): LatestTimerResponse

    /**
     * 총 몰입도 조회
     */
    @POST("/api/timer-histories/total-immersion")
    suspend fun getTotalImmersion(
        @Body totalImmersionRequestDto: TotalImmersionRequestDto
    ): ApiResponse<TotalImmersionResponseDto>

    /**
     * 전체 총 실험시간
     */
    @POST("/api/timer-histories/total-actual")
    suspend fun getTotalActual(
        @Body totalImmersionRequestDto: TotalImmersionRequestDto
    ): ApiResponse<TotalActualResponseDto>

    /**
     * 요일별 총 몰입도
     */
    @POST("/api/timer-histories/immersion-by-weekend")
    suspend fun getImmersionByWeekend(
        @Body totalImmersionRequestDto: TotalImmersionRequestDto
    ): ImmersionByWeekdayResponse

    /**
     * 몰입유형 별 실제 시간 합
     */
    @POST("/api/timer-histories/focus-distribution")
    suspend fun getFocusDistribution(
        @Body totalImmersionRequestDto: TotalImmersionRequestDto
    ): FocusDistributionResponse

    /**
     * 요일 별 총 실험 시간
     */
    @POST("/api/timer-histories/actual-by-weekend")
    suspend fun getActualByWeekend(
        @Body totalImmersionRequestDto: TotalImmersionRequestDto
    ): ActualByWeekendResponse

}