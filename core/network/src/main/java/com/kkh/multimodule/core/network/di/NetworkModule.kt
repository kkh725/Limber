package com.kkh.multimodule.core.network.di

import com.kkh.multimodule.core.network.api.TestApi
import com.kkh.multimodule.core.network.datasource.TestDataSource
import com.kkh.multimodule.core.network.datasource.TestDataSourceImpl
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
    internal fun provideTestApi(@TestApi3 retrofit: Retrofit): TestApi {
        return retrofit.create(TestApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideTestApi2(@TestApi2 retrofit: Retrofit): TestApi2 {
        return retrofit.create(TestApi2::class.java)
    }

    //최종적으로 TestDataSource 만 공개함.
    @Provides
    @Singleton
    fun provideTestDataSource(testApi: TestApi) : TestDataSource {
        return TestDataSourceImpl(testApi)
    }

}


