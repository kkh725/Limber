package com.kkh.multimodule.network.api

import com.kkh.multimodule.network.model.TestDataClass
import retrofit2.Response
import retrofit2.http.GET


interface TestApi {
    @GET("/posts")
    suspend fun getPosts(): Response<List<TestDataClass>>
}


