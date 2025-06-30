package com.kkh.multimodule.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.LimberBottomBar
import com.kkh.multimodule.RootViewModel
import com.kkh.multimodule.intent.RootEvent


@Composable
fun LimberApp() {
    val navHostController = rememberNavController()

    val rootViewModel: RootViewModel = hiltViewModel()
    val rootState by rootViewModel.uiState.collectAsState()

    // topbar는 없더라고 시스템 inset
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            LimberBottomBar(
                modifier = Modifier.navigationBarsPadding(),
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
                .fillMaxSize()
        )
    }
}



