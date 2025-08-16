package com.kkh.multimodule.core.data.di

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

    @Provides
    @Singleton
    fun provideOnBoardingRepository(
        localDataSource: LocalDataSource
    ): OnBoardingRepository{
        return OnBoardingRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideTimerRepository(
        timerDataSource: TimerDataSource,
        localDataSource : LocalDataSource
    ): TimerRepository{
        return TimerRepositoryImpl(timerDataSource,localDataSource)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyDataSource: HistoryDataSource,
    ): HistoryRepository {
        return HistoryRepositoryImpl(historyDataSource)
    }

}