package com.kkh.multimodule.core.data.di

import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.data.repository.AppDataRepositoryImpl
import com.kkh.multimodule.core.data.repository.BlockReservationRepositoryImpl
import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.network.datasource.TestDataSource
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
    fun provideAppDataRepository(
        localDataSource: LocalDataSource
    ): AppDataRepository {
        return AppDataRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideBlockReservationRepository(
        localDataSource: LocalDataSource
    ): BlockReservationRepository{
        return BlockReservationRepositoryImpl(localDataSource)
    }

}