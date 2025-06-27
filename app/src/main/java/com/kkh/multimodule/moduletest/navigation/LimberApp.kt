package com.kkh.multimodule.moduletest.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

    // topbar는 없더라고 시스템 inset
    Scaffold(
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
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}



