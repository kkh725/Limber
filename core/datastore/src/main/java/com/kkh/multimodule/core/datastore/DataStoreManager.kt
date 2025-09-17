package com.kkh.multimodule.core.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.io.IOException
import kotlin.collections.map

object DataStoreManager {
    lateinit var dataStore: DataStore<Preferences>

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

    suspend fun saveReservationInfoList(
        reservationKey: Preferences.Key<Set<String>>,
        list: List<ReservationItemModel>
    ) {
        // JSON 문자열 + 난수 suffix로 고유값 보장
        val jsonSet = list.map { item ->
            val json = Json.encodeToString(item)
            "$json#${System.nanoTime()}" // suffix 추가
        }.toSet()

        dataStore.edit { prefs ->
            prefs[reservationKey] = jsonSet
        }

        Log.d("TAG", "saveReservationInfoList: 저장완료 $list")
    }


    fun readString(key: Preferences.Key<String>): Flow<String> = read(key, "")
    fun readInt(key: Preferences.Key<Int>): Flow<Int> = read(key, -1)
    fun readBool(key: Preferences.Key<Boolean>): Flow<Boolean> = read(key, false)
    fun readStringList(key: Preferences.Key<Set<String>>): Flow<List<String>> {
        return dataStore.data.catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }.map { it[key]?.toList() ?: emptyList() }
    }

    fun readReservationInfoList(
        reservationKey: Preferences.Key<Set<String>>
    ): Flow<List<ReservationItemModel>> {
        return dataStore.data
            .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
            .map { prefs ->
                val jsonSet = prefs[reservationKey] ?: emptySet()
                jsonSet.map { jsonWithSuffix ->
                    val pureJson = jsonWithSuffix.substringBefore("#") // ✅ suffix 제거
                    Json.decodeFromString<ReservationItemModel>(pureJson)
                }
            }
    }

    private fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }.map { it[key] ?: defaultValue }
    }
}