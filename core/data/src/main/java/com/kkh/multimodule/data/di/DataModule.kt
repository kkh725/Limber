package com.kkh.multimodule.data.di

import com.kkh.multimodule.data.repository.TestRepositoryImpl
import com.kkh.multimodule.datastore.datasource.LocalDataSource
import com.kkh.multimodule.domain.repository.TestRepository
import com.kkh.multimodule.network.datasource.TestDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTestRepository(
        localDataSource: LocalDataSource,
        testDataSource: TestDataSource
    ): TestRepository {
        return TestRepositoryImpl(localDataSource, testDataSource)
    }

}