package com.kkh.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.util.Log

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
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "접근성 설정 화면 열기 실패", e)
        }
    }
}