package com.kkh.multimodule.core.data.repository

import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.model.ReservationInfo
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * 차단 예약 관련 레포 (timer)
 */
class BlockReservationRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) : BlockReservationRepository{
    override suspend fun setReservationList(reservationList: List<ReservationInfo>) {
        localDataSource.setReservationList(reservationList)
    }

    override suspend fun getReservationList(): List<ReservationInfo> {
        TODO("Not yet implemented")
    }

    override fun observeReservationList(): Flow<List<ReservationInfo>> {
        TODO("Not yet implemented")
    }
}