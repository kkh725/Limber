package com.kkh.multimodule.feature.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ReservationViewModel @Inject constructor() : ViewModel() {

    private val reducer = ReservationReducer(ReservationState.Companion.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: ReservationEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)
        }
    }
}