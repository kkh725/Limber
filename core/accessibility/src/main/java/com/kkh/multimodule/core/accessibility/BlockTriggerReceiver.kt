package com.kkh.multimodule.core.accessibility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlockTriggerReceiver : BroadcastReceiver() {
    @Inject
    lateinit var appDataRepository: AppDataRepository

    override fun onReceive(context: Context, intent: Intent) {

        val reservationId = intent.getIntExtra("reservation_id", -1)
        val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)

        if (reservationId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                if (isStartTrigger) {
                    appDataRepository.setBlockMode(true)
                } else {
                    appDataRepository.setBlockMode(false)
                }
            }
        }
    }
}