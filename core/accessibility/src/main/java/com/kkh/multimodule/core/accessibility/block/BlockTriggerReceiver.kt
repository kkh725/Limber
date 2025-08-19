package com.kkh.multimodule.core.accessibility.block

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository

@AndroidEntryPoint
class BlockTriggerReceiver : BroadcastReceiver() {
    @Inject
    lateinit var appDataRepository: AppDataRepository
    @Inject
    lateinit var timerRepository: TimerRepository

    override fun onReceive(context: Context, intent: Intent) {
        val reservationId = intent.getIntExtra("reservation_id", -1)
        val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)
        if (reservationId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("TAG", "set block mode on $isStartTrigger")

                appDataRepository.setBlockMode(isStartTrigger)
                // 시작으로 recieve 되었다면 현재 진행중인 타이머 아이디 저장, else -1(초기화)
                if (isStartTrigger) {
                    timerRepository.setActiveTimerId(reservationId)
                    Log.d("ScheduleBlockTrigger", "시작된 타이머 id : $reservationId")
                }
            }
        }
    }
}
