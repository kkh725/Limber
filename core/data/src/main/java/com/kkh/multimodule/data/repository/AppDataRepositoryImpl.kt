package com.kkh.multimodule.data.repository

import android.app.usage.UsageStats
import android.content.Context
import com.kkh.multimodule.datastore.datasource.LocalDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class AppDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : AppDataRepository {

    override suspend fun savePackageList(packageList: List<String>) {
        localDataSource.savePackageList(packageList)
    }

    override suspend fun getPackageList(): List<String> {
        return localDataSource.getPackageList()
    }

    override fun observePackageList() : Flow<List<String>>{
        return localDataSource.observePackageList()
    }

    override suspend fun setBlockMode(isBlock: Boolean) {
        localDataSource.setBlockMode(isBlock)
    }

    override suspend fun getBlockMode(): Boolean {
        return localDataSource.getBlockMode()
    }

    override fun observeBlockMode(): Flow<Boolean> {
        return localDataSource.observeBlockMode()
    }
}