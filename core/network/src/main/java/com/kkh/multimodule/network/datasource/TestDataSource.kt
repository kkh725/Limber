package com.kkh.multimodule.network.datasource

import com.kkh.multimodule.network.di.TestApi
import com.kkh.multimodule.network.model.TestDataClass
import jakarta.inject.Inject
import retrofit2.Response

interface TestDataSource {
    suspend fun testPosts() : Response<List<TestDataClass>>
}