package com.kkh.multimodule.limber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.domain.ScreenState
import com.kkh.multimodule.core.domain.repository.OnBoardingRepository
import com.kkh.multimodule.limber.intent.RootEvent
import com.kkh.multimodule.limber.intent.RootReducer
import com.kkh.multimodule.limber.intent.RootState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RootViewModel @Inject constructor(private val onBoardingRepository: OnBoardingRepository) : ViewModel(){
    private val reducer = RootReducer(RootState.Companion.init())
    val uiState get() = reducer.uiState

    fun sendEvent(event : RootEvent){
        viewModelScope.launch {
            reducer.sendEvent(event)

            when(event){
                is RootEvent.Init -> {
                    checkOnBoarding()
                }
                else -> {}
            }
        }
    }

    private suspend fun checkOnBoarding(){
        val checked = onBoardingRepository.getIsCheckedOnBoarding()
        reducer.setState(uiState.value.copy(isOnboardingChecked = checked))

        if (checked){
            sendEvent(RootEvent.SetScreenState(ScreenState.HOME_SCREEN))
        }
    }
}