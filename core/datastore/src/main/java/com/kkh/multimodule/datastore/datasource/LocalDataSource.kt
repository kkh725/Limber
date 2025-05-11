package com.kkh.multimodule.datastore.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface LocalDataSource {
    suspend fun saveCustomText(saveString : String)
    suspend fun getCustomText() : String
    fun observeCustomText(): Flow<String>
}