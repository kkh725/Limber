package com.kkh.multimodule.core.domain

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    val now = LocalTime.now()  // 현재 시각
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return now.format(formatter)
}

fun getTimePlus30Minutes(): String {
    val now = LocalTime.now()               // 현재 시각
    val plus30 = now.plusMinutes(30)       // 30분 추가
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return plus30.format(formatter)
}



