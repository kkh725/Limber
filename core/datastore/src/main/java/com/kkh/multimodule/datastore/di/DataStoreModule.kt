package com.kkh.multimodule.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kkh.multimodule.datastore.DataStoreManagerImpl
import com.kkh.multimodule.datastore.DataStoreManager
import com.kkh.multimodule.datastore.datasource.LocalDataSource
import com.kkh.multimodule.datastore.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.dataStoreFile("settings_preferences")
        }
    }

    // internal fun 사용 시 외부에서 주입 할 수 없음.
    @Provides
    @Singleton
    internal fun provideDataStoreManager(dataStore: DataStore<Preferences>) : DataStoreManager {
        return DataStoreManagerImpl(dataStore)
    }

    // LocalDataSource 만 외부에서 주입해 사용.
    @Provides
    @Singleton
    fun provideLocalDataSource(dataStoreManager: DataStoreManager) : LocalDataSource {
        return LocalDataSourceImpl(dataStoreManager)
    }
}