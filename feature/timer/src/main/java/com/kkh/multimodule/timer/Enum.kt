package com.kkh.multimodule.timer

enum class TimerScreenType{
    Now, Reserved
}

enum class ReservationScreenState{
    Idle, Modify
}

data class ReservationInfo(
    val startTime: String = "",
    val startAmPm: String = "AM",
    val endTime: String = "",
    val endAmPm: String = "AM",
    val repeatDays: List<String> = emptyList()
)


