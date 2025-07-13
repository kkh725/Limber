package com.kkh.accessibility

import android.app.usage.UsageStats
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.kkh.accessibility.AppUsageStatsManager.getTodayUsageStats

object AppInfoProvider {

    // 앱 정보 가져오기.
    fun getInstalledAppsWithIcons(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList = mutableListOf<com.kkh.accessibility.AppInfo>()
        for (packageInfo in packages) {
            // 런처에 표시되는 앱만 필터링
            val launchIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName)
            if (launchIntent != null) {
                val appName = packageManager.getApplicationLabel(packageInfo).toString()
                val packageName = packageInfo.packageName
                val icon = try {
                    packageManager.getApplicationIcon(packageInfo.packageName)
                } catch (e: PackageManager.NameNotFoundException) {
                    null // 또는 기본 아이콘 대체
                }
                appList.add(AppInfo(appName, packageName, icon))
            }
        }
        return appList
    }

    // appIcon 출력
    fun getAppIcon(context: Context, packageName: String): Drawable? {
        val packageManager = context.packageManager
        val icon = try {
            packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            null // 또는 기본 아이콘 대체
        }
        return icon
    }

    // app label 출력
    fun getAppLabel(context: Context, packageName: String): String? {
        return try {
            val packageManager = context.packageManager
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null  // 패키지를 찾을 수 없는 경우
        }
    }

    // app usage stats -> app info convert
    fun convertUsageStatsToAppInfo(context: Context, usageStat: UsageStats): AppInfo {
        return AppInfo(
            appName = getAppLabel(context, usageStat.packageName) ?: "Unknown",
            packageName = usageStat.packageName,
            appIcon = getAppIcon(context, usageStat.packageName),
            usageTime = AppUsageStatsManager.formatUsageTime(usageStat.totalTimeInForeground)
        )
    }

    // 하루의 앱 usage를 appInfo로 변환하여 출력.
    fun getTodayUsageAppInfoList(context: Context): List<AppInfo> {
        return getTodayUsageStats(context)
            .filter { it.totalTimeInForeground > 0 }
            .distinctBy { it.packageName }
            .sortedByDescending { it.totalTimeInForeground }
            .take(10)
            .map { convertUsageStatsToAppInfo(context, it) }
    }
}