package com.kkh.multimodule.limber

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kkh.multimodule.core.accessibility.notification.NotificationHelper
import com.kkh.multimodule.limber.navigation.LimberApp
import com.kkh.multimodule.limber.ui.theme.TestModuleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        NotificationHelper(this).showTimerNotification(1,true)

        enableEdgeToEdge()

        //lifecycleobserver 구독.
        val observer = LimberLifeCycleObserver(this)
        lifecycle.addObserver(observer)

        setContent {
            TestModuleTheme {
                LimberApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
