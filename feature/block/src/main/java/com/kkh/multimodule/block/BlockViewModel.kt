package com.kkh.multimodule.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BlockViewModel @Inject constructor() : ViewModel() {

    private val reducer = BlockReducer(BlockState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: BlockEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)
            when (e) {
                BlockEvent.OnClickContinueButton -> {

                }

                BlockEvent.OnClickUnLockedButton -> {

                }
            }
        }
    }
}