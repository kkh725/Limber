package com.kkh.multimodule.network.datasource

import com.kkh.multimodule.network.api.TestApi
import com.kkh.multimodule.network.model.TestDataClass
import jakarta.inject.Inject
import retrofit2.Response

internal class TestDataSourceImpl @Inject constructor(private val testApi: TestApi) :
    TestDataSource {
    override suspend fun testPosts(): Response<List<TestDataClass>> {
        return testApi.getPosts()
    }
}