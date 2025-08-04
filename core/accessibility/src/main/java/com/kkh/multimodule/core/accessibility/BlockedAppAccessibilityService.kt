package com.kkh.multimodule.core.accessibility

import android.accessibilityservice.AccessibilityService
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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
    private val isTimeBlockedState = MutableStateFlow(false)
    private val blockedPackageListState = MutableStateFlow(listOf<String>())
    private val blockedReservationListState = MutableStateFlow(listOf<ReservationItemModel>())
    private val currentBlockedId = MutableStateFlow<Int>(-1)

    // 예약 정보 안에 현재 시각 유무
    private fun isCurrentTimeInAnyReservation(): Boolean {
        val now = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val currentReservation = blockedReservationListState.value
            .filter { it.isToggleChecked } // 토글 켜진 예약만 필터
            .firstOrNull() { reservation ->
                val startStr = reservation.reservationInfo.startTime.replace(" ", "")
                val endStr = reservation.reservationInfo.endTime.replace(" ", "")
                val start = LocalTime.parse(startStr, formatter)
                val end = LocalTime.parse(endStr, formatter)

                if (end.isAfter(start) || end == start) {
                    !now.isBefore(start) && !now.isAfter(end)
                } else {
                    !now.isBefore(start) || !now.isAfter(end)
                }
            }
        currentReservation?.let {
            currentBlockedId.value = it.id
        }

        return currentReservation != null
    }


    override fun onCreate() {
        super.onCreate()
        // blockMode 관찰
        coroutineScope.launch {
            appDataRepository.observeBlockMode().collect { newState ->
                isBlockedState.value = newState
            }
        }
        // blockedPackage 변화 관찰
        coroutineScope.launch {
            appDataRepository.observeBlockedPackageList().collect { packageList ->
                Log.d("TAG", "packageList: ${packageList.firstOrNull()}")
                blockedPackageListState.value = packageList
            }
        }
        // blocked 예약 정보 변화 관찰
        coroutineScope.launch {
            blockReservationRepository.observeReservationList().collect { reservationList ->
                blockedReservationListState.value = reservationList
            }
        }
        // 매 초마다 예약시간 내에 있는지 검사.
        coroutineScope.launch {
            while (isActive) {
                isTimeBlockedState.value = isCurrentTimeInAnyReservation()
                delay(1000L) // 1초마다 검사
            }
        }
        // 예약시간이 종료되었다면 토글 값을 false로 변경.
        coroutineScope.launch {
            isTimeBlockedState.collect {
                val oldList = blockReservationRepository.getReservationList()

                if (currentBlockedId.value != -1) {
                    val newList = oldList.map { reservation ->
                        if (reservation.id == currentBlockedId.value) {
                            // 토글 값을 false로 변경한 새 객체 반환 (데이터 클래스라면 copy() 사용)
                            reservation.copy(isToggleChecked = false)
                        } else {
                            reservation
                        }
                    }
                    blockReservationRepository.setReservationList(newList)
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