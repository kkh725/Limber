package com.kkh.multimodule

import android.app.Application
import android.util.Log
import com.kkh.multimodule.core.datastore.DataStoreManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LimberApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("test", "test: ")
        DataStoreManager.init(this)
    }
}