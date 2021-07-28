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

        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                LogUtil.e("HttpRetrofit", "onFailure", t)
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {

                CoroutineScope(Dispatchers.Main).launch {

                    val code = response.code()
                    if (response.isSuccessful) {
                        liveData.value = ApiStatus.Success(code, response.body()!!)
                        return@launch
                    }

                    liveData.value = ApiStatus.Error(code, response.message())

//                    when (response.code()) {
//                        401 -> {
//                            Firebase.auth.currentUser?.getIdToken(true)
//                                ?.addOnSuccessListener {
//                                    process(call, liveData)
//                                }
//                                ?.addOnFailureListener {
//                                    liveData.value = ApiStatus.Error(code, response.message())
//                                }
//                        }
//                        else -> {
//                            // Error 처리
//                        }
//                    }

                }
            }
        })

        return liveData
    }

    private fun process(call: Call<R>, liveData: MutableApiLiveData<R>) {
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