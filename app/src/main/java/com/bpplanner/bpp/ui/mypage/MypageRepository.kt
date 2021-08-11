package com.bpplanner.bpp.ui.mypage

import androidx.lifecycle.Observer
import com.bpplanner.bpp.dto.ConfirmReservationRequest
import com.bpplanner.bpp.dto.InquiringList
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.dto.MypageResponse
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.model.base.MediatorApiLiveData
import com.bpplanner.bpp.model.base.RestClient

class MypageRepository {
    private val retrofit = RestClient.getReservationService()

    fun getInquiringList(): ApiLiveData<InquiringList> {
        val mediator = MediatorApiLiveData<InquiringList>()

        val liveData = retrofit.getReservationList(true)
        mediator.addSource(liveData, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val list = it.data.list

                    val studioList = mutableListOf<MypageData>()
                    val beautyList = mutableListOf<MypageData>()

                    for (item in list) {
                        when (item.shop.type) {
                            0 -> studioList.add(item)
                            1 -> beautyList.add(item)
                        }
                    }

                    val data = InquiringList(studioList, beautyList)
                    mediator.value = ApiStatus.Success(it.code, data)
                }
                else -> {
                    mediator.value = it as ApiStatus<InquiringList>
                }
            }
        })

        return mediator
    }

    fun getReservationList(): ApiLiveData<MypageResponse> {
        return retrofit.getReservationList(false)
    }

    fun cancelInquiring(id: Int): ApiLiveData<Void> {
        return retrofit.cancelInquiring(id)
    }

    fun confirmReservation(id: Int, date: String): ApiLiveData<Void> {
        return retrofit.confirmReservation(id, ConfirmReservationRequest(date))
    }


}