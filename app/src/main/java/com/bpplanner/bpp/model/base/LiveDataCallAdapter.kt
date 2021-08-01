package com.bpplanner.bpp.model.base

import androidx.lifecycle.LiveData
import com.bpplanner.bpp.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiStatus<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): ApiLiveData<R> {
        val liveData = MutableApiLiveData<R>()
        liveData.value = ApiStatus.Loading
        process(call, liveData)
        return liveData
    }

    private fun process(call: Call<R>, liveData: MutableApiLiveData<R>) {
        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                LogUtil.e("HttpRetrofit", "onFailure", t)
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                val code = response.code()
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        liveData.value = ApiStatus.Success(code, response.body()!!)
                        return@launch
                    }
                }
                when (response.code()) {
                    else -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            liveData.value = ApiStatus.Error(code, response.message())
                            return@launch
                        }
                    }
                }

            }
        })
    }

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            if (getRawType(returnType) != LiveData::class.java) {
                return null
            }
            val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
            val rawObservableType = getRawType(observableType)
            require(rawObservableType == ApiStatus::class.java) { "type must be a resource" }
            require(observableType is ParameterizedType) { "resource must be parameterized" }
            val bodyType = getParameterUpperBound(0, observableType)
            return LiveDataCallAdapter<Any?>(
                bodyType
            )
        }
    }
}