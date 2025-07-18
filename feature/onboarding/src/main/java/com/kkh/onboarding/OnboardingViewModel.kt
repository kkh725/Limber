package com.kkh.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.multimodule.domain.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val appDataRepository: AppDataRepository) :
    ViewModel() {

    private val reducer = OnboardingReducer(OnBoardingState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: OnboardingEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is OnboardingEvent.OnCompleteRegisterButton -> {
                    setBlockedPackageList(e.appList)
                }
                else -> {}
            }
        }
    }

    private suspend fun setBlockedPackageList(appList: List<AppInfo>) {
        val packageList = appList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }
}