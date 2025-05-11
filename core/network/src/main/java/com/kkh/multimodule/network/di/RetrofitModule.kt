package com.kkh.multimodule.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

// NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @TestApi
    @Provides
    @Singleton
    internal fun provideJsonPlaceholderRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @TestApi2
    @Provides
    @Singleton
    internal fun provideAnotherApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }
}

// NetworkQualifiers.kt
// baseUrl 이 두 개 이상일 때 retrofit을 다르게 생성 후 주입 필요.

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class TestApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class TestApi2
