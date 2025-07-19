package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.network.datasource.TestDataSource
import jakarta.inject.Inject

internal class TestRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: TestDataSource
) : TestRepository {

    override suspend fun localDoit(){
//        localDataSource.getCustomText()
    }

    override suspend fun networkDoit(){
//        val res = networkDataSource.testPosts()
//        if (res.isSuccessful){
//            Log.d("network", "networkDoit: network 통신 성공\n ${res.body()}")
//        }
    }
}