package com.kkh.multimodule.timer

import kotlinx.datetime.LocalTime

enum class TimerScreenType {
    Now, Reserved
}

enum class ReservationScreenState {
    Idle, Modify
}

data class ReservationTime(
    val title: String,
    val category: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val repeatDays: List<String> = emptyList()
) {
    companion object {
        fun init() = ReservationTime(
            startTime = LocalTime(8, 0),
            endTime = LocalTime(10, 0),
            title = "포트폴리오 작업",
            category = "공부",
            repeatDays = emptyList()
        )
    }
}


