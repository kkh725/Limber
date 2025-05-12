package com.kkh.multimodule.moduletest

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("test", "test: ")
    }
}