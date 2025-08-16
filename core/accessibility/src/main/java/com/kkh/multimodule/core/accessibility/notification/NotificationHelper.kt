package com.kkh.multimodule.core.accessibility.notification

import android.R.attr.description
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kkh.multimodule.core.accessibility.R

class NotificationHelper(private val context: Context) {

    private val channelId = "timer_channel_id"
    private val channelName = "Timer Notifications"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "타이머 알림 채널"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showTimerNotification(reservationId: Int, isStart: Boolean) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(if (isStart) "타이머가 곧 시작됩니다!" else "타이머가 종료되었어요!")
            .setContentText("예약된 타이머 #$reservationId ${if (isStart) "시작" else "종료"}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(reservationId, notification)
    }
}
