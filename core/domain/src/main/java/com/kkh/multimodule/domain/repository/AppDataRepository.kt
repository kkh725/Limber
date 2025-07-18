package com.kkh.multimodule.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    suspend fun savePackageList(packageList: List<String>)
    suspend fun getPackageList(): List<String>
    fun observePackageList(): Flow<List<String>>

    suspend fun setBlockMode(isBlock: Boolean)
    suspend fun getBlockMode(): Boolean
    fun observeBlockMode(): Flow<Boolean>
}