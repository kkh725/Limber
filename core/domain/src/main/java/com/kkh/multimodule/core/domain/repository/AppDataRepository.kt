package com.kkh.multimodule.core.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 *  차단 할 앱 정보 세팅 관련 레포
 */
interface AppDataRepository {
    suspend fun setBlockedPackageList(packageList: List<String>)
    suspend fun getBlockedPackageList(): List<String>
    fun observeBlockedPackageList(): Flow<List<String>>

    suspend fun setBlockMode(isBlock: Boolean)
    suspend fun getBlockMode(): Boolean
    fun observeBlockMode(): Flow<Boolean>
}