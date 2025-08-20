package com.kkh.multimodule.feature.block.unblock

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray50
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberSquareButton

@Preview
@Composable
fun UnBlockCompleteScreen(
    onClickCloseScreen: () -> Unit = {},
    onNavigateToRecall: () -> Unit = {}
) {
    Scaffold(
        containerColor = Gray50,
        topBar = {
            UnBlockCompleteTopBar(
                modifier = Modifier
                    .padding(top = 20.dp, end = 20.dp)
                    .statusBarsPadding(),
                onClickBack = {
                }
            )
        },
        bottomBar = {
            UnBlockCompleteBottomBar(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .navigationBarsPadding(),
                onClickCloseScreen = onClickCloseScreen,
                onNavigateToRecall = onNavigateToRecall
            )
        }
    ) { paddingValues ->
        UnBlockReasonContent(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
fun UnBlockCompleteTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = onClickBack, modifier = Modifier.size(24.dp)) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.Close,
                contentDescription = "ic_back",
                tint = Gray400
            )
        }
    }
}


@Composable
fun UnBlockReasonContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            "아쉽지만 실험을 중단했어요",
            style = LimberTextStyle.Heading1,
            color = Gray800,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "충분히 시간을 가지고\n" +
                    "다음 집중 시간을 가져보아요",
            style = LimberTextStyle.Body2,
            color = Gray600,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.char_sad),
            contentDescription = "ic_star",
            modifier = Modifier.size(280.dp)
        )
    }
}

@Composable
fun UnBlockCompleteBottomBar(
    modifier: Modifier = Modifier,
    onClickCloseScreen: () -> Unit,
    onNavigateToRecall: () -> Unit
) {

    val context = LocalContext.current
    // resValue로 생성된 문자열 리소스 참조
    val buildType = context.getString(com.kkh.multimodule.feature.block.R.string.build_type)
    val isProductBuild = buildType == "product"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        LimberSquareButton(
            onClick = onClickCloseScreen,
            containerColor = Color.White,
            textColor = Gray800,
            text = "화면 닫기",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        LimberSquareButton(
            onClick = onNavigateToRecall,
            text = if (isProductBuild) "새 타이머 시작" else "회고하기",
            modifier = Modifier.weight(1f)
        )

    }
}