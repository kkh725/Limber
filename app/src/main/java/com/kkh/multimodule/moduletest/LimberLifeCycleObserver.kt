package com.kkh.multimodule.moduletest

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.BlockedAppAccessibilityService
import com.kkh.accessibility.PermissionManager
import java.util.Date

class LimberLifeCycleObserver(private val context: Context) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val installedApps =
            AppInfoProvider.getInstalledAppsWithIcons(context = context)
        for (app in installedApps) {
            println("앱 이름: ${app}")
        }

        val statsList = AppUsageStatsManager.getMonthlyUsageStats(context)
        statsList.forEach { usageStats ->
            if (usageStats.totalTimeInForeground > 0){
                Log.d("UsageStats",
                    "패키지명: ${usageStats.packageName}, " +
                            "사용시간(ms): ${usageStats.totalTimeInForeground}, " +
                            "시작시간: ${Date(usageStats.firstTimeStamp)}, " +
                            "종료시간: ${Date(usageStats.lastTimeStamp)}, " +
                            "마지막사용: ${Date(usageStats.lastTimeUsed)}"
                )
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        //접근성 관련 권한 체크
        val isAccessibilityServiceEnabled = PermissionManager.isAccessibilityServiceEnabled(
            context,
            BlockedAppAccessibilityService::class.java
        )
        if (!isAccessibilityServiceEnabled) {
            // 권한이 안 켜져 있으면 유도 (딱 한 번만)
            PermissionManager.openAccessibilitySettings(context)
        }

        // usageStats관련 권한 체크.
        val isUsageStatsPermissionGranted = PermissionManager.hasUsageStatsPermission(context)
        if (!isUsageStatsPermissionGranted){
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }
}