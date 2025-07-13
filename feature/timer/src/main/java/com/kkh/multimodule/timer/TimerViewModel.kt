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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer

@HiltViewModel
class TimerViewModel @Inject constructor() :
    ViewModel() {

    private val reducer = TimerReducer(TimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TimerEvent) {
        viewModelScope.launch {

            reducer.sendEvent(e)
        }
    }
}