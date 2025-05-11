package com.kkh.multimodule.feature.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Preview
@Composable
fun TestScreen(
    testViewModel: TestViewModel = hiltViewModel(),
    onClickButtonTonNavigate: () -> Unit
) {

    val uiState by testViewModel.uiState.collectAsState()
    val testText = uiState.loadingState

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Button(onClick = {
                testViewModel.sendEvent(TestEvent.ClickedButton)
                onClickButtonTonNavigate()
            }) {
                Text(
                    "Button!"
                )
            }
        }
    }
}