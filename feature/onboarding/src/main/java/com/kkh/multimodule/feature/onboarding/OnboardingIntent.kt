package com.kkh.multimodule.feature.onboarding

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import com.kkh.multimodule.core.accessibility.appinfo.AppInfo
import com.kkh.multimodule.core.accessibility.appinfo.AppInfoProvider
import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class OnBoardingState(
    val usageAppInfoList: List<AppInfo>
) : UiState {
    companion object {
        fun init() = OnBoardingState(
            usageAppInfoList = emptyList()
        )
    }
}

sealed class OnboardingEvent : UiEvent {
    data class OnEnterScreen(val context: Context)  : OnboardingEvent()
    data class OnClickRegisterButton(val context: Context) : OnboardingEvent()
    data class OnCompleteRegisterButton (val appList : List<AppInfo>): OnboardingEvent()
    data object OnCompleteOnBoarding : OnboardingEvent()
}

class OnboardingReducer(state: OnBoardingState) : Reducer<OnBoardingState, OnboardingEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: OnBoardingState, event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.OnEnterScreen -> {
                // sheet이 올라오는지 내려가는지 확인
                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getUsageAppInfoList(event.context, period = UsageStatsManager.INTERVAL_MONTHLY)
                }

                setState(
                    oldState.copy(
                        usageAppInfoList = newList
                    )
                )
            }

            is OnboardingEvent.OnClickRegisterButton -> {
                val context = event.context.applicationContext

                val startTime = System.currentTimeMillis()
                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getUsageAppInfoList(context, UsageStatsManager.INTERVAL_MONTHLY)
                }
                val endTime = System.currentTimeMillis()

                Log.d("OnboardingReducer", "AppInfoProvider.getMonthUsageAppInfoList took ${endTime - startTime} ms")

                // UI 스레드에서 상태 업데이트
                setState(oldState.copy(usageAppInfoList = newList))
            }
            else -> {}
        }
    }
}