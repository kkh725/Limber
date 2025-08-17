package com.kkh.multimodule.core.datastore.di

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.datastore.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import androidx.core.content.edit
import com.kkh.multimodule.core.datastore.datasource.AppUuidProvider

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideAppUuid(@ApplicationContext context: Context): String {
        return AppUuidProvider.getUuid(context)
    }
}