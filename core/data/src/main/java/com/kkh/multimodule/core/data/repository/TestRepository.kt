package com.kkh.multimodule.core.data.repository

interface TestRepository {
    suspend fun localDoit()
    suspend fun networkDoit()
}