package com.kkh.accessibility

import android.content.Context
import android.content.pm.PackageManager

object AppInfoProvider {

    // 앱 정보 가져오기.
    fun getInstalledAppsWithIcons(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList = mutableListOf<AppInfo>()
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
}