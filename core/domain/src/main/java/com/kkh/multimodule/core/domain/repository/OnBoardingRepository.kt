package com.kkh.multimodule.core.domain.repository

interface OnBoardingRepository {
    suspend fun setIsCheckedOnBoarding(isChecked: Boolean)
    suspend fun getIsCheckedOnBoarding(): Boolean
}