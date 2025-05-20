package com.kkh.multimodule.domain.repository

interface TestRepository {
    suspend fun localDoit()
    suspend fun networkDoit()
}