package com.kkh.multimodule.core.ui.util

import android.annotation.SuppressLint
import android.os.Build
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.LocalTime

@SuppressLint("AnnotateVersionCheck")
fun isAndroid15OrAbove(): Boolean {
    return Build.VERSION.SDK_INT >= 35
}

fun getTodayInKoreanFormat(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN)
    return today.format(formatter)
}

fun getTodayDayOfWeek(): String {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek
    return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)  // 예: "월", "화"
}

fun getCurrentTimeInKoreanFormat(): String {
    val now = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.KOREAN)
    return now.format(formatter)
}

fun decrementOneSecond(timeStr: String): String {
    val timeParts = timeStr.split(":").map { it.toIntOrNull() ?: 0 }
    if (timeParts.size != 3) return timeStr
    var (h, m, s) = timeParts
    var totalSec = h * 3600 + m * 60 + s
    if (totalSec <= 0) return "00:00:00"
    totalSec -= 1
    val nh = totalSec / 3600
    val nm = (totalSec % 3600) / 60
    val ns = totalSec % 60
    return "%02d:%02d:%02d".format(nh, nm, ns)
}
