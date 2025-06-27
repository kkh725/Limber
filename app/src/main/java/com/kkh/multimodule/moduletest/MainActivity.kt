package com.kkh.multimodule.moduletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.moduletest.navigation.LimberApp
import com.kkh.multimodule.moduletest.navigation.LimberNavHost
import com.kkh.multimodule.moduletest.ui.theme.TestModuleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
