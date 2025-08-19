package com.kkh.multimodule.feature.onboarding.contents.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.kkh.multimodule.core.accessibility.block.BlockedAppAccessibilityService
import com.kkh.multimodule.core.accessibility.permission.PermissionManager
import com.kkh.multimodule.core.accessibility.permission.PermissionManager.openAccessibilitySettings
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberAnimation
import com.kkh.multimodule.core.ui.ui.component.LimberBackButton
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import kotlinx.coroutines.delay

@Composable
fun AccessPermissionScreen(navigateToAlertPermission: () -> Unit) {

    val context = LocalContext.current
    var isRequestingPermission by remember { mutableStateOf(false) }

    var hasPermission by remember {
        mutableStateOf(
            PermissionManager.isAccessibilityServiceEnabled(
                context = context,
                service = BlockedAppAccessibilityService::class.java
            )
        )
    }

    var animationPlaying by remember { mutableStateOf(false) }

    // app이 세팅창으로 갔다가 돌아오면 재검사.
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        if (isRequestingPermission) {
            hasPermission = PermissionManager.isAccessibilityServiceEnabled(
                context = context,
                service = BlockedAppAccessibilityService::class.java
            )
        }
    }

    // permission이 있다면 다음페이지로 이동
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            animationPlaying = true
            delay(1000)
            animationPlaying = false
            navigateToAlertPermission()
        }
    }

    Box(Modifier
        .fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                Modifier
                    .height(84.dp)
            )

            LimberProgressBar(0.4f)

            Spacer(Modifier.height(40.dp))
            Text(
                "방해되는 앱을 차단하기 위해\n" +
                        "접근성 허용이 필요해요",
                textAlign = TextAlign.Center,
                style = LimberTextStyle.Heading3,
                color = Gray800
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "권한에 동의해야 림버를 제대로 사용할 수 있어요", style = LimberTextStyle.Body2, color = Gray600
            )
            Spacer(Modifier.height(40.dp))
            PermissionBox(headText = "접근성 허용", bodyText = "앱 차단 허용")
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                LimberGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (!hasPermission) {
                            openAccessibilitySettings(context)
                            isRequestingPermission = true
                        }
                    },
                    text = "동의하기"
                )
            }
            Spacer(Modifier.height(20.dp))
        }

    }
    // Show animation if playing
    if (animationPlaying) {
        Dialog(onDismissRequest = { /* no-op, 또는 로딩중에는 막기 */ }) {
            LimberAnimation(
                modifier = Modifier
                    .size(50.dp),
                resId = R.raw.loading_dark
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AccessPermissionScreenPreview() {
    AccessPermissionScreen({})
}

@Composable
fun TopBar(modifier: Modifier = Modifier, onClickBack: () -> Unit = {}) {
    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LimberBackButton(onClick = onClickBack)
        TextButton({}, enabled = false, contentPadding = PaddingValues(0.dp)) {}
    }
}

@Composable
fun LimberProgressBar(percentage: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(color = LimberColorStyle.Gray200, shape = RoundedCornerShape(100.dp)) // 배경 바
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(percentage.coerceIn(0f, 1f)) // 퍼센트만큼 채움
                .background(
                    color = LimberColorStyle.Primary_Main,
                    shape = RoundedCornerShape(100.dp)
                )
        )
    }
}

@Composable
fun PermissionBox(headText: String, bodyText: String, imgResId : Int = R.drawable.ic_data) {
    Box(
        Modifier
            .fillMaxWidth()
            .border(1.dp, color = Gray300, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(imgResId), contentDescription = "info")
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    headText, style = LimberTextStyle.Heading4, color = Gray800
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    bodyText, style = LimberTextStyle.Body2, color = Gray500
                )
            }
        }
    }
}
