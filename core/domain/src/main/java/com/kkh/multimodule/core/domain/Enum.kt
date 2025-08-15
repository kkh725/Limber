package com.kkh.multimodule.core.domain

enum class ScreenState{
    ONBOARDING_SCREEN,HOME_SCREEN, TIMER_SCREEN, LABORATORY_SCREEN, MORE_SCREEN
}

enum class TimerStatusModel {
    ON,OFF
}

enum class TimerCode(val text : String){
    IMMEDIATE("IMMEDIATE"),
    SCHEDULED("SCHEDULED")
}

enum class RepeatCycleCodeModel(val code: String) {
    NONE("NONE"),
    EVERYDAY("EVERYDAY"),
    WEEKDAY("WEEKDAY"),
    WEEKEND("WEEKEND")
}