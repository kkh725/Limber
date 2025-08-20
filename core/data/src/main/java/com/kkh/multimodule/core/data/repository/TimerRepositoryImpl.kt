package com.kkh.multimodule.core.data.repository

import android.annotation.SuppressLint
import com.kkh.multimodule.core.data.mapper.toDomain
import com.kkh.multimodule.core.data.mapper.toDomainList
import com.kkh.multimodule.core.data.mapper.toDto
import com.kkh.multimodule.core.data.mapper.toRequestDto
import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.TimerCode
import com.kkh.multimodule.core.domain.TimerStatusModel
import com.kkh.multimodule.core.domain.model.PatchTimerModel
import com.kkh.multimodule.core.domain.model.RetrospectsRequestModel
import com.kkh.multimodule.core.domain.model.SingleTimerModel
import com.kkh.multimodule.core.domain.model.TimerListModel
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import jakarta.inject.Inject
import jakarta.inject.Named
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import com.kkh.multimodule.core.network.model.response.processApiResponse
import java.util.UUID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.concurrent.timer

class TimerRepositoryImpl @Inject constructor(
    private val timerDataSource: TimerDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext private val context: Context
) :
    TimerRepository {

    /**
     * 타이머 지금시작
     */
    override suspend fun reserveImmediateTimer(request: SingleTimerModel): Result<SingleTimerModel> {
        return runCatching {
            val requestDto = request.toRequestDto(TimerCode.IMMEDIATE.text, getOrCreateUUID())

            timerDataSource.reserveTimer(requestDto).processApiResponse().toDomain()
        }
    }

    /**
     * 현재 진행중인 타이머 id
     */
    override suspend fun setActiveTimerId(timerId: Int) {
        localDataSource.setActiveTimerId(timerId)
    }

    override suspend fun getActiveTimerId(): Int {
        return localDataSource.getActiveTimerId()
    }

    /**
     * 타이머 예약하기
     */
    override suspend fun reserveScheduledTimer(request: SingleTimerModel): Result<SingleTimerModel> {
        return runCatching {
            val requestDto = request.toRequestDto(TimerCode.SCHEDULED.text, getOrCreateUUID())

            timerDataSource.reserveTimer(requestDto).processApiResponse().toDomain()
        }
    }

    /**
     * 타이머 해제
     */
    override suspend fun unlockTimer(
        timerId: Int,
        failReason: String
    ): Result<Unit> =
        runCatching {
            timerDataSource.unlockTimer(timerId = timerId, failReason = failReason).processApiResponse()
        }

    /**
     * 타이머 현재 상태. (진행중, 레디 등)
     */
    override suspend fun getCurrentTimerStatus(timerId: Int): Result<TimerStatusModel> =
        runCatching {
            timerDataSource.getCurrentTimerStatus(timerId).processApiResponse().toDomain()
        }

    /**
     * 타이머 싱글?
     */
    override suspend fun getSingleTimer(timerId: Int): Result<SingleTimerModel> =
        runCatching {
            timerDataSource.getSingleTimer(timerId).processApiResponse().toDomain()
        }

    /**
     * 타이머 리스트 get
     */
    override suspend fun getTimerList(): Result<TimerListModel> =
        runCatching {
            timerDataSource.getTimerList(getOrCreateUUID()).processApiResponse().toDomainList()
        }

    /**
     * 타이머 상태 변경 토글
     */
    override suspend fun patchTimerStatus(timerId: Int, status: PatchTimerModel): Result<Unit> =
        runCatching {
            timerDataSource.patchTimerStatus(timerId, status.toDto()).processApiResponse()
        }

    /**
     * 타이머 삭제
     */
    override suspend fun deleteTimerList(timerIdList: List<Int>): Result<Unit> =
        runCatching {
            val response = timerDataSource.deleteTimer(timerIdList)
            if (response.isSuccessful) {
                // 삭제 성공
            } else {
                // 에러 처리
                throw (Exception("error"))
            }
        }

    /**
     * 타이머 회고 작성
     */
    override suspend fun writeRetrospects(requestModel: RetrospectsRequestModel): Result<Unit> =
        runCatching {
            timerDataSource.writeRetrospects(requestModel.toDto(getOrCreateUUID())).processApiResponse()
        }

    //uuid 생성
    @SuppressLint("HardwareIds")
    private fun getOrCreateUUID(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
