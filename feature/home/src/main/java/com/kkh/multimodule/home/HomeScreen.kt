package com.kkh.multimodule.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.ui.component.LimberHomeTopAppBar

@Composable
fun HomeScreen(
    onClickButtonTonNavigate: () -> Unit
) {
    Scaffold(
        topBar = {
            LimberHomeTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                onNavigationClick = onClickButtonTonNavigate,
                onActionClick = {},
            )
        },
        bottomBar = { Text("gd") }) { paddingValues ->

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}