package com.kkh.multimodule.core.network.datasource

import com.kkh.multimodule.core.network.model.TestDataClass
import retrofit2.Response

interface TestDataSource {
    suspend fun testPosts() : Response<List<TestDataClass>>
}