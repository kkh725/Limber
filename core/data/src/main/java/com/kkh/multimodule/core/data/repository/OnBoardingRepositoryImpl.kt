package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.repository.OnBoardingRepository
import jakarta.inject.Inject

internal class OnBoardingRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    OnBoardingRepository {
    override suspend fun setIsCheckedOnBoarding(isChecked: Boolean) {
        localDataSource.setIsOnboardingChecked(isChecked)
    }

    override suspend fun getIsCheckedOnBoarding(): Boolean {
        return localDataSource.getIsOnboardingChecked()
    }
}