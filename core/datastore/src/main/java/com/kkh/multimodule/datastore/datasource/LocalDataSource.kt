package com.kkh.multimodule.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import com.kkh.accessibility.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface LocalDataSource {
    suspend fun saveCustomText(saveString : String)
    suspend fun getCustomText() : String
    fun observeCustomText(): Flow<String>
    fun getUsageStats(context : Context) : List<UsageStats>
    fun getAppInfo(context: Context, usageStats: UsageStats) : AppInfo
}