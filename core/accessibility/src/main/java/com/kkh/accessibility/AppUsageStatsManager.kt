package com.kkh.accessibility

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar

object AppUsageStatsManager {

    // 앱 별 사용시간 리스트.
    fun getMonthlyUsageStats(context: Context): List<UsageStats> {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis

        calendar.add(Calendar.MONTH, -1)  // 한 달 전
        val startTime = calendar.timeInMillis

        // 기간 내 UsageStats 리스트 가져오기
        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        // 빈 리스트가 올 수 있으니 체크
        if (usageStatsList.isNullOrEmpty()) {
            // 권한이 없거나 데이터가 없는 상태
            return emptyList()
        }

        // 사용자가 사용한 앱들의 UsageStats 반환
        // 패키지명, 사용시간, 시작시간, 종료시간, 마지막사용
        return usageStatsList
    }

    // 총 사용 시간 합산.(1달)
    fun getMonthlyAppUsage(context: Context): Map<String, Long> {
        val usageStatsList = getMonthlyUsageStats(context)
        val usageMap = mutableMapOf<String, Long>()

        usageStatsList.forEach { usageStats ->
            val currentTime = usageMap.getOrDefault(usageStats.packageName, 0L)
            usageMap[usageStats.packageName] = currentTime + usageStats.totalTimeInForeground
        }

        return usageMap
    }

}