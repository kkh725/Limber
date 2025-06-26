package com.kkh.multimodule.moduletest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kkh.accessibility.BlockedAppAccessibilityService
import com.kkh.multimodule.moduletest.navigation.LimberNavHost
import com.kkh.multimodule.moduletest.ui.theme.TestModuleTheme
import com.kkh.accessibility.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //lifecycleobserver 구독.
        val observer = LimberLifeCycleObserver(this)  
        lifecycle.addObserver(observer)
        
        setContent {
            TestModuleTheme {
                TestApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

}

@Composable
fun TestApp() {
    val navHostController = rememberNavController()
    LimberNavHost(navHostController)
}
