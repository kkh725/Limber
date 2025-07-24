package com.kkh.multimodule.limber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.limber.intent.RootEvent
import com.kkh.multimodule.limber.intent.RootReducer
import com.kkh.multimodule.limber.intent.RootState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RootViewModel @Inject constructor() : ViewModel(){
    private val reducer = RootReducer(RootState.Companion.init())
    val uiState get() = reducer.uiState

    fun sendEvent(event : RootEvent){
        viewModelScope.launch {
            reducer.sendEvent(event)
        }
    }
}