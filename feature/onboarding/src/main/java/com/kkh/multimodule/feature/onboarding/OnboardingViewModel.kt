package com.kkh.multimodule.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.appinfo.AppInfo
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val reducer = OnboardingReducer(OnBoardingState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: OnboardingEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is OnboardingEvent.OnCompleteRegisterButton -> {
                    setBlockedPackageList(e.appList)
                }

                is OnboardingEvent.OnCompleteOnBoarding -> {
                    setIsCheckedOnBoarding(true)
                }

                else -> {}
            }
        }
    }

    private suspend fun setBlockedPackageList(appList: List<AppInfo>) {
        val packageList = appList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setIsCheckedOnBoarding(isChecked: Boolean) {
        onBoardingRepository.setIsCheckedOnBoarding(isChecked)
    }
}