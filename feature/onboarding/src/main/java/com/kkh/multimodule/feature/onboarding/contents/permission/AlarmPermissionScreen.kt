package com.kkh.multimodule.feature.onboarding.contents.permission

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.kkh.multimodule.core.accessibility.permission.PermissionManager
import com.kkh.multimodule.core.accessibility.permission.PermissionManager.openExactAlarmSettings
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberAnimation
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import kotlinx.coroutines.delay
@Composable
fun AlarmPermissionScreen(navigateToManageApp: () -> Unit) {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var isRequestingPermission by remember { mutableStateOf(false) }
    var hasAlarmPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true // Android 11 이하에서는 권한 필요 없음
            }
        )
    }

    // 권한 요청 launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                isRequestingPermission = false // 권한 허용 시 실행
            } else {
                // 거부 시 처리 (SnackBar 등)
            }
        }
    )
    LaunchedEffect(Unit) {
        PermissionManager.requestNotificationPermission(
            context = context,
            requestPermissionLauncher = requestPermissionLauncher,
            onRequestFinished = {
                isRequestingPermission = it
            }
        )
    }
    var animationPlaying by remember { mutableStateOf(false) }

    // 앱이 설정 화면에서 돌아왔을 때 권한 재확인
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        if (isRequestingPermission) {
            isRequestingPermission = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                hasAlarmPermission = alarmManager.canScheduleExactAlarms()
            }
        }
    }

    // permission이 있다면 다음페이지로 이동
    LaunchedEffect(hasAlarmPermission) {
        if (hasAlarmPermission) {
            animationPlaying = true
            delay(1000)
            animationPlaying = false
            navigateToManageApp()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                Modifier
                    .height(84.dp)
            )

            LimberProgressBar(0.6f)

            Spacer(Modifier.height(40.dp))
            Text(
                "알림을 수신받으려면\n" +
                        "알림 허용이 필요해요",
                textAlign = TextAlign.Center,
                style = LimberTextStyle.Heading3,
                color = Gray800
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "권한에 동의해야 림버를 제대로 사용할 수 있어요", style = LimberTextStyle.Body2, color = Gray600
            )
            Spacer(Modifier.height(40.dp))
            PermissionBox(
                headText = "알림 허용",
                bodyText = "앱 차단 허용",
                imgResId = R.drawable.ic_permission_alert
            )
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                LimberGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (!hasAlarmPermission) {
                            openExactAlarmSettings(context)
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
