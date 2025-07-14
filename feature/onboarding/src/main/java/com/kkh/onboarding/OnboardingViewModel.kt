package com.kkh.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppUsageStatsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val reducer = OnboardingReducer(OnBoardingState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: OnboardingEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)
        }
    }
}