package com.bpplanner.bpp.ui.mypage

import androidx.lifecycle.ViewModel
import com.bpplanner.bpp.dto.InquiringList
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.dto.MypageResponse
import com.bpplanner.bpp.model.base.ApiLiveData

class MypageViewModel : ViewModel() {
    private val repository = MypageRepository()

    fun getInquiringList(): ApiLiveData<InquiringList> {
        return repository.getInquiringList()
    }

    fun getReservation(): ApiLiveData<MypageResponse> {
        return repository.getReservationList()
    }

    fun cancelInquiring(item: MypageData): ApiLiveData<Void> {
        return repository.cancelInquiring(item.id)
    }


    fun confirmReservation(item: MypageData, date: String): ApiLiveData<Void> {
        return repository.confirmReservation(item.id, date)
    }
}