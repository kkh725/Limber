package com.kkh.multimodule.feature.onboarding.contents.permission

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.kkh.multimodule.core.accessibility.PermissionManager.hasUsageStatsPermission
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberAnimation
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
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

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(Modifier
                .padding(vertical = 18.dp), onClickBack = {})

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
            PermissionBox(headText = "스크린타임 데이터", bodyText = "앱 별 사용 시간 조회")
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

@Preview(showBackground = true)
@Composable
fun ScreenTimePermissionScreenPreview() {
    ScreenTimePermissionScreen()
}