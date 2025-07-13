package com.kkh.accessibility

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar

object AppUsageStatsManager {

    // 앱 별 사용시간 리스트.
    // totalTimeInForeGround 에서 많이 사용한 순으로 탑10, 중복제거.
    fun getTodayUsageStats(context: Context): List<UsageStats> {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val calendar = Calendar.getInstance()

        // 오늘의 끝 시간 = 현재 시각
        val endTime = calendar.timeInMillis

        // 오늘의 시작 시간 = 00:00:00.000
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis

        // 하루치 사용 기록 조회
        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return usageStatsList
    }

    fun getTodayUsageForApp(context: Context, packageName: String): Long {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis

        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        if (usageStatsList.isNullOrEmpty()) {
            return 0L
        }

        val targetStat = usageStatsList.find { it.packageName == packageName }
        return targetStat?.totalTimeInForeground ?: 0L
    }


    // 총 사용 시간 합산.(하루)
    fun getTotalUsageStats(context: Context): Map<String, Long> {
        val usageStatsList = getTodayUsageStats(context)
        val usageMap = mutableMapOf<String, Long>()

        usageStatsList.forEach { usageStats ->
            val currentTime = usageMap.getOrDefault(usageStats.packageName, 0L)
            usageMap[usageStats.packageName] = currentTime + usageStats.totalTimeInForeground
        }

        return usageMap
    }

    fun formatUsageTime(millis: Long): String {
        val totalMinutes = millis / (1000 * 60)
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return when {
            hours > 0 && minutes > 0 -> "${hours}시간 ${minutes}분"
            hours > 0 -> "${hours}시간"
            else -> "${minutes}분"
        }
    }
}