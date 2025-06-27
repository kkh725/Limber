package com.kkh.multimodule.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.ui.component.LimberHomeTopAppBar

@Composable
fun HomeScreen(
    onClickButtonTonNavigate: () -> Unit
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val appInfoList = uiState.usageAppInfoList

    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(HomeEvent.EnterHomeScreen(context))
    }

    Scaffold(
        topBar = {
            LimberHomeTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                onNavigationClick = onClickButtonTonNavigate,
                onActionClick = {},
            )
        },
        bottomBar = { Text("gd") }
    ) { paddingValues ->

        if (appInfoList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(appInfoList) { appInfo ->
                    DopamineAppItem(appInfo)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}

@Composable
fun DopamineAppItem(appInfo: AppInfo = AppInfo.empty) {

    val bitmap = appInfo.appIcon?.toBitmap()
    val imageBitmap = bitmap?.asImageBitmap()
    val painter = imageBitmap?.let { BitmapPainter(it) }

    Row {
        painter?.let {
            Image(
                painter = it,
                contentDescription = "App Icon",
                modifier = Modifier.size(48.dp) // 원하는 크기 지정
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(text = appInfo.usageTime.toString())
    }
}
