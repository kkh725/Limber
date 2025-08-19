package com.kkh.multimodule.limber

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kkh.multimodule.core.accessibility.appinfo.AppInfoProvider

class LimberLifeCycleObserver(private val context: Context) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val installedApps =
            AppInfoProvider.getInstalledAppsWithIcons(context = context)
        for (app in installedApps) {
            println("앱 이름: $app")
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

//        //접근성 관련 권한 체크
//        val isAccessibilityServiceEnabled = PermissionManager.isAccessibilityServiceEnabled(
//            context,
//            BlockedAppAccessibilityService::class.java
//        )
//        if (!isAccessibilityServiceEnabled) {
//            // 권한이 안 켜져 있으면 유도 (딱 한 번만)
//            PermissionManager.openAccessibilitySettings(context)
//        }
//
//        // usageStats관련 권한 체크.
//        val isUsageStatsPermissionGranted = PermissionManager.hasUsageStatsPermission(context)
//        if (!isUsageStatsPermissionGranted){
//            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
//            context.startActivity(intent)
//        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }
}