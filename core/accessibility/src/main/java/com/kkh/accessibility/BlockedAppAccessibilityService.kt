package com.kkh.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import kotlin.apply
import androidx.core.net.toUri

//접근성 서비스가 켜져 있는 동안 (접근성 설정 메뉴에서 내 서비스가 활성화된 상태)
//시스템은 계속해서 onAccessibilityEvent()를 호출해서 이벤트를 전달합니다.
//따라서, 앱이 백그라운드에 있든 포그라운드에 있든 상관없이 blockApp() 같은 차단 로직이 지속적으로 동작.
//시스템에서 백그라운드로 계속 유지해준다.
class BlockedAppAccessibilityService : AccessibilityService() {

    private val blockedPackages = listOf(
        "com.google.android.youtube",  // 유튜브
        "com.instagram.android"       // 인스타그램 등
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        // 특정 조건에 따라서 동작하게끔 구현. -> dataStore를 통해서 전달받는다.
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return

            if (blockedPackages.contains(packageName)) {
                // 앱 차단 로직 실행
                blockApp(packageName)
            }
        }
    }
    // 사용자가 시스템 접근성 설정 메뉴에서 내 서비스를 on 한 경우.
    // 토글을 켜는 경우 한 번 호출됨.
    override fun onServiceConnected() {
        super.onServiceConnected()
        // 초기화 작업
    }

    private fun blockApp(packageName: String) {
        // 차단 방법 1: 홈 화면으로 강제 이동
        val uri = "myapp://home".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)

        // 차단 방법 2: 사용자에게 메시지 표시 (선택사항)
        Toast.makeText(this, "$packageName 사용이 차단되었습니다", Toast.LENGTH_SHORT).show()
    }

    override fun onInterrupt() {
    }

    data class AppInfo(
        val appName: String,
        val packageName: String
    )

    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledAppsWithNames(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList = mutableListOf<AppInfo>()
        for (packageInfo in packages) {
            // 런처에 표시되는 앱만 필터링
            val launchIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName)
            if (launchIntent != null) {
                val appName = packageManager.getApplicationLabel(packageInfo).toString()
                val packageName = packageInfo.packageName
                appList.add(AppInfo(appName, packageName))
            }
        }
        return appList
    }

}