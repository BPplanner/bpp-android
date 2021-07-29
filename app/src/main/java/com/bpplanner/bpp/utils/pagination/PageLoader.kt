package com.bpplanner.bpp.utils.pagination

import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.model.base.MediatorApiCallback
import com.bpplanner.bpp.model.base.MediatorApiLiveData

class PageLoader<T> {
    private val list = ArrayList<T>()
    private val mediatorLiveData = MediatorApiLiveData<List<T>>()
    val liveData: ApiLiveData<List<T>> = mediatorLiveData

    var isFinish = false
        private set

    var page = 1


    fun canLoadList(): Boolean {
        if (mediatorLiveData.value is ApiStatus.Loading) return false
        if (isFinish) return false

        return true
    }

    fun <C : IPageLoadData<T>> addObserve(
        liveData: ApiLiveData<C>,
        callback: MediatorApiCallback<C>? = null
    ) {
        mediatorLiveData.value = ApiStatus.Loading
        mediatorLiveData.addSource(liveData, object : MediatorApiCallback<C> {
            override fun onLoading() {
                mediatorLiveData.value = ApiStatus.Loading
                callback?.onLoading()
            }

            override fun onSuccess(code: Int, data: C) {
                isFinish = true
                list.addAll(data.getList())
                mediatorLiveData.value = ApiStatus.Success(code, list)

                callback?.onSuccess(code, data)
            }

            override fun onError(code: Int, msg: String) {
                if (code == 404) isFinish = true

                mediatorLiveData.value = ApiStatus.Error(code, msg)
                callback?.onError(code, msg)
            }
        })
    }


    fun reset() {
        page = 1
        isFinish = false
        list.clear()
        mediatorLiveData.value = ApiStatus.Success(200, list)
    }

    fun getList(): List<T> = list
}