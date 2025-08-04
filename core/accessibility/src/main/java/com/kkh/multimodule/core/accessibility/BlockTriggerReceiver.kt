package com.kkh.multimodule.core.accessibility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import dagger.hilt.android.EntryPointAccessors
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.java

//class BlockTriggerReceiver : BroadcastReceiver() {
//    @Inject
//    lateinit var appDataRepository: AppDataRepository
//
//    override fun onReceive(context: Context, intent: Intent) {
//        // 수동으로 의존성 주입 (Hilt)
//        val entryPoint = EntryPointAccessors.fromApplication(
//            context.applicationContext,
//            AppDataRepositoryEntryPoint::class.java
//        )
//        appDataRepository = entryPoint.getAppDataRepository()
//
//        val reservationId = intent.getIntExtra("reservation_id", -1)
//        val isStartTrigger = intent.getBooleanExtra("is_start_trigger", true)
//
//        if (reservationId != -2) {
//            CoroutineScope(Dispatchers.IO).launch {
//                if (isStartTrigger) {
//                    appDataRepository.setBlockMode(true)
//                } else {
//                    appDataRepository.setBlockMode(false)
//                }
//            }
//        }
//    }
//}