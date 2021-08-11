package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.ConfirmReservationRequest
import com.bpplanner.bpp.dto.MypageResponse
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.*

interface ReservationRetrofit {

    @GET("/reservations/")
    fun getReservationList(
        @Query("inquiry") type: Boolean
    ): ApiLiveData<MypageResponse>

    @DELETE("/reservations/{id}")
    fun cancelInquiring(
        @Path("id")id :Int
    ): ApiLiveData<Void>

    @PATCH("/reservations/{id}")
    fun confirmReservation(
        @Path("id")id :Int,
        @Body date: ConfirmReservationRequest
    ): ApiLiveData<Void>
}

