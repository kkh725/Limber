package com.kkh.multimodule.core.network.datasource

import com.kkh.multimodule.core.network.api.TestApi
import com.kkh.multimodule.core.network.model.TestDataClass
import jakarta.inject.Inject
import retrofit2.Response

class TestDataSourceImpl @Inject constructor(private val testApi: TestApi) :
    TestDataSource {
    override suspend fun testPosts(): Response<List<TestDataClass>> {
        return testApi.getPosts()
    }
}