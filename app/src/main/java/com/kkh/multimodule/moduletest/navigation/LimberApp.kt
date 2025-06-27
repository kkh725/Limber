package com.kkh.multimodule.moduletest.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.moduletest.LimberBottomBar
import com.kkh.multimodule.moduletest.RootViewModel
import com.kkh.multimodule.moduletest.intent.RootEvent


@Composable
fun LimberApp() {
    val navHostController = rememberNavController()

    val rootViewModel: RootViewModel = hiltViewModel()
    val rootState by rootViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { /* AppBar 등 */ },
        bottomBar = {
            LimberBottomBar(
                navHostController,
                onScreenSelected = { bottomNavRoute ->
                    rootViewModel.sendEvent(RootEvent.OnClickedBottomNaviItem(bottomNavRoute))
                }
            )
        }
    ) { paddingValues ->
        LimberNavHost(
            navController = navHostController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}



