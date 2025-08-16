package com.kkh.multimodule.core.accessibility.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var appDataRepository: AppDataRepository
    @Inject
    lateinit var timerRepository: TimerRepository

    override fun onReceive(context: Context, intent: Intent) {
        val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)

        NotificationHelper(context).showTimerNotification(isStartTrigger)
    }
}