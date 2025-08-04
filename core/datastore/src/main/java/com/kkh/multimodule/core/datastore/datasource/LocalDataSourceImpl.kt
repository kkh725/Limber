package com.kkh.multimodule.core.datastore.datasource

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.kkh.multimodule.core.datastore.DataStoreManager
import com.kkh.multimodule.core.domain.model.ReservationInfo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList

internal class LocalDataSourceImpl @Inject constructor() :
    LocalDataSource {
    private val keyString = stringPreferencesKey("CUSTOM_TEXT")
    private val keyBoolean = booleanPreferencesKey("CUSTOM_BOOL")
    private val keyInt = intPreferencesKey("CUSTOM_INT")

    private val packageListKey = stringSetPreferencesKey("CUSTOM_STRING_LIST")
    private val reservationListKey = stringSetPreferencesKey("RESERVATION_LIST")
    private val isBlockMode = booleanPreferencesKey("IS_BLOCK_MODE")

    /**
     * 패키지명 로컬 저장
     */
    override suspend fun getBlockedPackageList(): List<String> {
        return DataStoreManager.readStringList(packageListKey).first().toList()
    }

    override suspend fun setBlockedPackageList(packageList: List<String>) {
        DataStoreManager.saveStringList(packageListKey, packageList)
    }

    override fun observeBlockedPackageList(): Flow<List<String>> {
        return DataStoreManager.readStringList(packageListKey)
    }

    /**
     * 앱 차단 상태
     */

    override suspend fun setBlockMode(isBlock: Boolean) {
        DataStoreManager.saveBool(isBlockMode, isBlock)
    }

    override suspend fun getBlockMode(): Boolean {
        return DataStoreManager.readBool(isBlockMode).first()
    }

    override fun observeBlockMode(): Flow<Boolean> {
        return DataStoreManager.readBool(isBlockMode)
    }

    /**
     * 차단 예약 관리.
     */

    override suspend fun setReservationList(reservationList: List<ReservationInfo>) {
        DataStoreManager.saveReservationInfoList(reservationListKey, reservationList)
    }

    override suspend fun getReservationList(): List<ReservationInfo> {
        return DataStoreManager.readReservationInfoList(reservationListKey).first()
    }

    override fun observeReservationList(): Flow<List<ReservationInfo>> {
        return DataStoreManager.readReservationInfoList(reservationListKey)
    }
}