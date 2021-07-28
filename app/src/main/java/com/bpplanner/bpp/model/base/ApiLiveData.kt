package com.bpplanner.bpp.model.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


typealias ApiLiveData<T> = LiveData<ApiStatus<T>>
typealias MutableApiLiveData<T> = MutableLiveData<ApiStatus<T>>
typealias MediatorApiLiveData<T> = MediatorImpl<ApiStatus<T>>

class MediatorImpl<T : ApiStatus<*>> : MediatorLiveData<T>() {
    fun <S : Any?> addSource(
        source: LiveData<ApiStatus<S>>,
        callback: MediatorApiCallback<in S>
    ) {
        addSource(source, Observer {
            if (it !is ApiStatus.Loading) removeSource(source)
            when (it) {
                is ApiStatus.Loading -> callback.onLoading()
                is ApiStatus.Success<S> -> callback.onSuccess(it.code, it.data)
                is ApiStatus.Error -> callback.onError(it.code, it.message)
            }
        })
    }

    fun <S : Any?> addSource(
        source: LiveData<ApiStatus<S>>,
        callback: MediatorApiSuccessCallback<in S>
    ) {
        addSource(source, Observer {
            if (it !is ApiStatus.Loading) removeSource(source)
            when (it) {
                is ApiStatus.Success<S> -> callback.onSuccess(it.code, it.data)
                else -> value = it as T
            }
        })
    }

    fun addSource(source: LiveData<T>) {
        addSource(source, Observer {
            if (it !is ApiStatus.Loading) removeSource(source)
            value = it
        })
    }

}

interface MediatorApiCallback<T>: MediatorApiSuccessCallback<T> {
    fun onLoading() {}
    fun onError(code: Int, msg: String) {}
}

interface MediatorApiSuccessCallback<T> {
    fun onSuccess(code: Int, data: T)
}
