package com.kkh.multimodule.core.domain.repository

import com.kkh.multimodule.core.domain.model.ReservationItemModel
import kotlinx.coroutines.flow.Flow

/**
 * 차단 예약 관련 레포 (timer)
 */
interface BlockReservationRepository {
    suspend fun setReservationList(reservationList: List<ReservationItemModel>)
    suspend fun getReservationList(): List<ReservationItemModel>
    fun observeReservationList(): Flow<List<ReservationItemModel>>
}