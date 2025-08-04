package com.kkh.multimodule.core.accessibility

import android.accessibilityservice.AccessibilityService
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.core.net.toUri
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.jvm.java

//접근성 서비스가 켜져 있는 동안 (접근성 설정 메뉴에서 내 서비스가 활성화된 상태)
//시스템은 계속해서 onAccessibilityEvent()를 호출해서 이벤트를 전달합니다.
//따라서, 앱이 백그라운드에 있든 포그라운드에 있든 상관없이 blockApp() 같은 차단 로직이 지속적으로 동작.
//시스템에서 백그라운드로 계속 유지해준다.
@AndroidEntryPoint
class BlockedAppAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var appDataRepository: AppDataRepository
    @Inject
    lateinit var blockReservationRepository: BlockReservationRepository

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val isBlockedState = MutableStateFlow(false)
    private val blockedPackageListState = MutableStateFlow(listOf<String>())

    // BroadcastReceiver를 object로 중첩 정의
    object BlockTriggerReceiver : BroadcastReceiver() {
        // 외부 클래스 인스턴스를 참조할 수 없으므로
        // 외부 서비스 인스턴스를 참조하도록 lateinit으로 둔다
        lateinit var serviceInstance: BlockedAppAccessibilityService

        override fun onReceive(context: Context, intent: Intent) {
            val reservationId = intent.getIntExtra("reservation_id", -1)
            val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)

            // serviceInstance의 appDataRepository를 사용
            if (reservationId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (isStartTrigger) {
                        serviceInstance.appDataRepository.setBlockMode(true)
                    } else {
                        serviceInstance.appDataRepository.setBlockMode(false)
                    }
                }
            }
        }
    }


    private fun scheduleStartBlockTrigger(reservation: ReservationItemModel) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, BlockTriggerReceiver::class.java).apply {
            putExtra("reservation_id", reservation.id)
            putExtra("is_start_trigger", true) // 시작 트리거 표시
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            reservation.id * 2, // 고유한 requestCode로 충돌 방지
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val startTime = LocalTime.parse(reservation.reservationInfo.startTime, DateTimeFormatter.ofPattern("HH:mm"))
        val triggerTime = startTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    private fun scheduleEndBlockTrigger(reservation: ReservationItemModel) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, BlockTriggerReceiver::class.java).apply {
            putExtra("reservation_id", reservation.id)
            putExtra("is_start_trigger", false) // 종료 트리거 표시
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            reservation.id * 2 + 1, // start와 구분되는 requestCode
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val endTime = LocalTime.parse(reservation.reservationInfo.endTime, DateTimeFormatter.ofPattern("HH:mm"))
        val triggerTime = endTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    override fun onCreate() {
        super.onCreate()

        coroutineScope.launch {
            appDataRepository.observeBlockMode().collect { newState ->
                blockedPackageListState.value = appDataRepository.getBlockedPackageList()
                isBlockedState.value = newState
            }
        }

        coroutineScope.launch {
            blockReservationRepository.observeReservationList().collect { reservationList ->
                Log.d("TAG", "saveReservationInfoList: 방출 완")
                reservationList.filter { it.isToggleChecked }.forEach { reservation ->
                    scheduleStartBlockTrigger(reservation)
                    scheduleEndBlockTrigger(reservation)
                }
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isBlockedState.value) return
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