package com.kkh.multimodule.limber.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.limber.LimberBottomBar
import com.kkh.multimodule.limber.RootViewModel
import com.kkh.multimodule.core.domain.ScreenState
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.limber.intent.RootEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimberApp() {
    val navHostController = rememberNavController()

    val rootViewModel: RootViewModel = hiltViewModel()
    val rootState by rootViewModel.uiState.collectAsState()
    val screenState = rootState.screenState

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        Modifier
            .fillMaxSize()
            .background(Gray50)
    ) {
        Scaffold(
            containerColor = Gray50, bottomBar = {
                if (screenState == ScreenState.HOME_SCREEN ||
                    screenState == ScreenState.TIMER_SCREEN ||
                    screenState == ScreenState.LABORATORY_SCREEN ||
                    screenState == ScreenState.MORE_SCREEN) {
                    LimberBottomBar(
                        modifier = Modifier.navigationBarsPadding(),
                        navController = navHostController,
                        onScreenSelected = { bottomNavRoute ->
                            rootViewModel.sendEvent(RootEvent.OnClickedBottomNaviItem(bottomNavRoute))
                        })
                }
            }) { paddingValues ->
            LimberNavHost(
                navController = navHostController,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                rootViewModel = rootViewModel
            )
        }
    }
}



