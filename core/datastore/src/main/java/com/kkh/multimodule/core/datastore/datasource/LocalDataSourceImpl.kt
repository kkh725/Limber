package com.kkh.multimodule.core.datastore.datasource

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.kkh.multimodule.core.datastore.DataStoreManager
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlin.concurrent.timer

internal class LocalDataSourceImpl @Inject constructor() :
    LocalDataSource {
    private val keyString = stringPreferencesKey("CUSTOM_TEXT")
    private val keyBoolean = booleanPreferencesKey("CUSTOM_BOOL")
    private val keyInt = intPreferencesKey("CUSTOM_INT")

    private val packageListKey = stringSetPreferencesKey("CUSTOM_STRING_LIST")
    private val reservationListKey = stringSetPreferencesKey("RESERVATION_LIST")
    private val isBlockMode = booleanPreferencesKey("IS_BLOCK_MODE")
    private val activeTimerIdKey = intPreferencesKey("ACTIVE_TIMER_ID")
    private val isOnBoardingChecked = booleanPreferencesKey("IS_ONBOARDING_CHECKED")


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

    override suspend fun setReservationList(reservationList: List<ReservationItemModel>) {
        DataStoreManager.saveReservationInfoList(reservationListKey, reservationList)
    }

    override suspend fun getReservationList(): List<ReservationItemModel> {
        return DataStoreManager.readReservationInfoList(reservationListKey).first()
    }

    override fun observeReservationList(): Flow<List<ReservationItemModel>> {
        return DataStoreManager.readReservationInfoList(reservationListKey)
    }

    /**
     * 현재 진행 되고 있는 타이머 id
     */
    override suspend fun setActiveTimerId(timerId: Int) {
        DataStoreManager.saveInt(activeTimerIdKey, timerId)
    }

    override suspend fun getActiveTimerId(): Int {
        return DataStoreManager.readInt(activeTimerIdKey).first()
    }

    /**
     * 온보딩 확인 여부 체크
     */

    override suspend fun setIsOnboardingChecked(isChecked: Boolean) {
        DataStoreManager.saveBool(isOnBoardingChecked, isChecked)
    }

    override suspend fun getIsOnboardingChecked(): Boolean {
        return DataStoreManager.readBool(isOnBoardingChecked).first()
    }
}