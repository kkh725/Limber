package com.kkh.permission

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.kkh.accessibility.BlockedAppAccessibilityService
import com.kkh.accessibility.PermissionManager
import com.kkh.accessibility.PermissionManager.hasUsageStatsPermission
import com.kkh.accessibility.PermissionManager.openAccessibilitySettings
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberAnimation
import com.kkh.multimodule.ui.component.LimberGradientButton
import kotlinx.coroutines.delay


@Composable
fun ScreenTimePermissionScreen(navigateToAccessPermission: () -> Unit = {}) {

    val context = LocalContext.current
    var isRequestingPermission by remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(hasUsageStatsPermission(context)) }
    var animationPlaying by remember { mutableStateOf(false) }

    // app이 세팅창으로 갔다가 돌아오면 재검사.
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        if (isRequestingPermission) {
            hasPermission = hasUsageStatsPermission(context)
        }
    }

    // permission이 있다면 다음페이지로 이동
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            animationPlaying = true
            delay(1000)
            animationPlaying = false
            navigateToAccessPermission()
        }
    }

    Box(Modifier.fillMaxSize().systemBarsPadding()){
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
//        TopBar(Modifier.padding(vertical = 20.dp).systemBarsPadding(), onClickBack = {})
            Spacer(Modifier
                .height(84.dp)
                .systemBarsPadding())

            LimberProgressBar(0.2f)

            Spacer(Modifier.height(40.dp))
            Text(
                "앱 사용 시간을 조회하기 위해\n" +
                        "스크린타임 데이터가 필요해요",
                textAlign = TextAlign.Center,
                style = LimberTextStyle.Heading3,
                color = Gray800
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "권한에 동의해야 림버를 제대로 사용할 수 있어요", style = LimberTextStyle.Body2, color = Gray600
            )
            Spacer(Modifier.height(40.dp))
            PermissionBox(headText = "스크틴타임 데이터", bodyText = "앱 별 사용 시간 조회")
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                LimberGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // 유저 stats 허용 확인.
                        if (!hasPermission) {
                            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                            context.startActivity(intent)
                            isRequestingPermission = true // 설정으로 갔음을 기록
                        }
                    },
                    text = "동의하기"
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        if (animationPlaying) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))
            LimberAnimation(
                modifier = Modifier.align(Alignment.Center).size(50.dp),
                resId = R.raw.loading_dark
            )
        }
    }
}

@Preview
@Composable
fun ScreenTimePermissionScreenPreview() {
    ScreenTimePermissionScreen()
}