package com.kkh.multimodule.feature.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

@HiltViewModel
class TimerViewModel @Inject constructor(private val appDataRepository: AppDataRepository) :
    ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)

            when(e){
                is TimerEvent.OnClickSheetCompleteButton ->{
                    // modal 에서 시작하기를 눌러야 차단해야할듯.
//                    setBlockedPackageList()
                }
                is TimerEvent.OnClickModalCompleteButton -> {
                    setBlockNow(LocalTime(1,1))
                }
                else -> {}
            }
        }
    }

    private suspend fun setBlockedPackageList() {
        val packageList = uiState.value.appDataList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setBlockNow(localTime: LocalTime){
        val type = uiState.value.chipList.find { it.isSelected }
        val time = localTime.toString()
        val blockAppList = uiState.value.modalAppDataList

        // send api

        // local에서 당장 block 시작.
        appDataRepository.setBlockedPackageList(blockAppList.map { it.packageName })
        appDataRepository.setBlockMode(true)
    }
}