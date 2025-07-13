package com.kkh.onboarding

import android.annotation.SuppressLint
import android.content.Context
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

sealed class HomeEvent : UiEvent {
    data class OnClickRegisterButton(val context: Context) : HomeEvent()
}

class OnboardingReducer(state: OnBoardingState) : Reducer<OnBoardingState, HomeEvent>(state) {
    @SuppressLint("UseCompatLoadingForDrawables")

    override suspend fun reduce(oldState: OnBoardingState, event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClickRegisterButton -> {
                val context = event.context.applicationContext

                val newList = withContext(Dispatchers.IO) {
                    AppInfoProvider.getMonthUsageAppInfoList(context)
                }

                // UI 스레드에서 상태 업데이트
                setState(oldState.copy(usageAppInfoList = newList))
            }
        }
    }
}