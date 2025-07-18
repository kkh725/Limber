package com.kkh.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import kotlin.apply
import androidx.core.net.toUri
import com.kkh.multimodule.domain.repository.AppDataRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

//접근성 서비스가 켜져 있는 동안 (접근성 설정 메뉴에서 내 서비스가 활성화된 상태)
//시스템은 계속해서 onAccessibilityEvent()를 호출해서 이벤트를 전달합니다.
//따라서, 앱이 백그라운드에 있든 포그라운드에 있든 상관없이 blockApp() 같은 차단 로직이 지속적으로 동작.
//시스템에서 백그라운드로 계속 유지해준다.
@AndroidEntryPoint
class BlockedAppAccessibilityService : AccessibilityService() {

    @Inject lateinit var appDataRepository: AppDataRepository
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val isBlockedState = MutableStateFlow(true)
    private val blockedPackageListState = MutableStateFlow(listOf<String>())

    override fun onCreate() {
        super.onCreate()
        coroutineScope.launch {
            appDataRepository.observeBlockMode().collect { newState ->
                isBlockedState.value = newState
            }
        }
        coroutineScope.launch {
            appDataRepository.observeBlockedPackageList().collect { packageList ->
                Log.d("TAG", "packageList: ${packageList.firstOrNull()}")
                blockedPackageListState.value = packageList
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        if (!isBlockedState.value) return
        val packageName = event?.packageName?.toString() ?: return
        Log.d("TAG", "packageList:22 ${packageName.firstOrNull()}")

        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (blockedPackageListState.value.contains(packageName)) {
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



    override fun onDestroy() {
        coroutineScope.cancel()
    }


    override fun onInterrupt() {
    }


}