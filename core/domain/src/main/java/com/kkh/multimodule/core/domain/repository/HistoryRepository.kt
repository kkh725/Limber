package com.kkh.multimodule.core.domain.repository

import com.kkh.multimodule.core.domain.model.history.ActualByWeekendModel
import com.kkh.multimodule.core.domain.model.history.FocusDistributionModel
import com.kkh.multimodule.core.domain.model.history.HistoryModel
import com.kkh.multimodule.core.domain.model.history.HistoryWithRetrospectsModel
import com.kkh.multimodule.core.domain.model.history.ImmersionByWeekdayModel
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel
import com.kkh.multimodule.core.domain.model.history.TotalActualModel
import com.kkh.multimodule.core.domain.model.history.TotalImmersionModel

interface HistoryRepository {
    // 이력조회
    suspend fun getHistoryList(): Result<List<HistoryModel>>
    // 회고 여부 파악한 이력조회
    suspend fun getHistoryListWithRetrospects(): Result<List<HistoryWithRetrospectsModel>>
    // 가장 최근 이력 조회
    suspend fun getLatestHistoryId(timerId : Int) : Result<LatestTimerHistoryModel>
    // 전체 몰입도// 전체 몰입도
    suspend fun getTotalImmersion(startTime : String, endTime : String): Result<TotalImmersionModel>
    // 전체 총 실험시간
    suspend fun getTotalActual(startTime : String, endTime : String): Result<TotalActualModel>
    // 요일별 총 몰입도
    suspend fun getImmersionByWeekend (startTime : String, endTime : String): Result<List<ImmersionByWeekdayModel>>
    // 몰입유형 별 실제 시간 합
    suspend fun getFocusDistribution(startTime : String, endTime : String): Result<List<FocusDistributionModel>>
    // 요일별 총 실험 시간
    suspend fun getActualByWeekend(startTime : String, endTime : String): Result<List<ActualByWeekendModel>>
}