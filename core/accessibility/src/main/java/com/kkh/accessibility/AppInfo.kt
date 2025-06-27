package com.kkh.accessibility

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val appIcon : Drawable?,
    val usageTime: String = ""
){
    companion object{
        val empty = AppInfo("", "", null)
    }
}