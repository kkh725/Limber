package com.kkh.multimodule.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.ui.component.LimberButton

@Composable
fun BlockScreen(
    onClickUnBlock: () -> Unit,
    onClickContinue: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BlockScreenBottomBar(
                modifier = Modifier.padding(bottom = 20.dp),
                onClickUnBlock = onClickUnBlock,
                onClickContinue = onClickContinue
            )
        }
    ) { paddingValues ->
        BlockScreenContent(Modifier.padding(paddingValues))
    }
}

@Preview
@Composable
fun BlockScreenPreview() {
    BlockScreen ({},{})
}

@Composable
fun BlockScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_limber),
            modifier = Modifier.size(180.dp),
            contentDescription = "Limber Logo"
        )
        BlockAppText("youtube")

        Text("지금은 집중의 시간이다.\n집중 흐름을 이어나가면 만족스러운 하루가 될것이다.", textAlign = TextAlign.Center)
    }
}

@Composable
fun BlockAppText(appName: String) {
    Text("$appName 차단중..")
}

@Composable
fun BlockScreenBottomBar(
    modifier: Modifier = Modifier,
    onClickUnBlock: () -> Unit,
    onClickContinue: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        LimberButton(
            onClick = onClickUnBlock,
            text = "잠금 풀기",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        LimberButton(
            onClick = onClickContinue,
            text = "계속 집중하기",
            modifier = Modifier.weight(1f)
        )

    }
}