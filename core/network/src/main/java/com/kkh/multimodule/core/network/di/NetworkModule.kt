package com.kkh.multimodule.core.network.di

import com.kkh.multimodule.core.network.api.timer.HistoryApi
import com.kkh.multimodule.core.network.api.timer.RetrospectsApi
import com.kkh.multimodule.core.network.api.timer.TimerApi
import com.kkh.multimodule.core.network.datasource.history.HistoryDataSource
import com.kkh.multimodule.core.network.datasource.history.HistoryDataSourceImpl
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSource
import com.kkh.multimodule.core.network.datasource.timer.TimerDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun provideTimerApi(retrofit: Retrofit): TimerApi {
        return retrofit.create(TimerApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrospectsApi(retrofit: Retrofit): RetrospectsApi {
        return retrofit.create(RetrospectsApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideHistoryApi(retrofit: Retrofit): HistoryApi {
        return retrofit.create(HistoryApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTimerDataSource(
        impl: TimerDataSourceImpl
    ): TimerDataSource

    @Binds
    @Singleton
    abstract fun bindHistoryDataSource(
        impl: HistoryDataSourceImpl
    ): HistoryDataSource
}


