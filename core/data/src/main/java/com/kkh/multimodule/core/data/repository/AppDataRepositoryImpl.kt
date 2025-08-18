package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AppDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : AppDataRepository {

    override suspend fun setBlockedPackageList(packageList: List<String>) {
        localDataSource.setBlockedPackageList(packageList)
    }

    override suspend fun getBlockedPackageList(): List<String> {
        return localDataSource.getBlockedPackageList()
    }

    override fun observeBlockedPackageList() : Flow<List<String>>{
        return localDataSource.observeBlockedPackageList()
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