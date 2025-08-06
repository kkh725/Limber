package com.kkh.multimodule.core.accessibility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object BlockAlarmManager {
    fun scheduleBlockTrigger(
        context: Context,
        reservation: ReservationItemModel,
        isStart: Boolean
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, BlockTriggerReceiver::class.java).apply {
            putExtra("reservation_id", reservation.id)
            putExtra("is_start_trigger", isStart)
        }

        val requestCode = if (isStart) reservation.id * 2 else reservation.id * 2 + 1
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        val nowDate = LocalDate.now()
        val startTime = LocalTime.parse(reservation.reservationInfo.startTime, formatter)
        val endTime = LocalTime.parse(reservation.reservationInfo.endTime, formatter)

        val triggerTime = if (isStart) {
            startTime.atDate(nowDate)
        } else {
            // endTime이 startTime보다 이르면 다음날로 판단
            val endDate = if (endTime <= startTime) nowDate.plusDays(1) else nowDate
            endTime.atDate(endDate)
        }

        val triggerMillis = triggerTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.d("TAG", "isStart: ${isStart}, triggerTime: ${triggerMillis}")

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerMillis,
            pendingIntent
        )
    }
}
