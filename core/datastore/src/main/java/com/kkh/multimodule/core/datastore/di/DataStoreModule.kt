package com.kkh.multimodule.core.datastore.di

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.datastore.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }
}