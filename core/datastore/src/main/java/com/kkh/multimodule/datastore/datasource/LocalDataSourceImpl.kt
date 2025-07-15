package com.kkh.multimodule.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.kkh.multimodule.datastore.DataStoreManager
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal class LocalDataSourceImpl @Inject constructor() :
    LocalDataSource {
    private val keyString = stringPreferencesKey("CUSTOM_TEXT")
    private val keyBoolean = booleanPreferencesKey("CUSTOM_BOOL")
    private val keyInt = intPreferencesKey("CUSTOM_INT")
    private val packageListKey = stringSetPreferencesKey("CUSTOM_STRING_LIST")

    private val isBlockMode = booleanPreferencesKey("IS_BLOCK_MODE")
    /**
     * 패키지명 로컬 저장
     */
    override suspend fun getPackageList(): List<String> {
        return DataStoreManager.readStringList(packageListKey).first().toList()
    }

    override suspend fun savePackageList(packageList: List<String>) {
        DataStoreManager.saveStringList(packageListKey, packageList)
    }

    override fun observePackageList(): Flow<List<String>> {
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
}