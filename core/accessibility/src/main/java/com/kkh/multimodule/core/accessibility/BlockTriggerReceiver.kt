package com.kkh.multimodule.core.accessibility

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

@AndroidEntryPoint
class BlockTriggerReceiver : BroadcastReceiver() {
    @Inject
    lateinit var appDataRepository: AppDataRepository
    override fun onReceive(context: Context, intent: Intent) {
        val reservationId = intent.getIntExtra("reservation_id", -1)
        val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)
        if (reservationId != -2) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("TAG", "set block mode on $isStartTrigger")

                appDataRepository.setBlockMode(isStartTrigger)
            }
        }
    }
}
