package com.kkh.multimodule.block

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val blockViewModel : BlockViewModel = hiltViewModel()
            val navHostController = rememberNavController()

            BlockNavHost(navController = navHostController)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
