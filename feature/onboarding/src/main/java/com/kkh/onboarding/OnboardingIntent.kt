package com.kkh.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
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
    data class OnClickRegisterButton(val context: Context) : OnboardingEvent()
    data class OnCompleteRegisterButton (val appList : List<AppInfo>): OnboardingEvent()
}

class OnboardingReducer(state: OnBoardingState) : Reducer<OnBoardingState, OnboardingEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: OnBoardingState, event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.OnClickRegisterButton -> {
                val context = event.context.applicationContext

                val startTime = System.currentTimeMillis()
                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getMonthUsageAppInfoList(context)
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