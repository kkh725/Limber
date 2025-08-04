package com.kkh.multimodule.core.domain.repository

import com.kkh.multimodule.core.domain.model.ReservationInfo
import kotlinx.coroutines.flow.Flow

/**
 * 차단 예약 관련 레포 (timer)
 */
interface BlockReservationRepository {
    suspend fun setReservationList(reservationList: List<ReservationInfo>)
    suspend fun getReservationList(): List<ReservationInfo>
    fun observeReservationList(): Flow<List<ReservationInfo>>
}