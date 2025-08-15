package com.kkh.multimodule.core.ui.util

import android.annotation.SuppressLint
import android.os.Build
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import java.time.Duration
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

fun getCurrentTime(): String {
    val now = LocalTime.now()  // 현재 시각
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
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

fun getStartAndEndTime(selectedTime: String): Pair<String, String> {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)
    val selectedFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)

    val now = LocalTime.now() // 현재 시간
    val additionalTime = LocalTime.parse(selectedTime, selectedFormatter)

    val endTime = now
        .plusHours(additionalTime.hour.toLong())
        .plusMinutes(additionalTime.minute.toLong())

    return now.format(formatter) to endTime.format(formatter)
}

fun updateToggle(id: Int, list: List<ReservationItemModel>): List<ReservationItemModel> {
    return list.map { item ->
        if (item.id == id) {
            item.copy(isToggleChecked = !item.isToggleChecked)
        } else item
    }
}

fun isNowWithinTimeRange(startTimeStr: String, endTimeStr: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val now = LocalTime.now()
    val startTime = LocalTime.parse(startTimeStr, formatter)
    val endTime = LocalTime.parse(endTimeStr, formatter)

    return if (endTime.isAfter(startTime)) {
        // 같은 날 범위 (ex: 10:00 ~ 18:00)
        now >= startTime && now <= endTime
    } else {
        // 자정 넘어가는 범위 (ex: 23:00 ~ 02:00)
        now >= startTime || now <= endTime
    }
}

@SuppressLint("DefaultLocale")
fun getRemainingTimeFormatted(endTimeStr: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val now = LocalTime.now()
    val endTime = LocalTime.parse(endTimeStr, formatter)

    val duration = if (endTime.isAfter(now)) {
        Duration.between(now, endTime)
    } else {
        Duration.between(now, endTime.plusHours(24))
    }

    val hours = duration.toHours()
    val minutes = duration.toMinutesPart()
    val seconds = duration.toSecondsPart()

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun sumTimeStringsToHourMinuteFormat(timeStrings: List<String>): String {
    var totalMinutes = 0

    for (timeStr in timeStrings) {
        val hourRegex = Regex("(\\d+)시간")
        val minuteRegex = Regex("(\\d+)분")

        val hours = hourRegex.find(timeStr)?.groups?.get(1)?.value?.toIntOrNull() ?: 0
        val minutes = minuteRegex.find(timeStr)?.groups?.get(1)?.value?.toIntOrNull() ?: 0

        totalMinutes += hours * 60 + minutes
    }

    val totalHours = totalMinutes / 60
    val remainingMinutes = totalMinutes % 60

    return "${totalHours}시간 ${remainingMinutes}분"
}

fun convertTimeStringToMinutes(time: String): Int {
    val hourRegex = Regex("(\\d+)시간")
    val minuteRegex = Regex("(\\d+)분")

    val hours = hourRegex.find(time)?.groups?.get(1)?.value?.toIntOrNull() ?: 0
    val minutes = minuteRegex.find(time)?.groups?.get(1)?.value?.toIntOrNull() ?: 0

    return hours * 60 + minutes
}

fun getTimeDifference(startTimeStr: String, endTimeStr: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val startTime = LocalTime.parse(startTimeStr, formatter)
    val endTime = LocalTime.parse(endTimeStr, formatter)

    // 자정을 넘어가는 경우 처리
    val duration = if (endTime.isAfter(startTime) || endTime == startTime) {
        Duration.between(startTime, endTime)
    } else {
        Duration.between(startTime, endTime.plusHours(24))
    }

    val hours = duration.toHours()
    val minutes = duration.toMinutesPart()
    val seconds = duration.toSecondsPart()

    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

fun timeStringToSeconds(timeStr: String): Int {
    val parts = timeStr.split(":").map { it.toIntOrNull() ?: 0 }
    return if (parts.size == 3) {
        parts[0] * 3600 + parts[1] * 60 + parts[2]
    } else 0
}

fun calculateTimerPercent(totalTime: String, leftTime: String): Float {
    val totalSeconds = timeStringToSeconds(totalTime)
    val leftSeconds = timeStringToSeconds(leftTime)
    if (totalSeconds == 0) return 0f

    // 남은 시간 / 총 시간 × 100%
    return (leftSeconds.toFloat() / totalSeconds.toFloat())
}



