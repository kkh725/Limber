package com.kkh.multimodule.core.ui.util

import android.annotation.SuppressLint
import android.os.Build
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDate

@SuppressLint("AnnotateVersionCheck")
fun isAndroid15OrAbove(): Boolean {
    return Build.VERSION.SDK_INT >= 35
}

fun getTodayInKoreanFormat(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN)
    return today.format(formatter)
}