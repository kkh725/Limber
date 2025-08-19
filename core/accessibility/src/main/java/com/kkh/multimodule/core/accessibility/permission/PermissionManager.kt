package com.kkh.multimodule.core.accessibility.permission

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.app.AppOpsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionManager {

    private const val TAG = "PermissionManager"

    /**
     * 접근성 권한이 활성화되어 있는지 확인합니다.
     */
    fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService>): Boolean {
        val expectedComponentName = ComponentName(context, service)
        val enabledServices = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServices ?: "")
        return colonSplitter.any { it.equals(expectedComponentName.flattenToString(), ignoreCase = true) }
    }

    /**
     * 접근성 권한 설정 화면으로 이동합니다.
     */
    fun openAccessibilitySettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                Intent.setFlags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "접근성 설정 화면 열기 실패", e)
        }
    }

    /**
     * 정확한 알람 권한 설정 화면으로 이동합니다.
     */
    fun openExactAlarmSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    Intent.setFlags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e("AlarmPermission", "정확한 알람 설정 화면 열기 실패", e)
            }
        } else {
            Log.i("AlarmPermission", "Android 11 이하에서는 정확한 알람 권한이 필요하지 않습니다.")
        }
    }

    // usageStats 권한 있는지 체크.
    fun hasUsageStatsPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun requestNotificationPermission(
        context: Context,
        requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        onRequestFinished: (granted: Boolean) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onRequestFinished(true)
            }
        } else {
            // Android 12 이하에서는 권한 필요 없음
            onRequestFinished(true)
        }
    }


}