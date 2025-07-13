package com.kkh.multimodule.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.multimodule.datastore.DataStoreManager
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal class LocalDataSourceImpl @Inject constructor(private val dataStoreManager: DataStoreManager) :
    LocalDataSource {

    private val keyString = stringPreferencesKey("CUSTOM_TEXT")
    private val keyBoolean = booleanPreferencesKey("CUSTOM_BOOL")
    private val keyInt = intPreferencesKey("CUSTOM_INT")

    override suspend fun saveCustomText(saveString: String) {
        dataStoreManager.saveString(keyString, saveString)
    }

    // 한 번만 값을 얻고 싶다면.
    override suspend fun getCustomText(): String {
        return dataStoreManager.readString(keyString).first()
    }

    // 변화를 계속 관찰하고 싶다면.
    override fun observeCustomText(): Flow<String> {
        return dataStoreManager.readString(keyString)
    }

    override fun getUsageStats(context: Context): List<UsageStats> {
        return AppUsageStatsManager.getUsageStats(context)
    }

    override fun getAppInfo(context: Context, usageStats: UsageStats): AppInfo {
        val appInfo = AppInfo(
            appName = AppInfoProvider.getAppLabel(context, usageStats.packageName) ?: "Unknown",
            packageName = usageStats.packageName,
            appIcon = AppInfoProvider.getAppIcon(context, usageStats.packageName),
            usageTime = AppUsageStatsManager.formatUsageTime(usageStats.totalTimeInForeground)
        )
        return appInfo
    }
}