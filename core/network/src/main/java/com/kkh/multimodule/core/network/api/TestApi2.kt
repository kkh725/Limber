package com.kkh.multimodule.core.network.api

import com.kkh.multimodule.core.network.model.TestDataClass
import retrofit2.http.GET


interface TestApi2 {
    @GET("/posts")
    suspend fun getPosts(): List<TestDataClass>
}