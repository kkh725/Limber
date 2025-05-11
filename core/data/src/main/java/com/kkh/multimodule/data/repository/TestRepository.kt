package com.kkh.multimodule.data.repository

interface TestRepository {
    suspend fun localDoit()
    suspend fun networkDoit()
}