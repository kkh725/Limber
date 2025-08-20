package com.kkh.multimodule.core.accessibility.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import com.kkh.multimodule.core.accessibility.R
import kotlin.jvm.java
import androidx.core.net.toUri
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.timer

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timerRepository: TimerRepository,
    private val historyRepository: HistoryRepository
){

    private val channelId = "timer_channel_id"
    private val channelName = "Timer Notifications"
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

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

    fun showTerminateTimerNotification() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        coroutineScope.launch {
            val currentTimerId =  timerRepository.getActiveTimerId()
            historyRepository.getLatestHistoryId(currentTimerId)
                .onSuccess {
                    // 알림 클릭 시 열릴 Activity 지정
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "limber://recall?timerId=${currentTimerId}&timerHistoryId=${it}".toUri(),
                    )
                    val pendingIntent = PendingIntent.getActivity(
                        context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    val notification = NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_app)
                        .setContentTitle("집중 실험이 종료되었어요!")
                        .setContentText("이번 실험에 얼마나 몰입하셨나요? 회고를 작성하며 실험을 복기해봐요.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent) // ✅ 알림 클릭 시 이동
                        .build()

                    notificationManager.notify(1,notification)
                }.onFailure {
                    Log.e("TAG", "가장 최근 타이머 이력 아이디조회실패", )
                    // 메인화면으로 이동
                    val uri = "limber://app".toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    val pendingIntent = PendingIntent.getActivity(
                        context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    val notification = NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_app)
                        .setContentTitle("집중 실험이 종료되었어요!!")
//                        .setContentText("메인 화면으로 이동할게요.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent) // ✅ 알림 클릭 시 이동
                        .setAutoCancel(true)
                        .build()
                    notificationManager.notify(1,notification)
                }
        }
    }
}
