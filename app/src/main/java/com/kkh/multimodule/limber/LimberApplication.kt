package com.kkh.multimodule.limber

import android.app.Application
import com.kkh.multimodule.core.datastore.DataStoreManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LimberApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(this)
    }
}