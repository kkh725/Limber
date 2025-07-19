package com.kkh.multimodule.feature.home.activeTimer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ActiveTimerViewModel @Inject constructor(private val appDataRepository: AppDataRepository) :
    ViewModel() {

    private val reducer = HomeReducer(ActiveTimerState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: ActiveTimerEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when(e){
                is ActiveTimerEvent.SheetExpanded -> {
                    if (e.isExpanded){
                        setBlockedAppInfoList(e.context)
                    }
                }
                else -> {}
            }
        }
    }

    private suspend fun setBlockedAppInfoList(context: Context) {
        val blockedAppPackageList = appDataRepository.getBlockedPackageList()
        val newAppInfoList: List<AppInfo> = blockedAppPackageList.map {
            AppInfo(
                appName = AppInfoProvider.getAppLabel(context, it) ?: "Unknown",
                packageName = it,
                appIcon = AppInfoProvider.getAppIcon(context, it)
            )
        }
        sendEvent(ActiveTimerEvent.SetBlockedAppList(newAppInfoList))
    }
}