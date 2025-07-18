package com.kkh.multimodule.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.accessibility.AppInfo
import com.kkh.accessibility.AppUsageStatsManager
import com.kkh.multimodule.domain.repository.AppDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val appDataRepository: AppDataRepository) :
    ViewModel() {

    private val reducer = HomeReducer(HomeState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: HomeEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is HomeEvent.OnCompleteRegisterButton -> {
                    setBlockedPackageList(e.appList)
                    setPackageList()
                }

                is HomeEvent.EnterHomeScreen -> {
                    setPackageList()
                }

                else -> {}
            }
        }
    }

    private suspend fun setBlockedPackageList(appList: List<AppInfo>) {
        val packageList = appList.map { it.packageName }
        appDataRepository.setBlockedPackageList(packageList)
    }

    private suspend fun setPackageList() {
        sendEvent(HomeEvent.SetBlockingAppList(appDataRepository.getBlockedPackageList()))
    }

}