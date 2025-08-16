package com.kkh.multimodule.core.ui.util

import android.annotation.SuppressLint
import android.os.Build
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDate
import java.time.LocalDateTime
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

fun getRemainingTimeFormattedSafe(endTimeStr: String): String {
    // 허용하는 포맷들: "HH:mm:ss" 우선, 없으면 "HH:mm"
    val formatterWithSec = DateTimeFormatter.ofPattern("HH:mm:ss")
    val formatterNoSec = DateTimeFormatter.ofPattern("HH:mm")

    val nowDateTime = LocalDateTime.now()

    // endTimeStr에 초 정보가 있으면 그 포맷으로 파싱, 없으면 분만 있는 포맷으로 파싱
    val endLocalTime: LocalTime = try {
        LocalTime.parse(endTimeStr, formatterWithSec)
    } catch (e: Exception) {
        LocalTime.parse(endTimeStr, formatterNoSec)
    }

    // 오늘의 endDateTime
    var endDateTime = LocalDateTime.of(LocalDate.now(), endLocalTime)

    // end가 이미 지났다면 내일로 설정
    if (endDateTime.isBefore(nowDateTime) || endDateTime.isEqual(nowDateTime)) {
        endDateTime = endDateTime.plusDays(1)
    }

    val duration = Duration.between(nowDateTime, endDateTime)
    val totalSeconds = duration.seconds.coerceAtLeast(0) // 안전 장치

    val hours = (totalSeconds / 3600).toInt()
    val minutes = ((totalSeconds % 3600) / 60).toInt()
    val seconds = (totalSeconds % 60).toInt()

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
    // 허용되는 포맷 우선순위: 초 포함("HH:mm:ss") -> 초 미포함("HH:mm")
    val fmtWithSec = DateTimeFormatter.ofPattern("HH:mm:ss")
    val fmtNoSec = DateTimeFormatter.ofPattern("HH:mm")

    fun parseTime(s: String): LocalTime {
        return try {
            LocalTime.parse(s, fmtWithSec)
        } catch (e: Exception) {
            LocalTime.parse(s, fmtNoSec)
        }
    }

    val startLocalTime = parseTime(startTimeStr)
    val endLocalTime = parseTime(endTimeStr)

    // 오늘 날짜 기준 LocalDateTime 생성
    val today = LocalDate.now()
    var startDateTime = LocalDateTime.of(today, startLocalTime)
    var endDateTime = LocalDateTime.of(today, endLocalTime)

    // 만약 end가 start 이전이면 end는 다음날로 처리 (자정 넘김)
    if (endDateTime.isBefore(startDateTime)) {
        endDateTime = endDateTime.plusDays(1)
    }

    val duration = Duration.between(startDateTime, endDateTime)
    val totalSeconds = duration.seconds.coerceAtLeast(0) // 안전하게 음수 방지

    val hours = (totalSeconds / 3600).toInt()
    val minutes = ((totalSeconds % 3600) / 60).toInt()
    val seconds = (totalSeconds % 60).toInt()

    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

fun timeStringToSeconds(timeStr: String): Int {
    val parts = timeStr.split(":").map { it.toIntOrNull() ?: 0 }
    return if (parts.size == 3) {
        parts[0] * 3600 + parts[1] * 60 + parts[2]
    } else 0
}

fun calculateTimerPercentReversed(totalTime: String, leftTime: String): Float {
    val totalSeconds = timeStringToSeconds(totalTime)
    val leftSeconds = timeStringToSeconds(leftTime)
    if (totalSeconds == 0) return 1f  // 총 시간 0이면 바로 100%

    return 1f - (leftSeconds.toFloat() / totalSeconds.toFloat())
}

fun getWeekDateRange(): Pair<String, String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDate = LocalDate.now()
    val endDate = startDate.plusDays(7)
    return startDate.format(formatter) to endDate.format(formatter)
}

fun getCurrentWeekRange(): String {
    val today = LocalDate.now()
    val sevenDaysLater = today.plusDays(6) // 오늘 포함하면 6일 뒤가 7일 범위

    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN)

    val startDateStr = today.format(formatter)
    val endDateStr = sevenDaysLater.format(formatter)

    return "$startDateStr-${endDateStr}"
}

fun getWeekDateString(): Pair<String, String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    val currentDate = LocalDate.now()
    val beforeDate = currentDate.minusDays(7)
    return currentDate.format(formatter) to beforeDate.format(formatter)
}




