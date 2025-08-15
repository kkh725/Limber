package com.kkh.multimodule.core.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface LocalDataSource {
    suspend fun setBlockedPackageList(packageList : List<String>)
    suspend fun getBlockedPackageList() : List<String>
    fun observeBlockedPackageList(): Flow<List<String>>

    suspend fun setBlockMode(isBlock: Boolean)
    suspend fun getBlockMode(): Boolean
    fun observeBlockMode(): Flow<Boolean>

    suspend fun setActiveTimerId(timerId: Int)
    suspend fun getActiveTimerId(): Int

    suspend fun setReservationList(reservationList: List<ReservationItemModel>)
    suspend fun getReservationList(): List<ReservationItemModel>
    fun observeReservationList(): Flow<List<ReservationItemModel>>

    suspend fun setIsOnboardingChecked(isChecked: Boolean)
    suspend fun getIsOnboardingChecked(): Boolean
}