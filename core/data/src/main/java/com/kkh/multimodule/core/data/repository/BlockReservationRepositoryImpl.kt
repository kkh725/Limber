package com.kkh.multimodule.core.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.kkh.multimodule.core.datastore.datasource.LocalDataSource
import com.kkh.multimodule.core.domain.model.ReservationItemModel
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * 차단 예약 관련 레포 (timer)
 */
class BlockReservationRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    BlockReservationRepository {
    override suspend fun setReservationList(reservationList: List<ReservationItemModel>) {
        localDataSource.setReservationList(reservationList)
        Log.d(TAG, "setReservationList: 로컬 예약 완료 리스트중 첫번째 : ${reservationList.first()}")
    }

    override suspend fun getReservationList(): List<ReservationItemModel> {
        return localDataSource.getReservationList()
    }

    override fun observeReservationList(): Flow<List<ReservationItemModel>> {
        return localDataSource.observeReservationList()
    }
}