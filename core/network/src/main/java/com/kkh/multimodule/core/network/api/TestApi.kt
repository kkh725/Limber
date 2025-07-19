package com.kkh.multimodule.core.network.api

import com.kkh.multimodule.core.network.model.TestDataClass
import retrofit2.Response
import retrofit2.http.GET


interface TestApi {
    @GET("/posts")
    suspend fun getPosts(): Response<List<TestDataClass>>
}


