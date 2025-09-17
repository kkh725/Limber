package com.kkh.multimodule.feature.onboarding.contents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.core.ui.ui.component.RegisterBlockAppBottomSheet
import com.kkh.multimodule.feature.onboarding.OnboardingEvent
import com.kkh.multimodule.feature.onboarding.OnboardingEvent.OnCompleteRegisterButton
import com.kkh.multimodule.feature.onboarding.OnboardingViewModel
import com.kkh.multimodule.feature.onboarding.contents.permission.LimberProgressBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAppScreen(navigateToStart : () -> Unit) {

    var isSheetVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val appList = uiState.usageAppInfoList

    var checkedList by remember {
        mutableStateOf(List(appList.size) { false })
    }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(OnboardingEvent.OnEnterScreen(context))

    }

    LaunchedEffect(appList) {
        checkedList = List(appList.size) { false }
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
            .height(84.dp))

        LimberProgressBar(0.8f)
        Spacer(Modifier.height(40.dp))
        Text(
            "집중을 방해하는 앱을\n" +
                    "등록해주세요",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "최대 10개까지 등록할 수 있으며 언제든 변경 가능해요", style = LimberTextStyle.Body2, color = Gray600
        )
        Spacer(Modifier.height(120.dp))

        Image(painter = painterResource(R.drawable.bg_manage_app), contentDescription = null)
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
                navigateToStart()
            },
            appList = appList,
            checkedList = checkedList,
            onCheckClicked = { index ->
                checkedList = checkedList.toMutableList().also {
                    it[index] = !it[index]
                }
            }
        )
    }
}

@Preview
@Composable
fun ManageAppScreenPreview() {
    ManageAppScreen({})
}