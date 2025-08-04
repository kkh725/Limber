package com.kkh.multimodule.core.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import com.kkh.multimodule.core.domain.model.ReservationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface LocalDataSource {
    suspend fun setBlockedPackageList(packageList : List<String>)
    suspend fun getBlockedPackageList() : List<String>
    fun observeBlockedPackageList(): Flow<List<String>>

    suspend fun setBlockMode(isBlock: Boolean)
    suspend fun getBlockMode(): Boolean
    fun observeBlockMode(): Flow<Boolean>

    suspend fun setReservationList(reservationList: List<ReservationInfo>)
    suspend fun getReservationList(): List<ReservationInfo>
    fun observeReservationList(): Flow<List<ReservationInfo>>
}