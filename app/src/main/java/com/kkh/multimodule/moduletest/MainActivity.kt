package com.kkh.multimodule.moduletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.feature.test.TestScreen
import com.kkh.multimodule.feature.test.navigation.TestRoute
import com.kkh.multimodule.feature.test.navigation.navigateToTest
import com.kkh.multimodule.feature.test.navigation.testScreen
import com.kkh.multimodule.moduletest.navigation.TestNavHost
import com.kkh.multimodule.moduletest.ui.theme.TestModuleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestModuleTheme {
                TestApp()
            }
        }
    }
}

@Composable
fun TestApp(){
    val navHostController = rememberNavController()
    TestNavHost(navHostController)
}
