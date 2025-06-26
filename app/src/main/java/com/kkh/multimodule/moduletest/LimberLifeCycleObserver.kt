package com.kkh.multimodule.moduletest

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kkh.accessibility.BlockedAppAccessibilityService
import com.kkh.accessibility.PermissionManager

class LimberLifeCycleObserver(private val context: Context) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val installedApps =
            BlockedAppAccessibilityService().getInstalledAppsWithNames(context = context)
        for (app in installedApps) {
            println("앱 이름: ${app.appName}, 패키지명: ${app.packageName}")
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        val isEnabled = PermissionManager.isAccessibilityServiceEnabled(
            context,
            BlockedAppAccessibilityService::class.java
        )
        if (!isEnabled) {
            // 권한이 안 켜져 있으면 유도 (딱 한 번만)
            PermissionManager.openAccessibilitySettings(context)
        } else {
            // 권한 활성화됨, 정상 진행
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }
}