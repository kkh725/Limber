package com.kkh.multimodule.core.domain

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    val now = LocalTime.now().plusMinutes(3)  // 현재 시각
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return now.format(formatter)
}

fun getTimePlus30Minutes(): String {
    val now = LocalTime.now()               // 현재 시각
    val plus30 = now.plusMinutes(30)       // 30분 추가
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return plus30.format(formatter)
}

fun replaceIdToString(id : Int) : String{
    return when (id) {
        0 -> FailReason.LACK_OF_FOCUS_INTENTION
        1 -> FailReason.NEED_BREAK
        2 -> FailReason.FINISHED_EARLY
        3 -> FailReason.EMERGENCY
        4 -> FailReason.EXTERNAL_DISTURBANCE
        else -> FailReason.NONE
    }.toString()
}



