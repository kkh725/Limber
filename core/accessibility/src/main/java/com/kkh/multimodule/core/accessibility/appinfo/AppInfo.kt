package com.kkh.multimodule.core.accessibility.appinfo

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val appIcon : Drawable?,
    val usageTime: String = ""
){
    companion object{
        val empty = AppInfo("", "", null)
        val mockList = listOf(
            AppInfo("인스타그램", "com.app1", null, "30분"),
            AppInfo("유튜브", "com.app2", null, "45분"),
            AppInfo("카카오톡", "com.app3", null, "1시간"),
            AppInfo("인스타그램", "com.app1", null, "30분"),
            AppInfo("유튜브", "com.app2", null, "45분"),
            AppInfo("카카오톡", "com.app3", null, "1시간"),
            AppInfo("인스타그램", "com.app1", null, "30분"),
            AppInfo("유튜브", "com.app2", null, "45분"),
            AppInfo("카카오톡", "com.app3", null, "1시간")
        )
    }
}