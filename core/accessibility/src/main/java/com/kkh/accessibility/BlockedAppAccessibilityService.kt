package com.kkh.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
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

    private var lastBlockedTime = 0L
    private var lastBlockedPackage: String? = null

    private fun blockApp(packageName: String) {
        val now = System.currentTimeMillis()

        // 1초 안에 같은 앱을 두 번 차단하지 않도록
        if (packageName == lastBlockedPackage && now - lastBlockedTime < 1000L) return

        lastBlockedPackage = packageName
        lastBlockedTime = now

        // 차단 실행
        val uri = "limber://block".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)

        Toast.makeText(this, "$packageName 사용이 차단되었습니다", Toast.LENGTH_SHORT).show()
    }


    override fun onInterrupt() {
    }


}