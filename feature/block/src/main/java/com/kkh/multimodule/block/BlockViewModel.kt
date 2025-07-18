package com.kkh.multimodule.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.domain.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BlockViewModel @Inject constructor(private val appDataRepository: AppDataRepository) : ViewModel() {

    private val reducer = BlockReducer(BlockState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: BlockEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)
            when (e) {
                BlockEvent.OnClickContinueButton -> {

                }

                BlockEvent.OnClickUnBlockButton -> {
                    unBlock()
                }
            }
        }
    }

    private suspend fun unBlock(){
        appDataRepository.setBlockMode(false)
    }
}