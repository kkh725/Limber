package com.kkh.multimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kkh.multimodule.navigation.LimberApp
import com.kkh.multimodule.ui.theme.TestModuleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

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
