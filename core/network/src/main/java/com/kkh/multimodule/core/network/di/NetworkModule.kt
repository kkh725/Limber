package com.kkh.multimodule.core.network.di

import com.kkh.multimodule.core.network.api.timer.TimerApi
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

}


