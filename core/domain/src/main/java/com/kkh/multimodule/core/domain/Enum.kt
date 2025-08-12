package com.kkh.multimodule.core.domain

enum class ScreenState{
    ONBOARDING_SCREEN,HOME_SCREEN, TIMER_SCREEN, LABORATORY_SCREEN, MORE_SCREEN
}

enum class TimerStatusModel {
    READY,
    RUNNING,
    PAUSED,
    COMPLETED,
    CANCELED
}

enum class RepeatCycleCodeModel {
    NONE,
    EVERYDAY,
    WEEKDAY,
    WEEKEND
}