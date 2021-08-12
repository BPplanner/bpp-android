package com.bpplanner.bpp.ui.mypage

import androidx.lifecycle.Observer
import com.bpplanner.bpp.dto.*
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
                    val list = it.data!!.list

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

    fun getReservationList(): ApiLiveData<ReservationList> {
        val mediator = MediatorApiLiveData<ReservationList>()

        val liveData = retrofit.getReservationList(false)
        mediator.addSource(liveData, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val list = it.data!!.list

                    val reservationList = mutableListOf<MypageData>()
                    val expireList = mutableListOf<MypageData>()

                    for (item in list) {
                        when (item.state) {
                            1 -> reservationList.add(item)
                            2 -> expireList.add(item)
                        }
                    }

                    val data = ReservationList(it.data.remainingDays, reservationList, expireList)
                    mediator.value = ApiStatus.Success(it.code, data)
                }
                else -> {
                    mediator.value = it as ApiStatus<ReservationList>
                }
            }
        })

        return mediator
    }

    fun cancelInquiring(id: Int): ApiLiveData<Any> {
        return retrofit.cancelInquiring(id)
    }

    fun confirmReservation(id: Int, date: String): ApiLiveData<Any> {
        return retrofit.confirmReservation(id, ConfirmReservationRequest(date))
    }


}