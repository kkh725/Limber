package com.kkh.multimodule.timer

import android.app.usage.UsageStats
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppInfoProvider
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.accessibility.AppUsageStatsManager.getUsageStats
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.data.repository.AppDataRepository
import com.kkh.multimodule.datastore.datasource.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer

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
                    savePackageList()
                }
                else -> {}
            }
        }
    }

    private suspend fun savePackageList() {
        val packageList = uiState.value.appDataList.map { it.packageName }
        appDataRepository.savePackageList(packageList)
    }
}