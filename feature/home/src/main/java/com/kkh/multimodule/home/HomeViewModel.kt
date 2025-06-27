package com.kkh.multimodule.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val reducer = HomeReducer(HomeState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: HomeEvent) {
        when (e) {
            HomeEvent.ClickedButton -> {
            }
        }
    }

}