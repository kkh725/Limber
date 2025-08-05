package com.kkh.multimodule.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.domain.repository.AppDataRepository
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val reservationRepository: BlockReservationRepository
) :
    ViewModel() {

    private val reducer = HomeReducer(HomeState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: HomeEvent) {
        viewModelScope.launch {
            reducer.sendEvent(e)

            when (e) {
                is HomeEvent.OnCompleteRegisterButton -> {
                    setBlockedPackageList(e.appList)
                    setBlockModeOn()
                    setPackageList()
                }

                is HomeEvent.EnterHomeScreen -> {
                    setBlockReservationList()
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

    private suspend fun setBlockModeOn() {
        appDataRepository.setBlockMode(true)
    }

    private suspend fun setBlockReservationList() {
        val list = reservationRepository.getReservationList()
        reducer.setState(uiState.value.copy(blockReservationItemList = list))
    }

}