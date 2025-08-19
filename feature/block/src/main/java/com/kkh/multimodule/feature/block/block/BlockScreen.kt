package com.kkh.multimodule.feature.block.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.UnBlockWarnModal
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton

@Composable
fun BlockScreen(
    onClickUnBlock: () -> Unit = {},
    onClickContinue: () -> Unit = {}
) {

    var isWarnModalVisible by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            painter = painterResource(R.drawable.bg_all_blocking),
            contentDescription = "Background Image"
        )
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                BlockScreenBottomBar(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .navigationBarsPadding(),
                    onClickUnBlock = {
                        isWarnModalVisible = true
                    },
                    onClickContinue = onClickContinue
                )
            }
        ) { paddingValues ->
            BlockScreenContent(Modifier.padding(paddingValues))
        }
        if (isWarnModalVisible) {
            Dialog(onDismissRequest = { isWarnModalVisible = false }) {
                UnBlockWarnModal(
                    modifier = Modifier.height(180.dp),
                    onClickUnBlock = {
                        isWarnModalVisible = false
                        onClickUnBlock()
                    },
                    onClickContinue = { isWarnModalVisible = false })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BlockScreenPreview() {
    BlockScreen({}, {})
}

@Composable
fun BlockScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.char_block),
            modifier = Modifier.size(240.dp),
            contentDescription = "Limber Logo"
        )
        Text("차단중..", style = LimberTextStyle.Heading1, color = Color.White)
        Spacer(Modifier.height(12.dp))

        Text(
            "집중을 끝까지 이어나가면\n" +
                    "만족스러운 하루가 될거에요.", textAlign = TextAlign.Center, color = Color.White
        )
    }
}

@Composable
fun BlockAppText(appName: String) {
    Text("$appName 차단중..", style = LimberTextStyle.Heading1, color = Color.White)
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
        LimberSquareButton(
            onClick = onClickUnBlock,
            containerColor = Color.White,
            textColor = Gray800,
            text = "잠금 풀기",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        LimberSquareButton(
            onClick = onClickContinue,
            text = "계속 집중하기",
            modifier = Modifier.weight(1f)
        )

    }
}