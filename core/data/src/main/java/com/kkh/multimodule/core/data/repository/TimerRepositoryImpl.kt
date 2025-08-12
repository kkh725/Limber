package com.kkh.multimodule.core.data.repository

import com.google.gson.annotations.SerializedName
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.data.mapper.toDomainList
import com.kkh.multimodule.core.data.mapper.toDto
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.domain.model.TimerListModel
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import jakarta.inject.Inject

class TimerRepositoryImpl @Inject constructor(private val timerDataSource: TimerDataSource) : TimerRepository{
    override suspend fun reserveTimer(request : SingleTimerModel) : Result<SingleTimerModel>{
        return runCatching {
            val dto = timerDataSource.reserveTimer(request.toDto()).data
            dto?.toDomain() ?: SingleTimerModel.empty
        }
    }

    override suspend fun getCurrentTimerStatus(timerId: Int): Result<TimerStatusModel> {
        return runCatching {
            val dto = timerDataSource.getCurrentTimerStatus(timerId).data
            dto?.toDomain() ?: TimerStatusModel.READY
        }
    }

    override suspend fun getSingleTimer(timerId: Int): Result<SingleTimerModel> {
        return runCatching {
            val dto = timerDataSource.getSingleTimer(timerId).data
            dto?.toDomain() ?: SingleTimerModel.empty
        }
    }

    override suspend fun getTimerList(userId: String): Result<TimerListModel> {
        return runCatching {
            val dto = timerDataSource.getTimerList(userId).data
            dto?.toDomainList() ?: emptyList()
        }
    }
}

