package com.kkh.permission

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberGradientButton
import com.kkh.multimodule.ui.component.RegisterBlockAppBottomSheet
import com.kkh.onboarding.OnboardingEvent
import com.kkh.onboarding.OnboardingEvent.OnCompleteRegisterButton
import com.kkh.onboarding.OnboardingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAppScreen(navigateToSelectType : () -> Unit) {

    var isSheetVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val appList = uiState.usageAppInfoList

    LaunchedEffect(appList) {
        if (appList.isNotEmpty()) {
            Log.d("ManageAppScreen", "appList updated: ${appList.size}")
            isSheetVisible = true
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier
            .height(84.dp)
            .systemBarsPadding())

        LimberProgressBar(0.7f)
        Spacer(Modifier.height(40.dp))
        Text(
            "림버를 통해\n" +
                    "관리할 앱을 등록해주세요",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "최대 10개까지 등록할 수 있으며 언제든 변경 가능해요", style = LimberTextStyle.Body2, color = Gray600
        )
        Spacer(Modifier.height(40.dp))

        Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LimberGradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.sendEvent(OnboardingEvent.OnClickRegisterButton(context = context))
                },
                text = "앱 등록하기"
            )
        }
        Spacer(Modifier.height(20.dp))
    }

    if (isSheetVisible) {
        RegisterBlockAppBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetVisible = false },
            onClickComplete = { checkedAppList ->
                isSheetVisible = false
                viewModel.sendEvent(OnCompleteRegisterButton(checkedAppList))
                navigateToSelectType()
            },
            appList = appList,
        )
    }
}

@Preview
@Composable
fun ManageAppScreenPreview() {
    ManageAppScreen({})
}