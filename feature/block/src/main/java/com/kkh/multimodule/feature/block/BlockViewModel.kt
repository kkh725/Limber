package com.kkh.multimodule.feature.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.domain.FailReason
import com.kkh.multimodule.core.domain.replaceIdToString
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.feature.block.block.BlockEvent
import com.kkh.multimodule.feature.block.block.BlockReducer
import com.kkh.multimodule.feature.block.block.BlockState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val reducer = BlockReducer(BlockState.Companion.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: BlockEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)
            when (e) {
                BlockEvent.OnClickContinueButton -> {

                }

                is BlockEvent.OnClickUnBlockButton -> {
                    unBlock(e.reasonId)
                }
            }
        }
    }

    private suspend fun unBlock(reasonId: Int) {
        val currentTimer = timerRepository.getActiveTimerId()
        appDataRepository.setBlockMode(false)
        timerRepository.unlockTimer(currentTimer, replaceIdToString(reasonId))
    }
}