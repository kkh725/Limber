package com.kkh.multimodule.moduletest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.moduletest.intent.RootEvent
import com.kkh.multimodule.moduletest.intent.RootReducer
import com.kkh.multimodule.moduletest.intent.RootState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RootViewModel @Inject constructor() : ViewModel(){
    private val reducer = RootReducer(RootState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(event : RootEvent){
        viewModelScope.launch {
            reducer.sendEvent(event)
        }
    }
}