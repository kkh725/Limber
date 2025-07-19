package com.kkh.multimodule.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object DataStoreManager {
    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = PreferenceDataStoreFactory.create {
            context.dataStoreFile("settings.preferences_pb")
        }
    }

    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    suspend fun saveBool(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    suspend fun saveStringList(key: Preferences.Key<Set<String>>, value: List<String>) {
        dataStore.edit { preferences -> preferences[key] = value.toSet() }
    }

    fun readString(key: Preferences.Key<String>): Flow<String> = read(key, "")
    fun readInt(key: Preferences.Key<Int>): Flow<Int> = read(key, 0)
    fun readBool(key: Preferences.Key<Boolean>): Flow<Boolean> = read(key, false)
    fun readStringList(key: Preferences.Key<Set<String>>): Flow<List<String>> {
        return dataStore.data.catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }.map { it[key]?.toList() ?: emptyList() }
    }

    private fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }.map { it[key] ?: defaultValue }
    }
}