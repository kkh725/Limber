package com.kkh.multimodule.feature.home.recall

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.accessibility.AppInfoProvider
import com.kkh.multimodule.core.domain.model.RetrospectsRequestModel
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RecallViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val timerRepository: TimerRepository
) :
    ViewModel() {

    private val reducer = RecallReducer(RecallState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: RecallEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)


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
    }

//    private suspend fun writeRetrospects() {
//        val reqest = RetrospectsRequestModel("UUID1",)
//        timerRepository.writeRetrospects()
//    }
}