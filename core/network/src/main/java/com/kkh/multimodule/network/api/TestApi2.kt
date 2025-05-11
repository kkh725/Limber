package com.kkh.multimodule.network.api

import com.kkh.multimodule.network.model.TestDataClass
import retrofit2.http.GET


interface TestApi2 {
    @GET("/posts")
    suspend fun getPosts(): List<TestDataClass>
}