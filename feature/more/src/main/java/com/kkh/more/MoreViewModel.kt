package com.kkh.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.ui.ui.CommonEffect
import com.kkh.multimodule.core.ui.ui.CommonEvent
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.util.getWeekDateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val reservationRepository: BlockReservationRepository
) : ViewModel() {

    private val reducer = MoreReducer(MoreState.init())
    val uiState get() = reducer.uiState
    val uiEffect get() = reducer.uiEffect

    fun sendEvent(e: MoreEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is MoreEvent.EnterMoreScreen -> {
                    setPackageList()
                    setBottomSheetCheckedList()
                }

                else -> {}
            }
        }
    }

    private suspend fun setPackageList() {
        sendEvent(MoreEvent.SetBlockingAppList(appDataRepository.getBlockedPackageList()))
    }

    private suspend fun setBottomSheetCheckedList(){
        val res = appDataRepository.getBlockedPackageList()
        val appInfoList = uiState.value.usageAppInfoList

        val checkedList = appInfoList.map { res.contains(it.packageName) }
        reducer.setState(uiState.value.copy(checkedList=checkedList))
    }

}