package com.kkh.multimodule.datastore.datasource

import android.app.usage.UsageStats
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface LocalDataSource {
    suspend fun saveCustomText(saveString : String)
    suspend fun getCustomText() : String
    fun observeCustomText(): Flow<String>
    fun getTodayUsageStats(context : Context) : List<UsageStats>
}