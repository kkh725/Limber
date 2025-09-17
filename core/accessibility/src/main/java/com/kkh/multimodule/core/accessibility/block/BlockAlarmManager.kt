package com.kkh.multimodule.core.accessibility.block

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object BlockAlarmManager {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    fun scheduleBlockTrigger(
        context: Context,
        reservation: ReservationItemModel,
        isStart: Boolean
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        Log.i("TAG", "scheduleBlockTrigger: ${reservation}")

        // 실제 블록 모드 트리거
        val triggerTime = calculateTriggerTime(reservation, isStart)
        val triggerIntent =
            createIntent(context, reservation.id, isStart, BlockTriggerReceiver::class.java)
        val triggerPendingIntent =
            createPendingIntent(context, reservation.id, isStart, triggerIntent)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            triggerPendingIntent
        )
//
//        // 5분 전 알림 트리거 5분전 알림 없앰.
//        val notificationTime = calculateNotificationTime(reservation, isStart)
//        val notificationIntent =
//            createIntent(context, reservation.id, isStart, NotificationReceiver::class.java)
//        val notificationPendingIntent =
//            createPendingIntent(context, reservation.id, isStart, notificationIntent)
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            notificationTime,
//            notificationPendingIntent
//        )

//        Log.d(
//            "ScheduleBlockTrigger", "예약 ID: ${reservation.id}, 시작 여부: $isStart, " +
//                    "블록 트리거: $triggerTime, 알림 트리거: $notificationTime"
//        )
    }

    private fun calculateTriggerTime(reservation: ReservationItemModel, isStart: Boolean): Long {
        val nowDate = LocalDate.now()
        val startTime = LocalTime.parse(reservation.reservationInfo.startTime, formatter)
        val endTime = LocalTime.parse(reservation.reservationInfo.endTime, formatter)

        val triggerDateTime = if (isStart) {
            startTime.atDate(nowDate)
        } else {
            val endDate = if (endTime <= startTime) nowDate.plusDays(1) else nowDate
            endTime.atDate(endDate)
        }

        return triggerDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun calculateNotificationTime(
        reservation: ReservationItemModel,
        isStart: Boolean
    ): Long {
        val nowDate = LocalDate.now()
        val startTime = LocalTime.parse(reservation.reservationInfo.startTime, formatter)
        val endTime = LocalTime.parse(reservation.reservationInfo.endTime, formatter)

        val notificationDateTime = if (isStart) {
            startTime.minusMinutes(5).atDate(nowDate)
        } else {
            val endDate = if (endTime <= startTime) nowDate.plusDays(1) else nowDate
            endTime.minusMinutes(5).atDate(endDate)
        }

        return notificationDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun createIntent(
        context: Context,
        reservationId: Int,
        isStart: Boolean,
        clazz: Class<out BroadcastReceiver>
    ): Intent {
        return Intent(context, clazz).apply {
            putExtra("reservation_id", reservationId)
            putExtra("is_start_trigger", isStart)
        }
    }

    private fun createPendingIntent(
        context: Context,
        reservationId: Int,
        isStart: Boolean,
        intent: Intent
    ): PendingIntent {
        val requestCode = if (isStart) reservationId * 2 else reservationId * 2 + 1
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}

