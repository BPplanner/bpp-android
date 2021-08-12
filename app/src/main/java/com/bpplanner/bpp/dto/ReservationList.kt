package com.bpplanner.bpp.dto

data class ReservationList(
    val remainDay: Int?,
    val reservationList: List<MypageData>,
    val expireList: List<MypageData>,
)
