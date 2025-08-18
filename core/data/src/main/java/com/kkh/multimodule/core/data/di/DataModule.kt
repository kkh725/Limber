package com.kkh.multimodule.core.data.di

import android.content.Context
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.data.repository.AppDataRepositoryImpl
import com.kkh.multimodule.core.data.repository.BlockReservationRepositoryImpl
import com.kkh.multimodule.core.data.repository.HistoryRepositoryImpl
import com.kkh.multimodule.core.data.repository.OnBoardingRepositoryImpl
import com.kkh.multimodule.core.data.repository.TimerRepositoryImpl
import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.domain.repository.OnBoardingRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.core.network.datasource.history.HistoryDataSource
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppDataRepository(
        impl: AppDataRepositoryImpl
    ): AppDataRepository

    @Binds
    @Singleton
    abstract fun bindBlockReservationRepository(
        impl: BlockReservationRepositoryImpl
    ): BlockReservationRepository

    @Binds
    @Singleton
    abstract fun bindOnBoardingRepository(
        impl: OnBoardingRepositoryImpl
    ): OnBoardingRepository

    @Binds
    @Singleton
    abstract fun bindTimerRepository(
        impl: TimerRepositoryImpl
    ): TimerRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        impl: HistoryRepositoryImpl
    ): HistoryRepository
}