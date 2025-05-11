package com.kkh.multimodule.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

// DataStoreManager 구현체를 외부에 공개하지 않기 위함.
interface DataStoreManager {
    suspend fun saveString(key: Preferences.Key<String>, value: String)
    suspend fun saveInt(key: Preferences.Key<Int>, value: Int)
    suspend fun saveBool(key: Preferences.Key<Boolean>, value: Boolean)
    fun readString(key: Preferences.Key<String>): Flow<String>
    fun readInt(key: Preferences.Key<Int>): Flow<Int>
    fun readBool(key: Preferences.Key<Boolean>): Flow<Boolean>
}